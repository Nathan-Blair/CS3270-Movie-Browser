package com.example.cs3270_moviebrowser_nathanblair.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class, SavedMovie.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance != null){
            return instance;
        }else{
            instance = Room.databaseBuilder(context, AppDatabase.class, "user-database")
                    .build();
            return instance;
        }
    }

    public abstract MovieDAO movieDAO();
    public abstract SavedDAO savedDAO();
}