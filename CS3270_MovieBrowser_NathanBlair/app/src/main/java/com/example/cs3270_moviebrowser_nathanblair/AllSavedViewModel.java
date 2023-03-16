package com.example.cs3270_moviebrowser_nathanblair;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs3270_moviebrowser_nathanblair.db.AppDatabase;
import com.example.cs3270_moviebrowser_nathanblair.db.SavedMovie;

import java.util.List;

public class AllSavedViewModel extends ViewModel {
    private LiveData<List<SavedMovie>> savedMovieList;

    public LiveData<List<SavedMovie>> getSavedMovieList(Context c){
        if(savedMovieList != null){
            return savedMovieList;
        }else{
            return savedMovieList = AppDatabase.getInstance(c).savedDAO().getAll();
        }
    }
}
