package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseDefinisiFoto {
    String kode, pesan;
    List<ModelDefinisiFoto> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelDefinisiFoto> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(List<ModelDefinisiFoto> result) {
        this.result = result;
    }
}
