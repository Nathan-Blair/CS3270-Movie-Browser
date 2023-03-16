package com.example.cs3270_moviebrowser_nathanblair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import com.example.cs3270_moviebrowser_nathanblair.db.AppDatabase;
import com.example.cs3270_moviebrowser_nathanblair.db.Movie;
import com.example.cs3270_moviebrowser_nathanblair.db.SavedMovie;

public class MovieDetailsFragment extends DialogFragment {

    View view;
    Toolbar toolbar;
    private TextView txtTitle, txtMovieID, txtDescription;
    private Button btnSave;
    Movie movie;
    SavedMovie savedMovie;
    int movie_pk;
    ImageView imageView;
    String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        toolbar = (Toolbar)view.findViewById(R.id.newMovieDetailsToolbar);
        txtTitle = view.findViewById(R.id.textTitle);
        txtMovieID = view.findViewById(R.id.textMovieID);
        txtDescription = view.findViewById(R.id.textMovieDescription);
        btnSave = (Button)view.findViewById(R.id.btnSave);
        imageView = (ImageView)view.findViewById(R.id.imgView);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            movie_pk = bundle.getInt("movie_pk");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    movie = AppDatabase.getInstance(getContext())
                            .movieDAO()
                            .getByID(movie_pk);

                    txtTitle.setText(movie.getMovieCode());
                    txtMovieID.setText(movie.getTitle());
                    txtDescription.setText(movie.getDescription());
                    path = movie.getImage();

                }
            }).start();

        }

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        savedMovie = new SavedMovie("", "", "", "");
                                savedMovie.setSavedTitle(txtMovieID.getText().toString());
                                savedMovie.setSavedMovieCode(txtTitle.getText().toString());
                                savedMovie.setSavedDescription(txtDescription.getText().toString());
                                savedMovie.setSavedImage(path);

                        AppDatabase.getInstance(getContext())
                                .savedDAO()
                                .insert(savedMovie);
                    }
                }).start();

                Toast toast = Toast.makeText(getActivity(), "Movie has been added to you saved list.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                movie = AppDatabase.getInstance(getContext())
                        .movieDAO()
                        .getByID(movie_pk);

                txtTitle.setText(movie.getMovieCode());
                txtMovieID.setText(movie.getTitle());
                txtDescription.setText(movie.getDescription());
                path = movie.getImage();

            }
        }).start();

        Picasso
                .get()
                .load(path)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("__Load Error__", e.toString());
                    }
                });
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            path = null;
            dismiss();
        }

        return super.onOptionsItemSelected(item);
    }

}