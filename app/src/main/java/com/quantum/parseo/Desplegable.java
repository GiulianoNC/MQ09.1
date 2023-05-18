package com.quantum.parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Desplegable {
    @SerializedName("MQ0904A_DATAREQ")
    @Expose
    private List<Mq0904aDatareq> mq0904aDatareq;

    /**
     * No args constructor for use in serialization
     *
     */
    public Desplegable() {
    }

    /**
     *
     * @param mq0904aDatareq
     */
    public Desplegable(List<Mq0904aDatareq> mq0904aDatareq) {
        super();
        this.mq0904aDatareq = mq0904aDatareq;
    }

    public List<Mq0904aDatareq> getMq0904aDatareq() {
        return mq0904aDatareq;
    }

    public void setMq0904aDatareq(List<Mq0904aDatareq> mq0904aDatareq) {
        this.mq0904aDatareq = mq0904aDatareq;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Desplegable.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("mq0904aDatareq");
        sb.append('=');
        sb.append(((this.mq0904aDatareq == null)?"<null>":this.mq0904aDatareq));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}