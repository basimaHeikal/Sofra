package com.examlpe.com.sofra.data.model.client;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientLoginResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ClientLoginData data;

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ClientLoginData getData() {
        return data;
    }

    public void setData(ClientLoginData data) {
        this.data = data;
    }

}
