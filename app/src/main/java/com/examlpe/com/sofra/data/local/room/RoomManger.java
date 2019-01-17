package com.examlpe.com.sofra.data.local.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

//database class
//ItemFoodData a table in the DB
@Database(entities = {ItemFoodData.class}, version = 1, exportSchema = false)
@TypeConverters({DataTypeConverter.class})
//abstract class extend RoomDatabase
public abstract class RoomManger extends RoomDatabase {

    private static RoomManger roomManger;

    //abstract method that has 0 arguments and returns an object of data access object class(class with annotation @Dao)
    public abstract RoomDao roomDao();

    public static synchronized RoomManger getInstance(Context context) {
        if (roomManger == null) {
            //acquire an instance of database
            roomManger = Room.databaseBuilder(context, RoomManger.class, "sofra_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return roomManger;
    }

}