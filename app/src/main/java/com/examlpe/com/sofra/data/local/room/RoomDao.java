package com.examlpe.com.sofra.data.local.room;

//to access app's data using the room persistance library , you work with data access objects or DAOs
//this set of DAO objects forms the main components of room
//each DAO includes methods that offer abstract access to your app's database
//this interface contains all the possible access methods

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert
    void insertItemToCar(ItemFoodData ... foodItem);

    @Update
    void updateItemToCar(ItemFoodData... foodItem);

    @Delete
    void deleteItemToCar(ItemFoodData ... foodItem);

    @Query("Delete from car_food")
    void deleteAllItemToCar();

    @Query("Select * from car_food")
    List<ItemFoodData> getAllItem();
}