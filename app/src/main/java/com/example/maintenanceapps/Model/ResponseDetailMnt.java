package com.example.maintenanceapps.Model;

import java.util.List;

public class ResponseDetailMnt {
    String kode, pesan;
    ModelMesin mesin;
    ModelMaintenance maintenance;
    List<ModelMaintenanceDetail> detailMnt;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public ModelMesin getMesin() {
        return mesin;
    }

    public ModelMaintenance getMaintenance() {
        return maintenance;
    }

    public List<ModelMaintenanceDetail> getDetailMnt() {
        return detailMnt;
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

    public void setMaintenance(ModelMaintenance maintenance) {
        this.maintenance = maintenance;
    }

    public void setDetailMnt(List<ModelMaintenanceDetail> detailMnt) {
        this.detailMnt = detailMnt;
    }
}
