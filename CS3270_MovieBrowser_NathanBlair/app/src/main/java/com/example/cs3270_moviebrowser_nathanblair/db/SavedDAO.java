package com.example.cs3270_moviebrowser_nathanblair.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedDAO {

    @Query("select * from SavedMovie")
    LiveData<List<SavedMovie>> getAll();

    @Query("select * from SavedMovie where id =:id")
    SavedMovie getByID(int id);

    @Delete
    void delete(SavedMovie savedMovie);

    @Insert
    void insert(SavedMovie...savedMovies);
}
