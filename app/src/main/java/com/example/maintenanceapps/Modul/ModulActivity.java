package com.example.maintenanceapps.Modul;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterModul;
import com.example.maintenanceapps.Model.ModelModul;
import com.example.maintenanceapps.Model.ResponseModul;
import com.example.maintenanceapps.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModulActivity extends AppCompatActivity {
    private static final String TAG = "MODUL_ACTIVITY";

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<ModelModul> listModul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modul);

        rvData = findViewById(R.id.rvModul);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        getData();
    }

    public void getData(){
        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseModul> listData = aiData.getModul();

        listData.enqueue(new Callback<ResponseModul>() {
            @Override
            public void onResponse(Call<ResponseModul> call, Response<ResponseModul> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons: Data Modul");

                listModul = response.body().getResult();

                adData = new AdapterModul(ModulActivity.this, listModul);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModul> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Modul, error: " + t.getMessage());
                Toast.makeText(ModulActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
