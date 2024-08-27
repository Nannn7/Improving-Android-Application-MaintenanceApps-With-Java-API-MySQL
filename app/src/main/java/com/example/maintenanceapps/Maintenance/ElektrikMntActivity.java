package com.example.maintenanceapps.Maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterMntMasalah;
import com.example.maintenanceapps.Model.ModelMasalah;
import com.example.maintenanceapps.Model.ResponseMasalah;
import com.example.maintenanceapps.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElektrikMntActivity extends AppCompatActivity {
    private static final String TAG = "ELEKTRIK_MNT_ACTIVITY";

    private RecyclerView rvData;
    //    private RecyclerView.Adapter adData;
    private AdapterMntMasalah adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelMasalah> listMasalah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elektrik_mnt);

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
                Log.d(TAG, "Respons: Data Definisi");

                listMasalah = response.body().getResult();

                LinearLayout kosongLayout = (LinearLayout) findViewById(R.id.layout_kosong);

                Log.d(TAG, "listMasalah: " +listMasalah.size());
                if (listMasalah.size() == 0) {
                    kosongLayout.setVisibility(LinearLayout.VISIBLE);
                } else {
                    kosongLayout.setVisibility(LinearLayout.GONE);

                    Intent intent = getIntent();
                    String id_mnt = intent.getStringExtra("id_mnt");

//                    adData = new AdapterMntMasalah(ElektrikMntActivity.this, listMasalah, id_mnt);
//                    rvData.setAdapter(adData);
//                    adData.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseMasalah> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Modul, error: " + t.getMessage());
                Toast.makeText(ElektrikMntActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
