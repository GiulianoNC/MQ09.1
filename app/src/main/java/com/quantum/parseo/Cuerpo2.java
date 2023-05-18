package com.quantum.parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cuerpo2 {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("Deposito_Origen")
    @Expose
    private String depositoOrigen;
    @SerializedName("Deposito_Destino")
    @Expose
    private String depositoDestino;
    @SerializedName("Numero_Serie")
    @Expose
    private String numeroSerie;
    @SerializedName("Version")
    @Expose
    private String version;

    @SerializedName("Numero_Carga")
    @Expose
    private String numeroCarga;
    @SerializedName("Deposito")
    @Expose
    private String deposito;

    public Cuerpo2(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Cuerpo2(String username, String password, String depositoOrigen, String depositoDestino, String numeroSerie, String version) {
        this.username = username;
        this.password = password;
        this.depositoOrigen = depositoOrigen;
        this.depositoDestino = depositoDestino;
        this.numeroSerie = numeroSerie;
        this.version = version;
    }



}
