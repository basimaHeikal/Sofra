package com.examlpe.com.sofra.data.model.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Region {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("city")
    @Expose
    private City city;

    public Region( String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
