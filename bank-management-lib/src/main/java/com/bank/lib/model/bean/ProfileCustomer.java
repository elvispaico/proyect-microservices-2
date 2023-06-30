package com.bank.lib.model.bean;

public class ProfileCustomer {
    private String codPerfil;
    private String desPerfil;

    public ProfileCustomer() {
    }

    public ProfileCustomer(String codPerfil, String desPerfil) {
        this.codPerfil = codPerfil;
        this.desPerfil = desPerfil;
    }

    public String getCodPerfil() {
        return codPerfil;
    }

    public void setCodPerfil(String codPerfil) {
        this.codPerfil = codPerfil;
    }

    public String getDesPerfil() {
        return desPerfil;
    }

    public void setDesPerfil(String desPerfil) {
        this.desPerfil = desPerfil;
    }
}
