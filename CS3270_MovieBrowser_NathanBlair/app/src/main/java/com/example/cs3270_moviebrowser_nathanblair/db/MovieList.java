package com.example.cs3270_moviebrowser_nathanblair.db;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {
    @SerializedName("movie_list")
    public List<Movie> movies;
}
