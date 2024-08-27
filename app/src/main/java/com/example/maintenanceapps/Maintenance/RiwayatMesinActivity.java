package com.example.maintenanceapps.Maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterMntMesin;
import com.example.maintenanceapps.Model.ModelMaintenance;
import com.example.maintenanceapps.Model.ResponseMesinMnt;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatMesinActivity extends AppCompatActivity {
    private static final String TAG = "RIWAYAT_MESIN_ACTIVITY";

    private RecyclerView rvData;
    private AdapterMntMesin adData;
    private RecyclerView.LayoutManager lmData;

    private LinearLayout kosongLayout;
    private TextView txtLayout, txtLine, txtModul;

    private String idMesin, namaLine, noMesin, modul, proses;

    String[] filter = {"Mekanikal", "Display Alarm", "Bagian Mesin", "Sparepart"};
    int choosedFilter;

    private List<ModelMaintenance> listMnt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_mesin);

        rvData = findViewById(R.id.rvMnt);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        kosongLayout = (LinearLayout) findViewById(R.id.layout_kosong);
        txtLayout = (TextView) findViewById(R.id.txt_layout);
        txtLine = (TextView) findViewById(R.id.txt_line);
        txtModul = (TextView) findViewById(R.id.txt_modul);

        Intent intent = getIntent();
        idMesin = intent.getStringExtra("id_mesin");
        namaLine = intent.getStringExtra("nama_line");
        noMesin = intent.getStringExtra("no_mesin");
        proses = intent.getStringExtra("proses");
        modul = intent.getStringExtra("modul");

        txtLine.setText(namaLine + " | Mesin " + noMesin + " Proses " + proses);

        txtModul.setText("Modul " + modul);

        Spinner spin = (Spinner) findViewById(R.id.spinnerFilter);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, filter);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        choosedFilter = 0;
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "positionSpinner: " + i);
                choosedFilter = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EditText search = (EditText) findViewById(R.id.input_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged: " + charSequence);
                switch (choosedFilter) {
                    case 0: adData.getFilter().filter(charSequence); break;
                    case 1: adData.getFilterDisplayAlarm().filter(charSequence); break;
                    case 2: adData.getFilterBagianMesin().filter(charSequence); break;
                    case 3: adData.getFilterSparepart().filter(charSequence); break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getDataMnt();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.alarm_menu, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                Log.d(TAG, "onQueryTextChange: " + s);
//                adData.clearListData();
//                adData.getFilter().filter(s);
//                Log.d(TAG, "getItemCount mekanik: " + adData.getItemCount());
//                if (adData.getItemCount() < 1) {
//                    adData.getFilterDisplayAlarm().filter(s);
//                    Log.d(TAG, "getItemCount DisplayAlarm: " + adData.getItemCount());
//                    if (adData.getItemCount() < 1) {
//                        adData.getFilterNamaAlarm().filter(s);
//                        Log.d(TAG, "getItemCount NamaAlarm: " + adData.getItemCount());
//                        if (adData.getItemCount() < 1) {
//                            adData.getFilterBagianMesin().filter(s);
//                            Log.d(TAG, "getItemCount BagianMesin: " + adData.getItemCount());
//                            if (adData.getItemCount() < 1) {
//                                adData.getFilterSparepart().filter(s);
//                                Log.d(TAG, "getItemCount Sparepart: " + adData.getItemCount());
//                            }
//                        }
//                    }
//                }
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }

    public void getDataMnt() {
        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseMesinMnt> listData = aiData.getMntByMesin(idMesin);

        listData.enqueue(new Callback<ResponseMesinMnt>() {
            @Override
            public void onResponse(Call<ResponseMesinMnt> call, Response<ResponseMesinMnt> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons Data Mnt by Mesin");
                listMnt = response.body().getMaintenance();

                if (listMnt.size() != 0) {
                    kosongLayout.setVisibility(LinearLayout.GONE);
                } else {
                    txtLayout.setText("Data maintenance mesin ini kosong");
                    kosongLayout.setVisibility(LinearLayout.VISIBLE);
                }

                adData = new AdapterMntMesin(RiwayatMesinActivity.this, listMnt);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseMesinMnt> call, Throwable t) {
                txtLayout.setText("Gagal menghubungi server");
                kosongLayout.setVisibility(LinearLayout.VISIBLE);
                Toast.makeText(RiwayatMesinActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}