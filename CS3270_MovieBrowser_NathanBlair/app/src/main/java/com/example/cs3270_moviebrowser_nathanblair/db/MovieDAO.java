package com.example.cs3270_moviebrowser_nathanblair.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDAO {

    //Retrieve a list of courses
    @Query("select * from Movie")
    LiveData<List<Movie>> getAll();

    //View details of a selected course
    @Query("select * from Movie where id =:id")
    Movie getByID(int id);

    //Delete a Movie
    @Delete
    void delete(Movie movie);

    //Add a Movie
    @Insert
    void insert(Movie...movie);

    @Query("select count(*)")
    int dataSize();

    @Query("delete from Movie")
    void deleteAll();
}
