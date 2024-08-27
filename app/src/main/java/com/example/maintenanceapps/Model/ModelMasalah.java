package com.example.maintenanceapps.Model;

public class ModelMasalah {
    String ID, Masalah, Penyebab, Penanganan;

    public String getID() {
        return ID;
    }

    public String getMasalah() {
        return Masalah;
    }

    public String getPenyebab() {
        return Penyebab;
    }

    public String getPenanganan() {
        return Penanganan;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setMasalah(String masalah) {
        Masalah = masalah;
    }

    public void setPenyebab(String penyebab) {
        Penyebab = penyebab;
    }

    public void setPenanganan(String penanganan) {
        Penanganan = penanganan;
    }
}
