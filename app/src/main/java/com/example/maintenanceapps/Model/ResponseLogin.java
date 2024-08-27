package com.example.maintenanceapps.Model;

public class ResponseLogin {
    String kode;
    ModelUser response;

    public String getKode() {
        return kode;
    }

    public ModelUser getResponse() {
        return response;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setResponse(ModelUser response) {
        this.response = response;
    }
}
