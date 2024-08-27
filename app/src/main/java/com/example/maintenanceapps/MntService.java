package com.example.maintenanceapps;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Model.ModelMaintenance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MntService extends Service {
    private static final String TAG = "MNT_SERVICE";

    private ModelMaintenance updateMnt;

    private boolean isRunnable = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunnable) {
                    Log.d(TAG, "runnable ");

                    getdata();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Job finished: ");
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getdata() {
        final APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ModelMaintenance> mnt = aiData.getNotif2();
        mnt.enqueue(new Callback<ModelMaintenance>() {
            @Override
            public void onResponse(Call<ModelMaintenance> call, Response<ModelMaintenance> response) {
                updateMnt = response.body();

                if (updateMnt != null) {
                    Log.d(TAG, "Get it!");
                    getNotif("Breakdown Maintenance", "Mesin " + updateMnt.getNamaMesin() + " " + updateMnt.getNamaLine(), updateMnt.getID());
                } else {
                    Log.d(TAG, "Null, next!");
                }
            }

            @Override
            public void onFailure(Call<ModelMaintenance> call, Throwable t) {

            }
        });
    }

    public void getNotif(String title, String message, String id) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, id);
        notifBuilder.setContentTitle("Breakdown Mesin");
        notifBuilder.setContentText(message);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notifBuilder.setContentIntent(pendingIntent);
        notifBuilder.setSound(soundUri);
        notifBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notifBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
//        notifBuilder.setAutoCancel(true);

        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(2000);
        Log.d(TAG, "Let's go notif!");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = id;
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notifBuilder.setChannelId(channelId);
        }

        notificationManager.notify(Integer.parseInt(id), notifBuilder.build());
    }
}
