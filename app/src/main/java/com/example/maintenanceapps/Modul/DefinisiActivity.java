package com.example.maintenanceapps.Modul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterDefinisi;
import com.example.maintenanceapps.Model.ModelDefinisi;
import com.example.maintenanceapps.Model.ResponseDefinisi;
import com.example.maintenanceapps.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefinisiActivity extends AppCompatActivity {
    private static final String TAG = "DEFINISI_ACTIVITY";

    private RecyclerView rvData;
    //    private RecyclerView.Adapter adData;
    private AdapterDefinisi adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelDefinisi> listDefinisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definisi);

        rvData = findViewById(R.id.rvDefinisi);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        getData();
    }

    public void getData(){
        Intent intent = getIntent();
        String id_alarm = intent.getStringExtra("id_alarm");
        final String display = intent.getStringExtra("display");
        final String nama_alarm = intent.getStringExtra("nama_alarm");
        final String nama_jenis = intent.getStringExtra("nama_jenis");
        final String nama_series = intent.getStringExtra("nama_series");

        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseDefinisi> listData = aiData.getDefinisi(id_alarm);

        listData.enqueue(new Callback<ResponseDefinisi>() {
            @Override
            public void onResponse(Call<ResponseDefinisi> call, Response<ResponseDefinisi> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons: Data Definisi");

                listDefinisi = response.body().getResult();

                TextView tvDisplay = (TextView) findViewById(R.id.txt_display);
                TextView tvNamaAlarm = (TextView) findViewById(R.id.txt_nama_alarm);
                TextView tvModul = (TextView) findViewById(R.id.txt_modul);

                tvDisplay.setText("Display  : " + display);
                tvNamaAlarm.setText("Nama    : " + nama_alarm);
                tvModul.setText("Modul " + nama_jenis + " " + nama_series);

                LinearLayout kosongLayout = (LinearLayout) findViewById(R.id.layout_kosong);

                Log.d(TAG, "listDefinisi: " +listDefinisi.size());
                if (listDefinisi.size() == 0) {
                    kosongLayout.setVisibility(LinearLayout.VISIBLE);
                } else {
                    kosongLayout.setVisibility(LinearLayout.GONE);

                    adData = new AdapterDefinisi(DefinisiActivity.this, listDefinisi);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseDefinisi> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Modul, error: " + t.getMessage());
                Toast.makeText(DefinisiActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
