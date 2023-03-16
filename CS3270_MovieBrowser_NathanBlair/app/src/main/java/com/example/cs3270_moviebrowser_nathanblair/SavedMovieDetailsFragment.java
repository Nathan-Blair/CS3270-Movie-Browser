package com.example.cs3270_moviebrowser_nathanblair;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.example.cs3270_moviebrowser_nathanblair.db.AppDatabase;
import com.example.cs3270_moviebrowser_nathanblair.db.SavedMovie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class SavedMovieDetailsFragment extends DialogFragment {

    View view;
    Toolbar toolbar;
    private TextView txtTitle, txtMovieID, txtDescription;
    private Button btnDelete;
    SavedMovie savedMovie;
    int saved_movie_pk;
    ImageView imageView;
    String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_saved_movie_details, container, false);
        toolbar = (Toolbar)view.findViewById(R.id.newMovieDetailsToolbar);
        txtTitle = view.findViewById(R.id.textTitle);
        txtMovieID = view.findViewById(R.id.textMovieID);
        txtDescription = view.findViewById(R.id.textMovieDescription);
        btnDelete = (Button)view.findViewById(R.id.btnDelete);
        imageView = (ImageView)view.findViewById(R.id.imgView);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            saved_movie_pk = bundle.getInt("saved_movie_pk");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    savedMovie = AppDatabase.getInstance(getContext())
                            .savedDAO()
                            .getByID(saved_movie_pk);

                    txtTitle.setText(savedMovie.getSavedTitle());
                    txtMovieID.setText(savedMovie.getSavedMovieCode());
                    txtDescription.setText(savedMovie.getSavedDescription());
                    path = savedMovie.getSavedImage();

                }
            }).start();

        }

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        savedMovie = AppDatabase.getInstance(getContext())
                                .savedDAO()
                                .getByID(saved_movie_pk);

                        savedMovie.setSavedTitle(null);
                        savedMovie.setSavedMovieCode(null);
                        savedMovie.setSavedDescription(null);
                        path = null;

                        AppDatabase.getInstance(getContext())
                                .savedDAO()
                                .delete(savedMovie);
                    }
                }).start();
                dismiss();
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
                savedMovie = AppDatabase.getInstance(getContext())
                        .savedDAO()
                        .getByID(saved_movie_pk);

                txtTitle.setText(savedMovie.getSavedMovieCode());
                txtMovieID.setText(savedMovie.getSavedTitle());
                txtDescription.setText(savedMovie.getSavedDescription());
                path = savedMovie.getSavedImage();

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
