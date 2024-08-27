package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseAlarm {
    String kode, pesan;
    List<ModelAlarm> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelAlarm> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(List<ModelAlarm> result) {
        this.result = result;
    }
}
