package com.example.maintenanceapps.Model;

public class ModelModul {
    String ID_JenisKontrol, ID_SeriesKontrol, NamaJenis, NamaSeries;

    public String getID_JenisKontrol() {
        return ID_JenisKontrol;
    }

    public String getID_SeriesKontrol() {
        return ID_SeriesKontrol;
    }

    public String getNamaJenis() {
        return NamaJenis;
    }

    public String getNamaSeries() {
        return NamaSeries;
    }

    public void setID_JenisKontrol(String ID_JenisKontrol) {
        this.ID_JenisKontrol = ID_JenisKontrol;
    }

    public void setID_SeriesKontrol(String ID_SeriesKontrol) {
        this.ID_SeriesKontrol = ID_SeriesKontrol;
    }

    public void setNamaJenis(String namaJenis) {
        NamaJenis = namaJenis;
    }

    public void setNamaSeries(String namaSeries) {
        NamaSeries = namaSeries;
    }
}
