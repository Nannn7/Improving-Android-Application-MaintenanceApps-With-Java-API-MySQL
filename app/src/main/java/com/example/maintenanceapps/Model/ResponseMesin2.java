package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseMesin2 {
    String kode, pesan;
    List<ModelMesin> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelMesin> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(List<ModelMesin> result) {
        this.result = result;
    }
}
