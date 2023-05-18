package com.quantum.parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FsPMQ09001WMQ09001B {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @SerializedName("warnings")
    @Expose
    private List<Object> warnings = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public FsPMQ09001WMQ09001B() {
    }

    /**
     *
     * @param data
     * @param warnings
     * @param title
     * @param errors
     */
    public FsPMQ09001WMQ09001B(String title, Data data, List<Object> errors, List<Object> warnings) {
        super();
        this.title = title;
        this.data = data;
        this.errors = errors;
        this.warnings = warnings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
