package com.example.maintenanceapps.Model;

public class ResponseMesin {
    String kode, pesan, ID;
    Boolean isError;
    ModelMesin mesin;
    ModelSparepart sparepart;
    ModelMaintenance maintenance;
    ModelLine line;
    ModelMasalah masalah;
    ModelPenyebab penyebab;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public ModelMesin getMesin() {
        return mesin;
    }

    public ModelSparepart getNama() { return sparepart; }

    public Boolean getError() {
        return isError;
    }

    public ModelMaintenance getMaintenance() {
        return maintenance;
    }


    public ModelLine getLine() {
        return line;
    }

    public ModelMasalah getMasalah() {
        return masalah;
    }

    public ModelPenyebab getPenyebab() {
        return penyebab;
    }

    public ModelSparepart getSparepart() {
        return sparepart;
    }

    public String getID() {
        return ID;
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

    public void setNama(ModelSparepart sparepart) {this.sparepart = sparepart; }

    public void setError(Boolean error) {
        isError = error;
    }

    public void setMaintenance(ModelMaintenance maintenance) {
        this.maintenance = maintenance;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setLine(ModelLine line) {
        this.line = line;
    }

    public void setSparepart(ModelSparepart sparepart) {
        this.sparepart = sparepart;
    }

    public void setMasalah(ModelMasalah masalah) {
        this.masalah = masalah;
    }

    public void setPenyebab(ModelPenyebab penyebab) {
        this.penyebab = penyebab;
    }
}
