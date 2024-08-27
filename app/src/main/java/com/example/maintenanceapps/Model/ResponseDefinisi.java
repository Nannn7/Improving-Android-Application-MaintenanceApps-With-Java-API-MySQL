package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseDefinisi {
    String kode, pesan;
    List<ModelDefinisi> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelDefinisi> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(List<ModelDefinisi> result) {
        this.result = result;
    }
}
