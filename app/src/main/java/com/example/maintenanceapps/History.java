package com.example.maintenanceapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.maintenanceapps.Maintenance.AdmHistoryActivity;
import com.example.maintenanceapps.Model.PrefConfig;

public class History extends AppCompatActivity {

    private CardView cardHistory;
    private TextView tv_sejarah;

    public static PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih);

        prefConfig = new PrefConfig(this);
        if (!prefConfig.readLoginStatus()) {
            Intent intent = new Intent(History.this, LoginActivity.class);
            startActivity(intent);
        } else {
            tv_sejarah = (TextView) findViewById(R.id.txt_sejarah);
            tv_sejarah.setText("Selamat datang " + History.prefConfig.readName());
        }

        cardHistory = findViewById(R.id.cvPilih);
        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this, AdmHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exit_menu, menu);
        return true;
    }

    public void logout(MenuItem menu) {
        AlertDialog.Builder alert = new AlertDialog.Builder(History.this);
        alert.setTitle("Logout");
        alert.setMessage("Apakah anda yakin untuk logout akun anda?");
        alert.setCancelable(false);
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(History.this, LoginActivity.class);
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
}
