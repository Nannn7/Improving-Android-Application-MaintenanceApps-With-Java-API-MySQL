package com.example.maintenanceapps.Maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterMntFoto;
import com.example.maintenanceapps.Model.ModelMaintenance;
import com.example.maintenanceapps.Model.ModelMaintenanceDetail;
import com.example.maintenanceapps.Model.ModelMesin;
import com.example.maintenanceapps.Model.ResponseDetailMnt;
import com.example.maintenanceapps.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMntActivity extends AppCompatActivity {
    private static final String TAG = "DETAIL_MNT_ACTIVITY";

    private RecyclerView rvData;
    private AdapterMntFoto adData;
    private RecyclerView.LayoutManager lmData;

    private TextView line, modul;
    private TextView waktu_error, waktu_mulai, waktu_selesai, waktu_lama;
    private TextView pic, sppmwh, bagian_mesin, sparepart;
    private TextView mekanikal_masalah, mekanikal_penyebab, mekanikal_penyelesaian;
    private TextView teknikal_alarm, teknikal_definisi, teknikal_penyebab;

    private ModelMaintenance dataMnt;
    private ModelMesin dataMesin;
    private List<ModelMaintenanceDetail> listDetailMnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mnt);

        rvData = findViewById(R.id.rvFoto);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvData.setLayoutManager(lmData);

        line = (TextView) findViewById(R.id.txt_line);
        modul = (TextView) findViewById(R.id.txt_modul);

        waktu_error = (TextView) findViewById(R.id.waktu_error);
        waktu_mulai = (TextView) findViewById(R.id.waktu_mulai);
        waktu_selesai = (TextView) findViewById(R.id.waktu_selesai);
        waktu_lama = (TextView) findViewById(R.id.waktu_lama);

        pic = (TextView) findViewById(R.id.pic);
        sppmwh = (TextView) findViewById(R.id.sppmwh);
        bagian_mesin = (TextView) findViewById(R.id.bagian_mesin);
        sparepart = (TextView) findViewById(R.id.sparepart);

        mekanikal_masalah = (TextView) findViewById(R.id.mekanikal_masalah);
        mekanikal_penyebab = (TextView) findViewById(R.id.mekanikal_penyebab);
        mekanikal_penyelesaian = (TextView) findViewById(R.id.mekanikal_penyelesaian);

        teknikal_alarm = (TextView) findViewById(R.id.teknikal_alarm);
        teknikal_definisi = (TextView) findViewById(R.id.teknikal_definisi);
        teknikal_penyebab = (TextView) findViewById(R.id.teknikal_penyebab);

        getDataDetailMnt();
    }

    public void getDataDetailMnt() {
        Intent intent = getIntent();
        String idMnt = intent.getStringExtra("id_mnt");
        Log.d(TAG, "idMnt: " + idMnt);

        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseDetailMnt> listData = aiData.getAllInfoMnt(idMnt);

        listData.enqueue(new Callback<ResponseDetailMnt>() {
            @Override
            public void onResponse(Call<ResponseDetailMnt> call, Response<ResponseDetailMnt> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons Data Mnt by id");

                if (response.body().getKode().equals("1")) {
                    dataMesin = response.body().getMesin();
                    dataMnt = response.body().getMaintenance();
                    listDetailMnt = response.body().getDetailMnt();
                    Log.d(TAG, "dataMesin: " + dataMesin);
                    Log.d(TAG, "getDetailMnt: " + response.body().getDetailMnt().size());

                    line.setText(dataMesin.getNama_Line() + " | Mesin " + dataMesin.getNoMesin() + " Proses " + dataMesin.getNama_Proses());
                    if (dataMesin.getNamaJenis() == null)
                        modul.setText("Tidak ada modul");
                    else
                        modul.setText("Modul " + dataMesin.getNamaJenis() + ", " + dataMesin.getNamaSeries());

                    SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatOut = new SimpleDateFormat("dd MMMM yyyy");
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    try {
                        Date time = formatIn.parse(dataMnt.getTimeError());
                        waktu_error.setText(dataMnt.getTimeError().substring(11, 16) + " | " + formatOut.format(time));

                        time = formatIn.parse(dataMnt.getTimeStart());
                        waktu_mulai.setText(dataMnt.getTimeStart().substring(11,16) + " | " + formatOut.format(time));

                        time = formatIn.parse(dataMnt.getTimeStop());
                        waktu_selesai.setText(dataMnt.getTimeStop().substring(11,16) + " | " + formatOut.format(time));

                        Date start = formatDate.parse(dataMnt.getTimeStart());
                        Date end = formatDate.parse(dataMnt.getTimeStop());
                        waktu_lama.setText(printDifference(start, end));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (dataMnt.getTimeError().equals("0000-00-00 00:00:00")) {
                        waktu_error.setText("Preventive Maintenance");
                    }

                    pic.setText(dataMnt.getNamaPIC());
                    if (dataMnt.getSPPM_WH() != null && dataMnt.getSPPM_WH() != "")
                        sppmwh.setText(dataMnt.getSPPM_WH());
                    else sppmwh.setText("-");
                    if (dataMnt.getID_Sparepart() != null && dataMnt.getID_Sparepart() != "") {
                        bagian_mesin.setText(dataMnt.getNamaBagianMesin());
                        sparepart.setText(dataMnt.getNamaSparepart());
                    } else {
                        bagian_mesin.setText("-");
                        sparepart.setText("-");
                    }

                    if (dataMnt.getMasalah() != null && dataMnt.getMasalah() != "")
                        mekanikal_masalah.setText(dataMnt.getMasalah());
                    else mekanikal_masalah.setText("-");
                    if (dataMnt.getPenyebab() != null && dataMnt.getPenyebab() != "")
                        mekanikal_penyebab.setText(dataMnt.getPenyebab());
                    else mekanikal_penyebab.setText("-");
                    if (dataMnt.getPenanganan() != null && dataMnt.getPenanganan() != "")
                        mekanikal_penyelesaian.setText(dataMnt.getPenanganan());
                    else mekanikal_penyelesaian.setText("-");

                    if (dataMnt.getTekDisplayAlarm() != null && dataMnt.getTekDisplayAlarm() != "")
                        teknikal_alarm.setText(dataMnt.getTekDisplayAlarm() + " | " + dataMnt.getTekNamaAlarm());
                    else teknikal_alarm.setText("-");
                    if (dataMnt.getTekDefinisi() != null && dataMnt.getTekDefinisi() != "")
                        teknikal_definisi.setText(dataMnt.getTekDefinisi());
                    else teknikal_definisi.setText("-");
                    if (dataMnt.getTekPenyebab() != null && dataMnt.getTekPenyebab() != "")
                        teknikal_penyebab.setText(dataMnt.getTekPenyebab());
                    else teknikal_penyebab.setText("-");
                    // kurang satu yg perbaikan

                    adData = new AdapterMntFoto(DetailMntActivity.this, listDetailMnt);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailMnt> call, Throwable t) {
                Toast.makeText(DetailMntActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
//        different = different % minutesInMilli;

//        long elapsedSeconds = different / secondsInMilli;

//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        return elapsedDays + " hari, " + elapsedHours + " jam, " + elapsedMinutes + " menit";
    }
}