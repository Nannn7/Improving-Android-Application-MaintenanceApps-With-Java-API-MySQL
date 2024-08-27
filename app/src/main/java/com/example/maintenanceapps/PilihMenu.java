package com.example.maintenanceapps;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Model.ModelMesin;
import com.example.maintenanceapps.Model.ModelSparepart;
import com.example.maintenanceapps.Model.ResponseMesin;
import com.example.maintenanceapps.Model.ResponseSparepart;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihMenu extends AppCompatActivity {

    private static final String TAG = "PILIH_MENU";
    private CardView fungsi1;
    private TextView tema, text1;
    private ModelMesin dataMesin;
    private android.app.AlertDialog builder;
    private View mesin;
    private AlertDialog alert;
    private ModelSparepart dataSparepart;

    public PilihMenu() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        fungsi1 = findViewById(R.id.fungsi_1);
        tema = (TextView) findViewById(R.id.txt_tema);
        TextView text1 = (TextView) findViewById(R.id.txt_fungsi_1);

        tema.setText("Check Mesin");
        text1.setText("Scan QR Code");

        fungsi1.setOnClickListener(view -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(PilihMenu.this);

            intentIntegrator.setPrompt("Gunakan tombol volume up untuk hidupkan flash");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(Capture.class);
            intentIntegrator.initiateScan();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult.getContents() != null) {
            getMesin(intentResult.getContents());
        } else {
            Toast.makeText(getApplicationContext(), "Pastikan anda scan QRCode", Toast.LENGTH_SHORT).show();
        }
    }


    private void getMesin(String IDMesin) {
        final APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        final Call<ResponseMesin> mesin = aiData.getMesinByID(IDMesin);

        mesin.enqueue(new Callback<ResponseMesin>() {
            @Override
            public void onResponse(Call<ResponseMesin> call, Response<ResponseMesin> response) {
                Log.d(TAG, "onResponse: Response Data Mesin");
                dataMesin = response.body().getMesin();
                dataSparepart = response.body().getSparepart();

                if (response.body().getKode().equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihMenu.this);
                    View mesinView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.comp_mesin, null);

                    TextView txt_nomesin = (TextView) mesinView.findViewById(R.id.nomesin);
                    TextView txt_proses = (TextView) mesinView.findViewById(R.id.proses);
                    TextView txt_line = (TextView) mesinView.findViewById(R.id.line);
                    TextView txt_model = (TextView) mesinView.findViewById(R.id.model);
                    TextView txt_manipulator = (TextView) mesinView.findViewById(R.id.manipulator);
                    TextView txt_fanmotor = (TextView) mesinView.findViewById(R.id.fanmotor);
                    TextView txt_kontroller = (TextView) mesinView.findViewById(R.id.kontroller);
                    TextView txt_noserial = (TextView) mesinView.findViewById(R.id.noserial);
                    TextView txt_umur = (TextView) mesinView.findViewById(R.id.umur);
                    TextView txt_status = (TextView) mesinView.findViewById(R.id.status);
                    TextView txt_sparepart = (TextView) mesinView.findViewById(R.id.sparepart);


                    SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatOut = new SimpleDateFormat("hh:mm | dd MMM yyyy");

                    txt_nomesin.setText(dataMesin.getNoMesin());
                    txt_proses.setText(dataMesin.getNama_Proses());
                    txt_line.setText(dataMesin.getNama_Line());
                    txt_model.setText(dataMesin.getModel());
                    txt_manipulator.setText(dataMesin.getManipulator());
                    txt_fanmotor.setText(dataMesin.getFanMotor());
                    txt_kontroller.setText(dataMesin.getKontroller());
                    txt_noserial.setText(dataMesin.getNoSerial());
                    txt_status.setText(dataMesin.getStatus());
                    txt_sparepart.setText(dataMesin.getNamaSparepart());


                    String year = dataMesin.getTglKedatangan().substring(0, 4);
                    String month = dataMesin.getTglKedatangan().substring(5, 7);
                    String date = dataMesin.getTglKedatangan().substring(8, 10);

                    txt_umur.setText(getAge(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date)) + " tahun");

                    builder.setView(mesinView);
                    builder.setCancelable(true);
                    builder.show();
                } else {
                    Toast.makeText(getApplicationContext(), "ID Mesin tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMesin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal menghubungi server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getAge(int year, int month, int day) {
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
}