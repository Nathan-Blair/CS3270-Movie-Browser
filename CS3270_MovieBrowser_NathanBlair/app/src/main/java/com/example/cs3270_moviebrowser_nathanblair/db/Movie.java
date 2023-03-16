package com.example.cs3270_moviebrowser_nathanblair.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String movieCode;
    private String title;
    private String description;
    private String image;

    public Movie(String movieCode, String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.movieCode = movieCode;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieid=" + id +
                ", title='" + title + '\'' +
                ", id='" + movieCode + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int movieid) {
        this.id = movieid;
    }

    public String getMovieCode() {
        return movieCode;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
