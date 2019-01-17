package com.examlpe.com.sofra.data.local.room;

//this class represent a table in the database

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "car_food")
public class ItemFoodData {

    @PrimaryKey(autoGenerate = true)
    private int item_Id;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("item_note")
    @Expose
    private String item_note;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;




    public ItemFoodData(Integer id, String item_note, String quantity, String name, String price, String photoUrl, String restaurantId) {
        this.id = id;
        this.item_note = item_note;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
        this.photoUrl = photoUrl;

    }

    public int getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(int item_Id) {
        this.item_Id = item_Id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getItem_note() {
        return item_note;
    }

    public void setItem_note(String item_note) {
        this.item_note = item_note;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

   /* public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }*/
}

