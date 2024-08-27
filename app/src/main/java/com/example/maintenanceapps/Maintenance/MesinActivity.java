package com.example.maintenanceapps.Maintenance;

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
import com.example.maintenanceapps.Adapter.AdapterMesin;
import com.example.maintenanceapps.Model.ModelMesin;
import com.example.maintenanceapps.Model.ResponseMesin2;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesinActivity extends AppCompatActivity {
    private static final String TAG = "MESIN_ACTIVITY";

    private RecyclerView rvData;
    private AdapterMesin adData;
    private RecyclerView.LayoutManager lmData;

    private LinearLayout kosongLayout;
    private TextView txtLayout, txtLine;

    private String idLine, namaLine;

    private List<ModelMesin> listMesin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesin);

        rvData = findViewById(R.id.rvMesin);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        kosongLayout = (LinearLayout) findViewById(R.id.layout_kosong);
        txtLayout = (TextView) findViewById(R.id.txt_layout);
        txtLine = (TextView) findViewById(R.id.txt_line);

        Intent intent = getIntent();
        idLine = intent.getStringExtra("id_line");
        namaLine = intent.getStringExtra("nama_line");

        txtLine.setText(namaLine);

        getDataMesin();
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

    public void getDataMesin() {
        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseMesin2> listData = aiData.getMesinByLine(idLine);

        listData.enqueue(new Callback<ResponseMesin2>() {
            @Override
            public void onResponse(Call<ResponseMesin2> call, Response<ResponseMesin2> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons Data Mesin");
                listMesin = response.body().getResult();

                if (listMesin.size() != 0) {
                    kosongLayout.setVisibility(LinearLayout.GONE);
                } else {
                    txtLayout.setText("Data mesin produksi kosong");
                    kosongLayout.setVisibility(LinearLayout.VISIBLE);
                }

                adData = new AdapterMesin(MesinActivity.this, listMesin);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseMesin2> call, Throwable t) {
                txtLayout.setText("Gagal menghubungi server: " + t.getMessage());
                kosongLayout.setVisibility(LinearLayout.VISIBLE);
                Toast.makeText(MesinActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}