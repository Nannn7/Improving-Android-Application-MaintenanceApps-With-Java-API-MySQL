package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseModul {
    String kode, pesan;
    List<ModelModul> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelModul> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(List<ModelModul> result) {
        this.result = result;
    }
}
