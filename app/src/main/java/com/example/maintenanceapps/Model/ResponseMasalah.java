package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseMasalah {
    String kode, pesan;
    List<ModelMasalah> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelMasalah> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(List<ModelMasalah> result) {
        this.result = result;
    }
}
