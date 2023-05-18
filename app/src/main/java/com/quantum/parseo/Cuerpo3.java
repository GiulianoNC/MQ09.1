package com.quantum.parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cuerpo3 {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("Deposito")
    @Expose
    private String deposito;
    @SerializedName("Numero_Carga")
    @Expose
    private String numeroCarga;
    @SerializedName("Numero_Serie")
    @Expose
    private String numeroSerie;
    @SerializedName("Version")
    @Expose
    private String version;

    public Cuerpo3(String username, String password, String deposito, String numeroCarga, String numeroSerie, String version) {
        this.username = username;
        this.password = password;
        this.deposito = deposito;
        this.numeroCarga = numeroCarga;
        this.numeroSerie = numeroSerie;
        this.version = version;
    }
}
