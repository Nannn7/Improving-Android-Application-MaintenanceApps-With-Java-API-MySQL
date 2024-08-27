package com.example.maintenanceapps.Model;

import java.util.ArrayList;
import java.util.List;

public class ResponseSparepart {
    String kode, pesan;
    ModelSparepart sparepart;
    ArrayList<ModelSparepart> result;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public ModelSparepart getSparepart() {
        return sparepart;
    }

    public ArrayList<ModelSparepart> getResult() {
        return result;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setResult(ArrayList<ModelSparepart> result) {
        this.result = result;
    }
}
