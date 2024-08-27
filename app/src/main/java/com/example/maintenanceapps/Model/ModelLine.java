package com.example.maintenanceapps.Model;

public class ModelLine {
    String ID, Nama, Nama_Proses, Display;

    public String getID() {
        return ID;
    }

    public String getNama() {
        return Nama;
    }

    public String getDisplay() {return Display;}

    public String getNama_Proses() {
        return Nama_Proses;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public void setNama_Proses(String nama_Proses) {
        Nama_Proses = nama_Proses;
    }
}
