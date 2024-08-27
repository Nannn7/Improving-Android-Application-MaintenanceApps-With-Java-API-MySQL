package com.example.maintenanceapps.Model;

public class ModelUser {
    String ID, Nama, Role, Status;

    public String getID() {
        return ID;
    }

    public String getNama() {
        return Nama;
    }

    public String getRole() {
        return Role;
    }

    public String getStatus() {
        return Status;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
