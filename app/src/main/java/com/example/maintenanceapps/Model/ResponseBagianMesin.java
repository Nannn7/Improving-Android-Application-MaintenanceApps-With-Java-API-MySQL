package com.example.maintenanceapps.Model;

import java.util.ArrayList;
import java.util.List;

public class ResponseBagianMesin {
    String kode, pesan;
    List<ModelBagianMesin> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelBagianMesin> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(List<ModelBagianMesin> result) {
        this.result = result;
    }
}
