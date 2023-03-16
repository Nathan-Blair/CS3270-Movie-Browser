package com.example.cs3270_moviebrowser_nathanblair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.cs3270_moviebrowser_nathanblair.db.AppDatabase;
import com.example.cs3270_moviebrowser_nathanblair.db.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GetMovieList task;
    FragmentManager fm;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new HomeFragment())
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        switch(item.getItemId()){
                            case (R.id.navHome):
                                selectedFragment = new HomeFragment();
                                break;
                            case (R.id.navBrowse):
                                selectedFragment = new BrowseFragment();
                                break;
                            case(R.id.navSaved):
                                selectedFragment = new SavedMoviesFragment();
                                break;
                        }

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer, selectedFragment)
                                .commit();

                        return true;
                    }
                };

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }
}
