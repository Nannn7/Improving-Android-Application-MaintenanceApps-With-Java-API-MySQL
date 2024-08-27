package com.example.maintenanceapps.Maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterLine;
import com.example.maintenanceapps.Capture;
import com.example.maintenanceapps.MainActivity;
import com.example.maintenanceapps.Model.ModelHistory;
import com.example.maintenanceapps.Model.ModelLine;
import com.example.maintenanceapps.Model.ModelMaintenance;
import com.example.maintenanceapps.Model.ModelMasalah;
import com.example.maintenanceapps.Model.ModelMesin;
import com.example.maintenanceapps.Model.ModelPenyebab;
import com.example.maintenanceapps.Model.ModelSparepart;
import com.example.maintenanceapps.Model.ResponseHistory;
import com.example.maintenanceapps.Model.ResponseLine;
import com.example.maintenanceapps.Model.ResponseMesin;
import com.example.maintenanceapps.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatActivity extends AppCompatActivity {
    private static final String TAG = "RIWAYAT_ACTIVITY";

    private CardView qr;
    private CardView id;
    private TextView tvQR;
    private TextView tvID;
    private ModelHistory dataHistory;
    private RecyclerView rvData;
    private AdapterLine adData;
    private RecyclerView.LayoutManager lmData;
    private LinearLayout kosongLayout;
    private TextView txtLayout;

    //    private List<ModelMaintenance> listMnt = new ArrayList<>();
    private List<ModelLine> listLine = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        rvData = findViewById(R.id.rvLine);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        kosongLayout = (LinearLayout) findViewById(R.id.layout_kosong);
        txtLayout = (TextView) findViewById(R.id.txt_layout);

//        getData();
        getDataLine();
    }

    public void getDataLine() {
        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseLine> listData = aiData.getLine();

        listData.enqueue(new Callback<ResponseLine>() {
            @Override
            public void onResponse(Call<ResponseLine> call, Response<ResponseLine> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons Data Line");
                listLine = response.body().getResult();

                if (listLine.size() != 0) {
                    kosongLayout.setVisibility(LinearLayout.GONE);
                } else {
                    txtLayout.setText("Data line produksi kosong");
                    kosongLayout.setVisibility(LinearLayout.VISIBLE);
                }

                adData = new AdapterLine(RiwayatActivity.this, listLine);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseLine> call, Throwable t) {
                txtLayout.setText("Gagal menghubungi server: " + t.getMessage());
                kosongLayout.setVisibility(LinearLayout.VISIBLE);
                Toast.makeText(RiwayatActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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

//    public void getData(){
//        // JUST TODAY
//        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
//        final Call<ResponseMaintenance> listData = aiData.getMntByStatus("2");
//
//        listData.enqueue(new Callback<ResponseMaintenance>() {
//            @Override
//            public void onResponse(Call<ResponseMaintenance> call, Response<ResponseMaintenance> response) {
//                String pesan = response.body().getPesan();
//                Log.d(TAG, "Respons Data Maintenance");
//
////                listMnt = response.body().getResult();
//                for (ModelMaintenance mnt : response.body().getResult()) {
//                    SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                    SimpleDateFormat formatOut = new SimpleDateFormat("yyyy-MM-dd");
//
//                    try {
//                        Calendar calendar = Calendar.getInstance();
//                        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
//                        formatOut.setTimeZone(tz);
//
//                        Date time = formatIn.parse(mnt.getTimeStop());
//                        String timeStop = formatOut.format(time);
//                        String timeNow = formatOut.format(calendar.getTime());
//                        Log.d(TAG, "timeNow: " + timeNow);
//
//                        if (timeNow.equals(timeStop)) {
//                            listMnt.add(mnt);
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if (listMnt.size() != 0) {
//                    kosongLayout.setVisibility(LinearLayout.GONE);
//                } else {
//                    txtLayout.setText("Tidak ada maintenance yang berjalan");
//                    kosongLayout.setVisibility(LinearLayout.VISIBLE);
//                }
//
//                adData = new AdapterMnt(RiwayatActivity.this, listMnt);
//                rvData.setAdapter(adData);
//                adData.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseMaintenance> call, Throwable t) {
//                txtLayout.setText("Gagal menghubungi server: " + t.getMessage());
//                kosongLayout.setVisibility(LinearLayout.VISIBLE);
//                Toast.makeText(RiwayatActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
