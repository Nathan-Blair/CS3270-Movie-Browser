package com.example.cs3270_moviebrowser_nathanblair;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3270_moviebrowser_nathanblair.db.SavedMovie;

import java.util.ArrayList;
import java.util.List;

public class SavedMoviesFragment extends Fragment {


    View view;
    private RecyclerView recyclerView;
    private SavedMovieRecyclerViewAdapter savedMovieRecyclerViewAdapter;
    private int columnCount = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_saved_movies, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Context context = getContext();
        savedMovieRecyclerViewAdapter = new SavedMovieRecyclerViewAdapter(new ArrayList<>());

        if(columnCount <= 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }

        recyclerView.setAdapter(savedMovieRecyclerViewAdapter);
        recyclerView.setHasFixedSize(false);

        ViewModelProviders.of(this)
                .get(AllSavedViewModel.class)
                .getSavedMovieList(context)
                .observe(this, new Observer<List<SavedMovie>>() {
                    @Override
                    public void onChanged(List<SavedMovie> savedMovies) {
                        if(savedMovies != null){
                            savedMovieRecyclerViewAdapter.addItems(savedMovies);
                        }
                    }
                });
    }
}