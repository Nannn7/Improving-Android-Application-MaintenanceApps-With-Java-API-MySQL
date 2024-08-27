package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseMaintenance {
    String kode, pesan, proses;
    List<ModelMaintenance> result;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getProses() { return proses; }

    public void setProses(String proses) { this.proses = proses; }

    public List<ModelMaintenance> getResult() {
        return result;
    }

    public void setResult(List<ModelMaintenance> result) {
        this.result = result;
    }
}
