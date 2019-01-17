package com.examlpe.com.sofra.data.model.client;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientLoginData {
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private ClientUser user;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public ClientUser getUser() {
        return user;
    }

    public void setUser(ClientUser user) {
        this.user = user;
    }
}
