package com.example.maintenanceapps.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.maintenanceapps.R;

public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }


    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();
    }

    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }


    public void writeID(String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_id), id);
        editor.commit();
    }

    public String readID() {
        return sharedPreferences.getString(context.getString(R.string.pref_id), "User");
    }


    public void writeName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_nama), name);
        editor.commit();
    }

    public String readName() {
        return sharedPreferences.getString(context.getString(R.string.pref_nama), "User");
    }



    public void writeRole(String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_role), role);
        editor.commit();
    }

    public String readRole() {
        return sharedPreferences.getString(context.getString(R.string.pref_role), "User");
    }
}
