package com.quantum.parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cuerpo {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("Deposito")
    @Expose
    private String deposito;
    @SerializedName("Tipo_Pedido")
    @Expose
    private String tipoPedido;
    @SerializedName("Numero_Pedido")
    @Expose
    private String numeroPedido;
    @SerializedName("Numero_Serie")
    @Expose
    private String numeroSerie;
    @SerializedName("Version")
    @Expose
    private String version;

    @SerializedName("Numero_Carga")
    @Expose
    private String numeroCarga;

    /**
     * No args constructor for use in serialization
     *
     */
    public Cuerpo() {
    }

    /**
     *
     * @param numeroSerie
     * @param password
     * @param numeroPedido
     * @param tipoPedido
     * @param version
     * @param username
     */
    public Cuerpo(String username, String password, String tipoPedido, String numeroPedido, String numeroSerie, String version) {
        super();
        this.username = username;
        this.password = password;
        this.tipoPedido = tipoPedido;
        this.numeroPedido = numeroPedido;
        this.numeroSerie = numeroSerie;
        this.version = version;
    }

    public Cuerpo(String username, String password, String deposito, String tipoPedido, String numeroPedido, String numeroSerie, String version) {
        this.username = username;
        this.password = password;
        this.deposito = deposito;
        this.tipoPedido = tipoPedido;
        this.numeroPedido = numeroPedido;
        this.numeroSerie = numeroSerie;
        this.version = version;
    }



    public Cuerpo(String username, String password, String numeroCarga , String numeroSerie, String version) {
        this.username = username;
        this.password = password;
        this.numeroCarga = numeroCarga;
        this.numeroSerie = numeroSerie;
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
