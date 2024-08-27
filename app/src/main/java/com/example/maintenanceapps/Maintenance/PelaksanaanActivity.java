package com.example.maintenanceapps.Maintenance;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterMnt;
import com.example.maintenanceapps.Model.ModelMaintenance;
import com.example.maintenanceapps.Model.ResponseMaintenance;
import com.example.maintenanceapps.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PelaksanaanActivity extends AppCompatActivity {
    private static final String TAG = "PELAKSANAAN_ACTIVITY";

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private LinearLayout kosongLayout;
    private TextView txtLayout;

    private List<ModelMaintenance> listMnt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelaksanaan);

        rvData = findViewById(R.id.rvDoMnt);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        kosongLayout = (LinearLayout) findViewById(R.id.layout_kosong);
        txtLayout = (TextView) findViewById(R.id.txt_layout);

        getData();
    }

    public void getData(){
        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseMaintenance> listData = aiData.getMntByStatus("1");

        listData.enqueue(new Callback<ResponseMaintenance>() {
            @Override
            public void onResponse(Call<ResponseMaintenance> call, Response<ResponseMaintenance> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons Data Maintenance");

                listMnt = response.body().getResult();

                if (listMnt.size() != 0) {
                    kosongLayout.setVisibility(LinearLayout.GONE);
                } else {
                    txtLayout.setText("Tidak ada maintenance yang berjalan");
                    kosongLayout.setVisibility(LinearLayout.VISIBLE);
                }

                adData = new AdapterMnt(PelaksanaanActivity.this, listMnt);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseMaintenance> call, Throwable t) {
                txtLayout.setText("Gagal menghubungi server: " + t.getMessage());
                kosongLayout.setVisibility(LinearLayout.VISIBLE);
                Toast.makeText(PelaksanaanActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
