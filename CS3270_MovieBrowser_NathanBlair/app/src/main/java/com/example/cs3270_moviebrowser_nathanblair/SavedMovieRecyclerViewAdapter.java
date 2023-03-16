package com.example.cs3270_moviebrowser_nathanblair;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs3270_moviebrowser_nathanblair.db.SavedMovie;

import java.util.List;

public class SavedMovieRecyclerViewAdapter extends RecyclerView.Adapter<SavedMovieRecyclerViewAdapter.ViewHolder>{

    public final List<SavedMovie> savedMovies;

    public SavedMovieRecyclerViewAdapter(List<SavedMovie> savedMovies) {
        this.savedMovies = savedMovies;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<SavedMovie> newMovies) {
        this.savedMovies.clear();
        this.savedMovies.addAll(newMovies);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public SavedMovie savedMovies;
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
    public SavedMovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);



        return new SavedMovieRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedMovieRecyclerViewAdapter.ViewHolder holder, int position) {
        final SavedMovie saved = savedMovies.get(position);
        if(saved != null){
            holder.txtTitle.setText(saved.getSavedMovieCode());
            holder.txtDescription.setText(saved.getSavedDescription());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("saved_movie_pk", saved.getId());

                    SavedMovieDetailsFragment savedMovieDetailsFragment = new SavedMovieDetailsFragment();
                    savedMovieDetailsFragment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, savedMovieDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    public void clear() {
        int size = this.savedMovies.size();
        this.savedMovies.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return savedMovies.size();
    }
}
