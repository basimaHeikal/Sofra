package com.examlpe.com.sofra.data.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantLoginData {
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private RestaurantUser user;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public RestaurantUser getUser() {
        return user;
    }

    public void setUser(RestaurantUser user) {
        this.user = user;
    }
}
