package com.quantum.parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mq0904aDatareq {
    @SerializedName("Deposito")
    @Expose
    private String deposito;
    @SerializedName("Descripcion")
    @Expose
    private String descripcion;

    /**
     * No args constructor for use in serialization
     *
     */
    public Mq0904aDatareq() {
    }

    /**
     *
     * @param descripcion
     * @param deposito
     */
    public Mq0904aDatareq(String deposito, String descripcion) {
        super();
        this.deposito = deposito;
        this.descripcion = descripcion;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Mq0904aDatareq.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("deposito");
        sb.append('=');
        sb.append(((this.deposito == null)?"<null>":this.deposito));
        sb.append(',');
        sb.append("descripcion");
        sb.append('=');
        sb.append(((this.descripcion == null)?"<null>":this.descripcion));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}