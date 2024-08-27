package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseLine {
    String kode, pesan;
    List<ModelLine> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelLine> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(List<ModelLine> result) {
        this.result = result;
    }
}
