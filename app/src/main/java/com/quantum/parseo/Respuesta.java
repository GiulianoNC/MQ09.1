package com.quantum.parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Respuesta {
    @SerializedName("Tipo_Pedido")
    @Expose
    private String tipoPedido;
    @SerializedName("Numero_Serie")
    @Expose
    private String numeroSerie;
    @SerializedName("jde__status")
    @Expose
    private String jdeStatus;

    @SerializedName("Numero_Carga")
    @Expose
    private String nroCarga;

    @SerializedName("Version")
    @Expose
    private String version;


    @SerializedName("ServiceRequest1")
    @Expose
    private ServiceRequest1 serviceRequest1;

    /**
     * No args constructor for use in serialization
     *
     */
    public Respuesta() {
    }

    /**
     *
     * @param numeroSerie
     * @param tipoPedido
     * @param jdeStatus
     */
    public Respuesta(String tipoPedido, String numeroSerie, String jdeStatus) {
        super();
        this.tipoPedido = tipoPedido;
        this.numeroSerie = numeroSerie;
        this.jdeStatus = jdeStatus;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getJdeStatus() {
        return jdeStatus;
    }

    public void setJdeStatus(String jdeStatus) {
        this.jdeStatus = jdeStatus;
    }


}