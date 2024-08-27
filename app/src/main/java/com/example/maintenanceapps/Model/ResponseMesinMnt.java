package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseMesinMnt {
    String kode, pesan;
    ModelMesin mesin;
    List<ModelMaintenance> maintenance;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public ModelMesin getMesin() {
        return mesin;
    }

    public List<ModelMaintenance> getMaintenance() {
        return maintenance;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setMesin(ModelMesin mesin) {
        this.mesin = mesin;
    }

    public void setMaintenance(List<ModelMaintenance> maintenance) {
        this.maintenance = maintenance;
    }
}
