package com.example.maintenanceapps;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterMnt;
import com.example.maintenanceapps.Maintenance.CekMesinActivity;
import com.example.maintenanceapps.Maintenance.InputMslhActivity;
import com.example.maintenanceapps.Maintenance.PelaksanaanActivity;
import com.example.maintenanceapps.Maintenance.RiwayatActivity;
import com.example.maintenanceapps.Model.ModelMaintenance;
import com.example.maintenanceapps.Model.ModelMesin;
import com.example.maintenanceapps.Model.PrefConfig;
import com.example.maintenanceapps.Model.ResponseMaintenance;
import com.example.maintenanceapps.Model.ResponseMesin;
import com.example.maintenanceapps.Model.ResponseSet;
import com.example.maintenanceapps.Modul.InputPdfActivity;
import com.example.maintenanceapps.Modul.ModulActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LoginActivity.OnLoginFormActivityListener {
    private static final String TAG = "MAIN_ACTIVITY";

    private RecyclerView rvData, rvDoMnt;
    private RecyclerView.Adapter adData, adDoMnt;
    private RecyclerView.LayoutManager lmData, lmDoMnt;
    private TextView tvHello, tvNama, tvDoMnt, tvLastUpdate;
    private CardView menuModul;
    private CardView menuCheckMesin;
    private CardView menuDoMnt;
    private CardView menuDoneMnt;
    private CardView menuMulaiMnt;
    private CardView menuInputMslh;
    private CardView refresh;
    private CardView InputPDF;

    private List<ModelMaintenance> listMnt = new ArrayList<>();
    private List<ModelMaintenance> listDoMnt = new ArrayList<>();
    private ModelMesin dataMesin;
    private ModelMaintenance dataMnt;

    public static PrefConfig prefConfig;

    JobScheduler scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        startService(new Intent(this, MntService.class));

        prefConfig = new PrefConfig(this);
        if (!prefConfig.readLoginStatus()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            tvNama = (TextView) findViewById(R.id.txt_nama_user);
            tvNama.setText("Selamat datang " + MainActivity.prefConfig.readName());
        }

        tvLastUpdate = (TextView) findViewById(R.id.last_update);
        tvHello = (TextView) findViewById(R.id.txtHello);

        tvDoMnt = (TextView) findViewById(R.id.txt_do_mnt);
        rvDoMnt = findViewById(R.id.rvEndMnt);
        lmDoMnt = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDoMnt.setLayoutManager(lmDoMnt);
        getDataDoMnt();

        rvData = findViewById(R.id.rvMnt);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        getData();

        menuModul = (CardView) findViewById(R.id.menu_check_modul);
        menuModul.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ModulActivity.class);
            startActivity(intent);
        });

        menuCheckMesin = (CardView) findViewById(R.id.menu_check_mesin);
        menuCheckMesin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CekMesinActivity.class);
            startActivity(intent);
        });

        menuDoMnt = (CardView) findViewById(R.id.menu_do_maintenance);
        menuDoMnt.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PelaksanaanActivity.class);
            startActivity(intent);
        });

        menuDoneMnt = (CardView) findViewById(R.id.menu_done_maintenance);
        menuDoneMnt.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RiwayatActivity.class);
            startActivity(intent);
        });

        menuInputMslh = (CardView) findViewById(R.id.menu_input_masalah);
        menuInputMslh.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InputMslhActivity.class);
            startActivity(intent);
        });

        menuMulaiMnt = (CardView) findViewById(R.id.menu_mulai_mnt);
        menuMulaiMnt.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View mntView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.comp_start_mnt, null);

            CardView menuScan = (CardView) mntView.findViewById(R.id.menu_1);
            CardView menuType = (CardView) mntView.findViewById(R.id.menu_2);
            TextView judul = (TextView) mntView.findViewById(R.id.txt_judul);
            TextView menu1 = (TextView) mntView.findViewById(R.id.txt_menu_1);
            TextView menu2 = (TextView) mntView.findViewById(R.id.txt_menu_2);

            judul.setText("Pilih untuk memulai maintenance");
            menu1.setText("Scan QRCode");
            menu2.setText("Ketik ID Mesin");

            menuScan.setOnClickListener(view1 -> {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        MainActivity.this
                );

                intentIntegrator.setPrompt("Gunakan tombol volume up untuk hidupkan flash");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            });

            menuType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    View mesin = LayoutInflater.from(getApplicationContext()).inflate(R.layout.comp_input_mesin, null);
                    final EditText idMesin = (EditText) mesin.findViewById(R.id.id_mesin);
                    alert.setPositiveButton("Mulai", (dialogInterface, i) -> {
                        if (idMesin.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "ID Mesin harus diisi!", Toast.LENGTH_SHORT).show();
                        } else {
                            getMesin(idMesin.getText().toString());
                        }
                    });
                    alert.setNegativeButton("Batal", (dialogInterface, i) -> dialogInterface.dismiss());

                    alert.setView(mesin);
                    alert.setCancelable(false);
                    alert.show();
                }
            });

            builder.setView(mntView);
            builder.setCancelable(true);
            builder.show();
        });

        refresh = (CardView) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataDoMnt();
                getData();
            }
        });

        /* InputPDF = (CardView) findViewById(R.id.cvInputPdf);
        InputPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputPdfActivity.class);
                startActivity(intent);
            }
        }); */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        if (intentResult.getContents() != null) {
            getMesin(intentResult.getContents());
        } else {
//            Toast.makeText(getApplicationContext(), "Pastikan anda scan QRCode", Toast.LENGTH_SHORT).show();
        }
    }

    public void getMesin(String IDMesin) {

        final APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseMesin> mesin = aiData.getMesinByID(IDMesin);

        mesin.enqueue(new Callback<ResponseMesin>() {
            @Override
            public void onResponse(Call<ResponseMesin> call, Response<ResponseMesin> response) {
                Log.d(TAG, "Respons Data Mesin");
                dataMesin = response.body().getMesin();
                dataMnt = response.body().getMaintenance();

                if (response.body().getKode().equals("0")) {
                    Toast.makeText(getApplicationContext(), "ID Mesin tidak ditemukan", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    View mesinView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.comp_mesin1, null);

                    TextView txt_judul = (TextView) mesinView.findViewById(R.id.txt_judul_mnt_1);

                    TextView txt_timeerror = (TextView) mesinView.findViewById(R.id.time_error_1);
                    TextView txt_nomesin = (TextView) mesinView.findViewById(R.id.nomesin_1);
                    TextView txt_proses = (TextView) mesinView.findViewById(R.id.proses_1);
                    TextView txt_line = (TextView) mesinView.findViewById(R.id.line_1);
                    TextView txt_model = (TextView) mesinView.findViewById(R.id.model_1);
                    TextView txt_manipulator = (TextView) mesinView.findViewById(R.id.manipulator_1);
                    TextView txt_fanmotor = (TextView) mesinView.findViewById(R.id.fanmotor_1);
                    TextView txt_kontroller = (TextView) mesinView.findViewById(R.id.kontroller_1);
                    TextView txt_noserial = (TextView) mesinView.findViewById(R.id.noserial_1);
                    TextView txt_umur = (TextView) mesinView.findViewById(R.id.umur_1);

                    SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatOut = new SimpleDateFormat("hh:mm | dd MMM yyyy");

                    if (response.body().getError()) {
                        txt_judul.setText("Breakdown Maintenance");

                        try {
                            Date time = formatIn.parse(dataMnt.getTimeError());
                            txt_timeerror.setText(formatOut.format(time));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        txt_nomesin.setText(dataMesin.getNoMesin());
                        txt_proses.setText(dataMesin.getNama_Proses());
                        txt_line.setText(dataMesin.getNama_Line());
                        txt_model.setText(dataMesin.getModel());
                        txt_manipulator.setText(dataMesin.getManipulator());
                        txt_fanmotor.setText(dataMesin.getFanMotor());
                        txt_kontroller.setText(dataMesin.getKontroller());
                        txt_noserial.setText(dataMesin.getNoSerial());

                        String year = dataMesin.getTglKedatangan().substring(0, 4);
                        String month = dataMesin.getTglKedatangan().substring(5, 7);
                        String date = dataMesin.getTglKedatangan().substring(8, 10);

                        txt_umur.setText(getAge(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date)) + " tahun");

                        builder.setView(mesinView);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Perbaiki", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                Call<ResponseSet> setMnt = aiData.setStatusMnt(dataMnt.getID(), MainActivity.prefConfig.readID(), "1");

                                setMnt.enqueue(new Callback<ResponseSet>() {
                                    @Override
                                    public void onResponse(Call<ResponseSet> call, Response<ResponseSet> response) {
                                        Log.d(TAG, "Respons Data Update Mnt : " + response.body().getKode());

                                        if (response.body().getKode().equals("1")) {
                                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                            alert.setTitle("Berhasil!");
                                            alert.setMessage("Silahkan lakukan perbaikan pada mesin " + dataMesin.getNoMesin()
                                                    + " proses " + dataMesin.getNama_Proses()
                                                    + " pada " + dataMesin.getNama_Line());
                                            alert.setCancelable(false);
                                            alert.setPositiveButton("Laksanakan", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    getData();
                                                    getDataDoMnt();
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            alert.show();
                                        } else {
                                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                            alert.setTitle("Gagal!");
                                            alert.setMessage("Gagal mencatat data untuk pencatatan kegiatan maintenance");
                                            alert.setCancelable(false);
                                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            alert.show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseSet> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Gagal menghubungi server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();

                    } else {

                        txt_judul.setText("Preventive Maintenance");

                        txt_timeerror.setText("preventive");

                        txt_nomesin.setText(dataMesin.getNoMesin());
                        txt_proses.setText(dataMesin.getNama_Proses());
                        txt_line.setText(dataMesin.getNama_Line());
                        txt_model.setText(dataMesin.getModel());
                        txt_manipulator.setText(dataMesin.getManipulator());
                        txt_fanmotor.setText(dataMesin.getFanMotor());
                        txt_kontroller.setText(dataMesin.getKontroller());
                        txt_noserial.setText(dataMesin.getNoSerial());

                        String year = dataMesin.getTglKedatangan().substring(0, 4);
                        String month = dataMesin.getTglKedatangan().substring(5, 7);
                        String date = dataMesin.getTglKedatangan().substring(8, 10);

                        txt_umur.setText(getAge(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date)) + " tahun");

                        builder.setView(mesinView);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Perbaiki", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                Call<ResponseSet> setMnt = aiData.setPreventive(dataMesin.getID(), MainActivity.prefConfig.readID());

                                setMnt.enqueue(new Callback<ResponseSet>() {
                                    @Override
                                    public void onResponse(Call<ResponseSet> call, Response<ResponseSet> response) {
                                        Log.d(TAG, "Respons Data Update Mnt : " + response.body().getKode());

                                        if (response.body().getKode().equals("1")) {
                                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                            alert.setTitle("Berhasil!");
                                            alert.setMessage("Silahkan lakukan perbaikan pada mesin " + dataMesin.getNoMesin()
                                                    + " proses " + dataMesin.getNama_Proses()
                                                    + " pada " + dataMesin.getNama_Line());
                                            alert.setCancelable(false);
                                            alert.setPositiveButton("Laksanakan", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    getData();
                                                    getDataDoMnt();
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            alert.show();
                                        } else {
                                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                            alert.setTitle("Gagal!");
                                            alert.setMessage("Gagal mencatat data untuk pencatatan kegiatan maintenance");
                                            alert.setCancelable(false);
                                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            alert.show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseSet> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Gagal menghunbungi server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMesin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal menghunbungi server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData(){
        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseMaintenance> listData = aiData.getMntByStatus("0");

        listData.enqueue(new Callback<ResponseMaintenance>() {
            @Override
            public void onResponse(Call<ResponseMaintenance> call, Response<ResponseMaintenance> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons Data Maintenance belum ditangani");

                listMnt = response.body().getResult();

                if (listMnt.size() == 0) {
                    tvHello.setText("Tidak ada mesin breakdown");
                } else {
                    tvHello.setText("Breakdown Mesin");
                }

                adData = new AdapterMnt(MainActivity.this, listMnt);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseMaintenance> call, Throwable t) {
                tvHello.setText("Gagal Menghubungi Server : " + t.getCause());
                Log.d(TAG, "getMntByStatus: Gagal Respons Data Maintenance belum ditangani");
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDataDoMnt(){
        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseMaintenance> listData = aiData.getMntByStatus2(MainActivity.prefConfig.readID(), "1");

        listData.enqueue(new Callback<ResponseMaintenance>() {
            @Override
            public void onResponse(Call<ResponseMaintenance> call, Response<ResponseMaintenance> response) {
                String pesan = response.body().getPesan();
                Log.d(TAG, "Respons Data Pelaksanaan Maintenance: " + pesan);

                listDoMnt = response.body().getResult();

                if (listDoMnt.size() == 0) {
                    tvDoMnt.setVisibility(View.GONE);
                } else {
                    tvDoMnt.setVisibility(View.VISIBLE);
                }

                adDoMnt = new AdapterMnt(MainActivity.this, listDoMnt);
                rvDoMnt.setAdapter(adDoMnt);
                adDoMnt.notifyDataSetChanged();

                tvLastUpdate.setText("Update Terakhir : " + android.text.format.DateFormat.format("dd-MM-yyyy HH:mm:ss", new java.util.Date()));
            }

            @Override
            public void onFailure(Call<ResponseMaintenance> call, Throwable t) {
                tvHello.setText("Gagal Menghubungi Server : " + t.getCause());
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exit_menu, menu);
        return true;
    }

    @Override
    public void performLogin(String nama) {
        prefConfig.writeName(nama);
//        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//        startActivity(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getDataNetwork();
    }

    public void logout(MenuItem menu) {

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Logout");
        alert.setMessage("Apakah anda yakin untuk logout akun anda?");
        alert.setCancelable(false);
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                prefConfig.writeLoginStatus(false);
                prefConfig.writeID("User");
                prefConfig.writeName("User");
                prefConfig.writeRole("User");

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    public void startJobNotif() {
        ComponentName componentName = new ComponentName(this, MntJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPeriodic(15 * 60 * 1000)
                .setRequiresCharging(false)
                .setPersisted(true)
                .build();

//        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job Scheduled");
        } else {
            Log.d(TAG, "Job Scheduled failed");
        }
    }

//    public void startJob(View v) {
//        ComponentName componentName = new ComponentName(this, MntJobService.class);
//        JobInfo info = new JobInfo.Builder(123, componentName)
//                .setRequiresCharging(true)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
//                .setPersisted(true)
//                .setPeriodic(15 * 60 * 1000)
//                .build();
//
//        int resultCode = scheduler.schedule(info);
//        if (resultCode == JobScheduler.RESULT_SUCCESS) {
//            Log.d(TAG, "Job Scheduled");
//        } else {
//            Log.d(TAG, "Job Scheduled failed");
//        }
//    }

    public void stopJob(View v) {
//        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job Canceled");
    }
}
