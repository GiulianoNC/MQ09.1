package com.quantum.parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Form {
    @SerializedName("fs_PMQ09001_WMQ09001B")
    @Expose
    private FsPMQ09001WMQ09001B fsPMQ09001WMQ09001B;
    @SerializedName("stackId")
    @Expose
    private Integer stackId;
    @SerializedName("stateId")
    @Expose
    private Integer stateId;
    @SerializedName("rid")
    @Expose
    private String rid;
    @SerializedName("currentApp")
    @Expose
    private String currentApp;
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;
    @SerializedName("sysErrors")
    @Expose
    private List<Object> sysErrors = null;
    @SerializedName("fs_PMQ09001_WMQ09001A")
    @Expose
    private FsPMQ09001WMQ09001A fsPMQ09001WMQ09001A;

    /**
     * No args constructor for use in serialization
     *
     */
    public Form() {
    }

    /**
     *
     * @param timeStamp
     * @param fsPMQ09001WMQ09001B
     * @param fsPMQ09001WMQ09001A
     * @param stackId
     * @param stateId
     * @param sysErrors
     * @param rid
     * @param currentApp
     */
    public Form(FsPMQ09001WMQ09001B fsPMQ09001WMQ09001B, Integer stackId, Integer stateId, String rid, String currentApp, String timeStamp, List<Object> sysErrors, FsPMQ09001WMQ09001A fsPMQ09001WMQ09001A) {
        super();
        this.fsPMQ09001WMQ09001B = fsPMQ09001WMQ09001B;
        this.stackId = stackId;
        this.stateId = stateId;
        this.rid = rid;
        this.currentApp = currentApp;
        this.timeStamp = timeStamp;
        this.sysErrors = sysErrors;
        this.fsPMQ09001WMQ09001A = fsPMQ09001WMQ09001A;
    }

    public FsPMQ09001WMQ09001B getFsPMQ09001WMQ09001B() {
        return fsPMQ09001WMQ09001B;
    }

    public void setFsPMQ09001WMQ09001B(FsPMQ09001WMQ09001B fsPMQ09001WMQ09001B) {
        this.fsPMQ09001WMQ09001B = fsPMQ09001WMQ09001B;
    }

    public Integer getStackId() {
        return stackId;
    }

    public void setStackId(Integer stackId) {
        this.stackId = stackId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getCurrentApp() {
        return currentApp;
    }

    public void setCurrentApp(String currentApp) {
        this.currentApp = currentApp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<Object> getSysErrors() {
        return sysErrors;
    }

    public void setSysErrors(List<Object> sysErrors) {
        this.sysErrors = sysErrors;
    }

    public FsPMQ09001WMQ09001A getFsPMQ09001WMQ09001A() {
        return fsPMQ09001WMQ09001A;
    }

    public void setFsPMQ09001WMQ09001A(FsPMQ09001WMQ09001A fsPMQ09001WMQ09001A) {
        this.fsPMQ09001WMQ09001A = fsPMQ09001WMQ09001A;
    }

}
