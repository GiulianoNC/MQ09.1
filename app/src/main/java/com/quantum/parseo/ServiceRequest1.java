package com.quantum.parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceRequest1 {
    @SerializedName("forms")
    @Expose
    private List<Form> forms = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ServiceRequest1() {
    }

    /**
     *
     * @param forms
     */
    public ServiceRequest1(List<Form> forms) {
        super();
        this.forms = forms;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }
}
