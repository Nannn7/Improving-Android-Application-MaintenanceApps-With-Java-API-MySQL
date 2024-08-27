package com.example.maintenanceapps.Modul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterAlarm;
import com.example.maintenanceapps.Model.ModelAlarm;
import com.example.maintenanceapps.Model.ResponseAlarm;
import com.example.maintenanceapps.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmActivity extends AppCompatActivity {
    private static final String TAG = "ALARM_ACTIVITY";

    private RecyclerView rvData;
    //    private RecyclerView.Adapter adData;
    private AdapterAlarm adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelAlarm> listAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        rvData = findViewById(R.id.rvAlarm);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        getData();
    }

    public void getData(){
        Intent intent = getIntent();
        String id_series = intent.getStringExtra("id_series");
        final String nama_jenis = intent.getStringExtra("nama_jenis");
        final String nama_series = intent.getStringExtra("nama_series");

        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseAlarm> listData = aiData.getAlarm(id_series);

        listData.enqueue(new Callback<ResponseAlarm>() {
            @Override
            public void onResponse(Call<ResponseAlarm> call, Response<ResponseAlarm> response) {
                String pesan = response.body().getPesan();
//                Log.d(TAG, "Respons: Data Alarm");

                listAlarm = response.body().getResult();

                TextView tvModul = (TextView) findViewById(R.id.txt_modul);
                tvModul.setText("Modul " + nama_jenis + " " + nama_series);

                LinearLayout kosongLayout = (LinearLayout) findViewById(R.id.layout_kosong);

                if (listAlarm.size() == 0) {
                    kosongLayout.setVisibility(LinearLayout.VISIBLE);
                } else {
                    kosongLayout.setVisibility(LinearLayout.GONE);
                }

                adData = new AdapterAlarm(AlarmActivity.this, listAlarm);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseAlarm> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Modul, error: " + t.getMessage());
                Toast.makeText(AlarmActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.alarm_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                Log.d(TAG, "onQueryTextChange: " + s);
                adData.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
