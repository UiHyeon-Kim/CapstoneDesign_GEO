package com.example.capstonedesign_geo.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.capstonedesign_geo.data.dao.UserDao;
import com.example.capstonedesign_geo.data.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class GeoDatabase extends RoomDatabase {
    private static volatile GeoDatabase INSTANCE;

    public abstract UserDao userDao();

    public static GeoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (GeoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GeoDatabase.class, "GeoDatabase").build();
                }
            }
        }
        return INSTANCE;
    }
}