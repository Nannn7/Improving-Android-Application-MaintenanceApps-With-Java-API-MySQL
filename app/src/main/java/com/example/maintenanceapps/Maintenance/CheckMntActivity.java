package com.example.maintenanceapps.Maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterMntMasalah;
import com.example.maintenanceapps.Model.ModelMaintenance;
import com.example.maintenanceapps.Model.ModelMasalah;
import com.example.maintenanceapps.Model.ResponseMasalah;
import com.example.maintenanceapps.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckMntActivity extends AppCompatActivity {
    private static final String TAG = "CHECK_MNT_ACTIVITY";

    private RecyclerView rvData;
    //    private RecyclerView.Adapter adData;
    private AdapterMntMasalah adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelMasalah> listMasalah;
    CardView noMasalah;
    String id_mnt, id_series, id_mesin, id_line, jenis_mnt;

    private ModelMaintenance dataMnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_mnt);


        Intent intent = getIntent();
        id_mnt = intent.getStringExtra("id_mnt");
        id_series = intent.getStringExtra("id_series");
        id_mesin = intent.getStringExtra("id_mesin");
        id_line = intent.getStringExtra("id_line");
        jenis_mnt = intent.getStringExtra("jenis_mnt");

        noMasalah = (CardView) findViewById(R.id.btn_nomasalah);
        noMasalah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EndMntActivity.class);

                Bundle args = new Bundle();
                args.putString("id_mnt", id_mnt);
                args.putString("id_series", id_series);
                args.putString("id_mesin", id_mesin);
                args.putString("id_line", id_line);
                args.putString("jenis_mnt", jenis_mnt);
                args.putString("id_masalah", null);
                intent.putExtras(args);

                view.getContext().startActivity(intent);
            }
        });

        rvData = findViewById(R.id.rvMasalah);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        getData();
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

    public void getData(){

        final APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseMasalah> listData = aiData.getMasalah();

        listData.enqueue(new Callback<ResponseMasalah>() {
            @Override
            public void onResponse(Call<ResponseMasalah> call, Response<ResponseMasalah> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons: Data Masalah mekanik");

                listMasalah = response.body().getResult();

                LinearLayout kosongLayout = (LinearLayout) findViewById(R.id.layout_kosong);

                if (listMasalah.size() == 0) {
                    kosongLayout.setVisibility(LinearLayout.VISIBLE);
                } else {
                    kosongLayout.setVisibility(LinearLayout.GONE);

                    adData = new AdapterMntMasalah(CheckMntActivity.this, listMasalah, id_mnt, id_series, id_mesin, id_line, jenis_mnt);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseMasalah> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Masalah Mekanikal, error: " + t.getMessage());
                Toast.makeText(CheckMntActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
