package com.example.cs3270_moviebrowser_nathanblair;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cs3270_moviebrowser_nathanblair.db.AppDatabase;
import com.example.cs3270_moviebrowser_nathanblair.db.Movie;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private GetMovieList task;
    FragmentManager fm;
    View view;
    private TextInputEditText textMovieTitle;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private int columnCount = 1;
    private RecyclerView recyclerView;
    FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnSubmit = (Button)view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                task = new GetMovieList();
                //if(textMovieTitle != null){
                    textMovieTitle = (TextInputEditText)view.findViewById(R.id.txtMovieTitle);
                    task.movieTitle = textMovieTitle.getText().toString();
                //}

                task.setOnMovieListImportListener(new GetMovieList.OnMovieListImport() {
                    @Override
                    public void completedMovieList(Movie[] movies) {
                        final ArrayList<Movie> movieList = new ArrayList<Movie>();
                        int i = 0;
                        for(Movie m: movies){
                            Log.d("MainActivity", "completedMovieList: " + m.toString());
                            //TODO
                            //Convert the c and add to courseList
                            movieList.add(m);
                        }

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                int y = 0;
                                while(y < movieList.size()){
                                    int id = movieList.get(y).getId();
                                    Movie movie = new Movie(
                                            movieList.get(y).getTitle(),
                                            movieList.get(y).getMovieCode(),
                                            movieList.get(y).getDescription(),
                                            movieList.get(y).getImage());
                                    AppDatabase.getInstance(getContext())
                                            .movieDAO()
                                            .insert(movie);
                                    y++;
                                }
                            }
                        }).start();
                    }

                });

                task.execute("");
            }

            BrowseFragment browseFragment = new BrowseFragment();



        });
        return view;
    }


}