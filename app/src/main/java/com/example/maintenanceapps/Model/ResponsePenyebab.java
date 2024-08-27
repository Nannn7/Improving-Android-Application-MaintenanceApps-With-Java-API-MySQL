package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponsePenyebab {
    String kode, pesan;
    List<ModelPenyebab> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelPenyebab> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(List<ModelPenyebab> result) {
        this.result = result;
    }
}
