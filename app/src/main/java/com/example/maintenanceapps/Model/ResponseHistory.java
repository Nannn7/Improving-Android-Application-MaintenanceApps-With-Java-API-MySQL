package com.example.maintenanceapps.Model;

public class ResponseHistory {

    private ModelHistory modelHistory;
    private String ID, kode, pesan;

    public ModelHistory getModelHistory() {
        return modelHistory;
    }

    public void setModelHistory(ModelHistory modelHistory) {
        this.modelHistory = modelHistory;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
