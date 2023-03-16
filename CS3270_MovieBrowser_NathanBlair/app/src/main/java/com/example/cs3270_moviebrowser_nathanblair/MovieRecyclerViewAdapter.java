package com.example.cs3270_moviebrowser_nathanblair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs3270_moviebrowser_nathanblair.db.AppDatabase;
import com.example.cs3270_moviebrowser_nathanblair.db.Movie;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>{

    public final List<Movie> movies;

    public MovieRecyclerViewAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<Movie> newMovies) {


        movies.addAll(newMovies);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public Movie movie;
        public TextView txtTitle, txtDescription, txtGenre;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            view = itemView;
            txtTitle = view.findViewById(R.id.riTextTitle);
            txtDescription = view.findViewById(R.id.riTextDescription);
            txtGenre = view.findViewById(R.id.riTextGenre);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        if(movie != null){
            holder.txtTitle.setText(movie.getMovieCode());
            holder.txtDescription.setText(movie.getDescription());
            //holder.txtGenre.setText(movie.getGenre());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("movie_pk", movie.getId());

                    MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
                    movieDetailsFragment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, movieDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    public void clearMovies() {
        int size = movies.size();
        int start = 0;
        if (size > 0) {
            movies.subList(start, size).clear();
            notifyItemRangeRemoved(start, size);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
