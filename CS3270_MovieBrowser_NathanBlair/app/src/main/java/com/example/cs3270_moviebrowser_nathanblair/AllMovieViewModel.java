package com.example.cs3270_moviebrowser_nathanblair;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs3270_moviebrowser_nathanblair.db.AppDatabase;
import com.example.cs3270_moviebrowser_nathanblair.db.Movie;

import java.util.List;

public class AllMovieViewModel extends ViewModel {
    private LiveData<List<Movie>> movieList;

    public LiveData<List<Movie>> getMovieList(Context c){
        if(movieList != null){
            return movieList;
        }else{
            return movieList = AppDatabase.getInstance(c).movieDAO().getAll();
        }
    }
}
