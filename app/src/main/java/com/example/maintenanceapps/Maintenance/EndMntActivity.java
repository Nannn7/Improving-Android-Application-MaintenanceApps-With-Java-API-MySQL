package com.example.maintenanceapps.Maintenance;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Adapter.AdapterAutoCompAlarm;
import com.example.maintenanceapps.Adapter.AdapterAutoCompBagianMesin;
import com.example.maintenanceapps.Adapter.AdapterAutoCompDefinisi;
import com.example.maintenanceapps.Adapter.AdapterAutoCompPenyebab;
import com.example.maintenanceapps.Adapter.AdapterAutoCompSparepart;
import com.example.maintenanceapps.Adapter.AdapterFotoMnt;
import com.example.maintenanceapps.MainActivity;
import com.example.maintenanceapps.Model.ModelAlarm;
import com.example.maintenanceapps.Model.ModelBagianMesin;
import com.example.maintenanceapps.Model.ModelDefinisi;
import com.example.maintenanceapps.Model.ModelMasalah;
import com.example.maintenanceapps.Model.ModelPenyebab;
import com.example.maintenanceapps.Model.ModelSparepart;
import com.example.maintenanceapps.Model.ResponseAlarm;
import com.example.maintenanceapps.Model.ResponseBagianMesin;
import com.example.maintenanceapps.Model.ResponseDefinisi;
import com.example.maintenanceapps.Model.ResponsePenyebab;
import com.example.maintenanceapps.Model.ResponseSet;
import com.example.maintenanceapps.Model.ResponseSparepart;
import com.example.maintenanceapps.R;
import com.example.maintenanceapps.Upload;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndMntActivity extends AppCompatActivity {
    private static final String TAG = "END_MNT_ACTIVITY";

    ProgressDialog loading;

    private TextView tvMasalah, tvPenyebab, tvPenanganan, jdlPenyebab, jdlPenanganan, validate_tek;
    private CardView btnSelesai, btnCam;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private Button btnTeknikal;
    private LinearLayout layoutTeknikal;

    private TextInputLayout tilAlarm;
    private AutoCompleteTextView actAlarm;
    private TextInputLayout tilDefinisi;
    private AutoCompleteTextView actDefinisi;
    private TextInputLayout tilPenyebab;
    private AutoCompleteTextView actPenyebab;
    private TextInputLayout tilSparepart;
    private AutoCompleteTextView actSparepart;
    private TextInputLayout tilBagianMesin;
    private AutoCompleteTextView actBagianMesin;

    List<ModelAlarm> arrayAlarm;
    AdapterAutoCompAlarm adapterAlarm;
    List<ModelDefinisi> arrayDefinisi;
    AdapterAutoCompDefinisi adapterDefinisi;
    List<ModelPenyebab> arrayPenyebab;
    AdapterAutoCompPenyebab adapterPenyebab;
    List<ModelSparepart> arraySparepart;
    AdapterAutoCompSparepart adapterSparepart;
    List<ModelBagianMesin> arrayBagianMesin;
    AdapterAutoCompBagianMesin adapterBagianMesin;

    private RecyclerView rvFoto;
    private AdapterFotoMnt adFoto;
    private RecyclerView.LayoutManager lmFoto;

    private String id_mnt, id_series, id_masalah, id_mesin, id_line, jenis_mnt;
    private String clickedAlarm, clickedDefinisi, clickedPenyebab, clickedBagianMesin, clickedSparepart;
    private boolean isTeknikal, isSparepart;
    File photoFile = null;
    private static final String IMAGE_DIRECTORY_NAME = "FIM_temp";
    static final int CAPTURE_IMAGE_REQUEST = 1;
    String mCurrentPhotoPath;

    List<File> listFile = new ArrayList<>();

//    String fileUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_mnt);

        final APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);

        rvFoto = (RecyclerView) findViewById(R.id.rvFoto);
        lmFoto = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFoto.setLayoutManager(lmFoto);
        loadDataFoto();

        tvMasalah = (TextView) findViewById(R.id.txt_masalah);
        tvPenyebab = (TextView) findViewById(R.id.txt_penyebab);
        tvPenanganan = (TextView) findViewById(R.id.txt_penanganan);
        jdlPenyebab = (TextView) findViewById(R.id.jdl_penyebab);
        jdlPenanganan = (TextView) findViewById(R.id.jdl_penanganan);

        Intent intent = getIntent();
        id_mnt = intent.getStringExtra("id_mnt");
        id_series = intent.getStringExtra("id_series");
        id_masalah = intent.getStringExtra("id_masalah");
        id_mesin = intent.getStringExtra("id_mesin");
        jenis_mnt = intent.getStringExtra("jenis_mnt");
        id_line = intent.getStringExtra("id_line");
        Log.d(TAG, "onCreate: id_series: " + id_series);




        btnTeknikal = (Button) findViewById(R.id.btn_teknikal);
        layoutTeknikal = (LinearLayout) findViewById(R.id.layout_teknikal);
        validate_tek = (TextView) findViewById(R.id.validate_tek);



        tilAlarm = (TextInputLayout) findViewById(R.id.til_alarm);
        actAlarm = (AutoCompleteTextView) findViewById(R.id.act_alarm);
        tilDefinisi = (TextInputLayout) findViewById(R.id.til_definisi);
        actDefinisi = (AutoCompleteTextView) findViewById(R.id.act_definisi);
        tilPenyebab = (TextInputLayout) findViewById(R.id.til_penyebab);
        actPenyebab = (AutoCompleteTextView) findViewById(R.id.act_penyebab);

        if (id_masalah != null) {
            layoutTeknikal.setVisibility(View.GONE);
            validate_tek.setVisibility(View.GONE);
            btnTeknikal.setVisibility(View.VISIBLE);
        } else {
            isSparepart = true;
            isTeknikal = true;
            layoutTeknikal.setVisibility(View.VISIBLE);
            validate_tek.setVisibility(View.VISIBLE);
            loadAlarm(id_series);
            tilDefinisi.setVisibility(View.GONE);
            tilPenyebab.setVisibility(View.GONE);
            btnTeknikal.setVisibility(View.GONE);
        }


        btnTeknikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutTeknikal.setVisibility(View.VISIBLE);

                tilDefinisi.setVisibility(View.GONE);
                tilPenyebab.setVisibility(View.GONE);
                btnTeknikal.setVisibility(View.GONE);
                loadAlarm(id_series);
            }
        });




        if (id_masalah == null){
            tvMasalah.setText("Tidak ada masalah mekanikal");
            tvPenyebab.setVisibility(View.GONE);
            jdlPenyebab.setVisibility(View.GONE);
            tvPenanganan.setVisibility(View.GONE);
            jdlPenanganan.setVisibility(View.GONE);
        } else {
            Call<ModelMasalah> dataMasalah = aiData.getMasalahData(id_masalah);
            dataMasalah.enqueue(new Callback<ModelMasalah>() {
                @Override
                public void onResponse(Call<ModelMasalah> call, Response<ModelMasalah> response) {
                    tvPenyebab.setVisibility(View.VISIBLE);
                    jdlPenyebab.setVisibility(View.VISIBLE);
                    tvPenanganan.setVisibility(View.VISIBLE);
                    jdlPenanganan.setVisibility(View.VISIBLE);
                    tvMasalah.setText(response.body().getMasalah());
                    tvPenyebab.setText(response.body().getPenyebab());
                    tvPenanganan.setText(response.body().getPenanganan());
                }

                @Override
                public void onFailure(Call<ModelMasalah> call, Throwable t) {
                    tvMasalah.setText("Gagal mendapatkan data");
                    tvPenyebab.setText("Gagal mendapatkan data");
                    tvPenanganan.setText("Gagal mendapatkan data");
                }
            });
        }





        tilSparepart = (TextInputLayout) findViewById(R.id.til_sparepart);
        actSparepart = (AutoCompleteTextView) findViewById(R.id.act_sparepart);
        tilSparepart.setVisibility(View.GONE);

        tilBagianMesin = (TextInputLayout) findViewById(R.id.til_bagian_mesin);
        actBagianMesin = (AutoCompleteTextView) findViewById(R.id.act_bagian_mesin);
        Call<ResponseBagianMesin> dataBagianMesin = aiData.getBagianMesin(id_mesin);
        dataBagianMesin.enqueue(new Callback<ResponseBagianMesin>() {
            @Override
            public void onResponse(Call<ResponseBagianMesin> call, Response<ResponseBagianMesin> response) {
                arrayBagianMesin = response.body().getResult();
                adapterBagianMesin = new AdapterAutoCompBagianMesin(EndMntActivity.this, arrayBagianMesin);
                actBagianMesin.setAdapter(adapterBagianMesin);

                actBagianMesin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ModelBagianMesin item = adapterBagianMesin.getItem(i);
                        clickedBagianMesin = item.getID();
                        tilSparepart.setVisibility(View.VISIBLE);
                        loadSparepart(clickedBagianMesin);
                        Log.d(TAG, "BagianMesin: " + item.getNama());
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBagianMesin> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Bagian Mesin, error: " + t.getMessage());
                Toast.makeText(EndMntActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnSelesai = (CardView) findViewById(R.id.btn_selesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ID Masalah: " + id_masalah);
                if (id_masalah == null) {
                    if (!isSparepart || !isTeknikal) {
                        Log.d(TAG, "if sp tk");
                        AlertDialog.Builder alert = new AlertDialog.Builder(EndMntActivity.this);
                        alert.setTitle("Periksa kembali!");
                        alert.setMessage("Isi Data masalah teknikal dan data detail maintenance");
                        alert.setCancelable(false);
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alert.show();
                    } else {
                        writeData();
                    }
                }
            }
        });

        btnCam = (CardView) findViewById(R.id.btn_cam);
        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });
    }

    public void writeData() {

        radioGroup = (RadioGroup) findViewById(R.id.radio);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        Log.d(TAG, "selectedId: " + selectedId);

        String sppmWH = "-";
        if (selectedId != -1) sppmWH = radioButton.getText().toString();

        File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.maintenanceapp/files/Pictures");
        if (dir.isDirectory()) {
            String[] children = dir.list();
            File mntPict;
            Bitmap test;

            for (int i = 0; i < children.length; i++) {
                mntPict = new File(dir, children[i]);
                String fileUpload = mntPict.getPath();

                uploadImage(mntPict, fileUpload);
            }
        }
        // to end

        final APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseSet> selesai = aiData.setSelesaiMnt(id_mnt, id_masalah, clickedPenyebab, clickedSparepart, sppmWH, id_line, id_mesin, jenis_mnt);
        selesai.enqueue(new Callback<ResponseSet>() {
            @Override
            public void onResponse(Call<ResponseSet> call, Response<ResponseSet> response) {
                if (response.body().getKode().equals("1")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EndMntActivity.this);
                    alert.setTitle("Berhasil!");
                    alert.setMessage("Kegiatan maintenance telah tercatat");
                    alert.setCancelable(false);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                    alert.show();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EndMntActivity.this);
                    alert.setTitle("Gagal!");
                    alert.setMessage("Kegiatan maintenance belum tercatat");
                    alert.setCancelable(false);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.show();
                }

//                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseSet> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                AlertDialog.Builder alert = new AlertDialog.Builder(EndMntActivity.this);
                alert.setTitle("Gagal!");
                alert.setMessage("Kegiatan maintenance belum tercatat, hubungi administrator terkait kendala berikut: " + t.getLocalizedMessage());
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();

                loading.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            loadDataFoto();
        } else {
            photoFile.delete();
            displayMessage(getBaseContext(), "Request dibatalkan");
        }
    }

    public void loadSparepart(String id_bagian_mesin) {
        arraySparepart = new ArrayList<>();

        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseSparepart> listData = aiData.getSparepart(id_bagian_mesin);
        listData.enqueue(new Callback<ResponseSparepart>() {
            @Override
            public void onResponse(Call<ResponseSparepart> call, Response<ResponseSparepart> response) {
                arraySparepart = response.body().getResult();

                adapterSparepart = new AdapterAutoCompSparepart(EndMntActivity.this, arraySparepart);
                actSparepart.setAdapter(adapterSparepart);

                isSparepart = true;
                actSparepart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ModelSparepart item = adapterSparepart.getItem(i);
                        clickedSparepart = item.getID();
                        Log.d(TAG, "Sparepart: " + item.getNama());
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseSparepart> call, Throwable t) {
                Log.d(TAG, "Faiure: Data Sparepart, error: " + t.getMessage());
                Toast.makeText(EndMntActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadPenyebab(String id_definisi) {
        arrayDefinisi = new ArrayList<>();

        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponsePenyebab> listData = aiData.getPenyebab(id_definisi);
        listData.enqueue(new Callback<ResponsePenyebab>() {
            @Override
            public void onResponse(Call<ResponsePenyebab> call, Response<ResponsePenyebab> response) {
                arrayPenyebab = response.body().getResult();

                adapterPenyebab = new AdapterAutoCompPenyebab(EndMntActivity.this, arrayPenyebab);
                actPenyebab.setAdapter(adapterPenyebab);

                actPenyebab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ModelPenyebab item = adapterPenyebab.getItem(i);
                        isTeknikal = true;
                        clickedPenyebab = item.getID();
                        Log.d(TAG, "Penyebab: " + item.getPenyebab());
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponsePenyebab> call, Throwable t) {
                Toast.makeText(EndMntActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadDefinisi(String id_alarm) {
        arrayDefinisi = new ArrayList<>();

        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseDefinisi> listData = aiData.getDefinisi(id_alarm);
        listData.enqueue(new Callback<ResponseDefinisi>() {
            @Override
            public void onResponse(Call<ResponseDefinisi> call, Response<ResponseDefinisi> response) {
                arrayDefinisi = response.body().getResult();

                adapterDefinisi = new AdapterAutoCompDefinisi(EndMntActivity.this, arrayDefinisi);
                actDefinisi.setAdapter(adapterDefinisi);

                actDefinisi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ModelDefinisi item = adapterDefinisi.getItem(i);
                        clickedDefinisi = item.getID();
                        Log.d(TAG, "Definisi: " + item.getDefinisi());

                        tilPenyebab.setVisibility(View.VISIBLE);
                        loadPenyebab(clickedDefinisi);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseDefinisi> call, Throwable t) {

            }
        });
    }

    public void loadAlarm(String id_series) {
        arrayAlarm = new ArrayList<>();

        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseAlarm> listData = aiData.getAlarm(id_series);
        listData.enqueue(new Callback<ResponseAlarm>() {
            @Override
            public void onResponse(Call<ResponseAlarm> call, Response<ResponseAlarm> response) {
                arrayAlarm = response.body().getResult();
                Log.d(TAG, "onResponse: result: " + arrayAlarm.size());

                adapterAlarm = new AdapterAutoCompAlarm(EndMntActivity.this, arrayAlarm);
                actAlarm.setAdapter(adapterAlarm);

                actAlarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ModelAlarm item = adapterAlarm.getItem(i);
                        clickedAlarm = item.getID();
                        Log.d(TAG, "Alarm: " + item.getDisplay());

                        tilDefinisi.setVisibility(View.VISIBLE);
                        loadDefinisi(clickedAlarm);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseAlarm> call, Throwable t) {

            }
        });
    }

    public void loadDataFoto() {
        listFile.clear();
        File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.maintenanceapp/files/Pictures");
        if (dir.isDirectory()) {
            String[] children = dir.list();
            File mntPict;

            for (int i = 0; i < children.length; i++) {
                mntPict = new File(dir, children[i]);
                listFile.add(mntPict);
            }

            // send list File to adapter
            adFoto = new AdapterFotoMnt(EndMntActivity.this, listFile);
            rvFoto.setAdapter(adFoto);
            adFoto.notifyDataSetChanged();
        }
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MNT_" + id_mnt + "_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    private void captureImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        else
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile();
                    displayMessage(getBaseContext(), photoFile.getAbsolutePath());
                    Log.i(TAG, photoFile.getAbsolutePath());

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.maintenanceapp.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        takePictureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, "360000");
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getBaseContext(),"takePictureIntent: " + ex.getMessage().toString());
                }
            } else {
                displayMessage(getBaseContext(),"Null");
            }
        }
    }

    private void uploadImage(final File mntPict, final String fileUpload){
        class UploadImage extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                Log.d(TAG, "uploadImage: onPreExecute");
//                displayMessage(getBaseContext(), "uploadImage: onPreExecute");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                mntPict.delete();
                displayMessage(getBaseContext(), "Foto Tersimpan");
            }

            @Override
            protected String doInBackground(Void... voids) {
                Upload upload = new Upload();
//                displayMessage(getBaseContext(), "uploadImage fileUpload: " + fileUpload);
                Log.d(TAG, "uploadImage fileUpload: " + fileUpload);
                String message = upload.uploadImage(id_mnt, fileUpload);
                Log.d(TAG, "uploadImage message: " +message);
//                displayMessage(getBaseContext(), "uploadImage: " + message);
                return message;
            }
        }
        UploadImage up = new UploadImage();
        up.execute();
    }
}
