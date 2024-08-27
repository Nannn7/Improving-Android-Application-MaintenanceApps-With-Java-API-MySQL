package com.example.maintenanceapps;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Model.ModelMaintenance;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MntJobService extends JobService {
    private static final String TAG = "MNT_JOBSERVICE";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Job started");
        doBackgroundWork();

        return true;
    }

    private void doBackgroundWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    Log.d(TAG, "run: " + i);

                    getDataNetwork();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Job finished: ");
//                jobFinished(jobParameters, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }

    public void getNotif(String title, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this);
        notifBuilder.setContentTitle("Breakdown Mesin");
        notifBuilder.setContentText(message);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notifBuilder.setSound(soundUri);
        notifBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notifBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
        notifBuilder.setAutoCancel(true);

        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1000);
        notifBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notifBuilder.build());
    }

    private void getDataNetwork() {
        getObservable().subscribeWith(getObserver());
    }

    public Observable<ModelMaintenance> getObservable() {
        Log.d(TAG, "getObservable");
        return APIClient.getApiClient().create(APIInterface.class)
                .getNotif()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<ModelMaintenance> getObserver() {
        return new DisposableObserver<ModelMaintenance>() {
            @Override
            public void onNext(ModelMaintenance mnt) {
                getNotif("Breakdown Maintenance", "Mesin " + mnt.getNamaMesin() + " " + mnt.getNamaLine());
                Log.d(TAG, "onNext Notif");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError Notif : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete Notif");
            }
        };
    }
}