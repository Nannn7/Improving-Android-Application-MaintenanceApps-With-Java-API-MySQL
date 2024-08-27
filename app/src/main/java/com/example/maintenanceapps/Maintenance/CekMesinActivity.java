package com.example.maintenanceapps.Maintenance;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.maintenanceapps.PilihMenu;
import com.example.maintenanceapps.R;

public class CekMesinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_mesin);

        CardView pilihan_menu = findViewById(R.id.pilihan_menu);
        pilihan_menu.setOnClickListener(v -> {
            Intent intent = new Intent(CekMesinActivity.this, PilihMenu.class);
            startActivity(intent);
        });
    }
}




