package com.example.maintenanceapps.Model;

public class ModelAlarm {
    String ID, Display, Nama;
    String NamaJenis, NamaSeries;

    public String getID() {
        return ID;
    }

    public String getDisplay() {
        return Display;
    }

    public String getNama() {
        return Nama;
    }

    public String getNamaJenis() {
        return NamaJenis;
    }

    public String getNamaSeries() {
        return NamaSeries;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDisplay(String display) {
        Display = display;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public void setNamaJenis(String namaJenis) {
        NamaJenis = namaJenis;
    }

    public void setNamaSeries(String namaSeries) {
        NamaSeries = namaSeries;
    }
}
