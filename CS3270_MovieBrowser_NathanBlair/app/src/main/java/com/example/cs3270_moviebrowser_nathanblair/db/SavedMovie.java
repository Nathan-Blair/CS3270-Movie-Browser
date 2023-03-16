package com.example.cs3270_moviebrowser_nathanblair.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SavedMovie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String savedMovieCode;
    private String savedTitle;
    private String savedDescription;
    private String savedImage;

    public SavedMovie(String savedMovieCode, String savedTitle, String savedDescription, String savedImage) {
        this.savedMovieCode = savedMovieCode;
        this.savedTitle = savedTitle;
        this.savedDescription = savedDescription;
        this.savedImage = savedImage;
    }

    @Override
    public String toString() {
        return "SavedMovie{" +
                "savedid=" + id +
                ", savedMovieCode='" + savedMovieCode + '\'' +
                ", savedTitle='" + savedTitle + '\'' +
                ", savedDescription='" + savedDescription + '\'' +
                ", savedImage='" + savedImage + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int savedid) {
        this.id = savedid;
    }

    public String getSavedMovieCode() {
        return savedMovieCode;
    }

    public void setSavedMovieCode(String savedMovieCode) {
        this.savedMovieCode = savedMovieCode;
    }

    public String getSavedTitle() {
        return savedTitle;
    }

    public void setSavedTitle(String savedTitle) {
        this.savedTitle = savedTitle;
    }

    public String getSavedDescription() {
        return savedDescription;
    }

    public void setSavedDescription(String savedDescription) {
        this.savedDescription = savedDescription;
    }

    public String getSavedImage() {
        return savedImage;
    }

    public void setSavedImage(String savedImage) {
        this.savedImage = savedImage;
    }
}
