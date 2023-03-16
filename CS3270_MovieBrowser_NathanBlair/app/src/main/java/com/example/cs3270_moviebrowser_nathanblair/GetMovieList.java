package com.example.cs3270_moviebrowser_nathanblair;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cs3270_moviebrowser_nathanblair.db.Movie;
import com.example.cs3270_moviebrowser_nathanblair.db.MovieList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetMovieList extends AsyncTask<String, Integer, String> {

    private String rawJSON;
    public String movieTitle;
    private OnMovieListImport listener;
    JSONObject obj;


    public interface OnMovieListImport{
        void completedMovieList(Movie[] movies);
    }

    public void setOnMovieListImportListener(OnMovieListImport listenerFromMain){
        listener = listenerFromMain;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            URL url = new URL("https://imdb-api.com/en/API/SearchMovie/k_l4ildyxi/" + movieTitle);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer " + Authorization.AUTH_TOKEN);

            connection.connect();

            int status = connection.getResponseCode();

            switch (status){
                case 200:
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    rawJSON = bufferedReader.readLine();
                    obj = new JSONObject(rawJSON);


                    Log.d("GetMovieList", "doInBackground: " + rawJSON.toString());
                    break;
            }

        }catch (Exception e){
            Log.d("GetMovieList", "doInBackground" + e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        List<Movie> movieList = new ArrayList<Movie>();
        Movie movie;// = new Movie(null, null, null, null);
        JSONArray arr;
        Movie[] movies;
        try{

            arr = obj.getJSONArray("results");
            movies = new Movie[arr.length()];
            //Movie[] movies = new Movie[arr.length()];
            for(int i = 0; i < arr.length(); i++){
                        movie = new Movie(null, null, null, null);
                        movie.setTitle(arr.getJSONObject(i).getString("title"));
                        movie.setMovieCode(arr.getJSONObject(i).getString("id"));
                        movie.setDescription(arr.getJSONObject(i).getString("description"));
                        movie.setImage(arr.getJSONObject(i).getString("image"));

                movies[i] = movie;
            }

            listener.completedMovieList(movies);
        }catch (Exception e){
            Log.d("GetMovieList", "onPostExecute" + e.toString());
        }

        super.onPostExecute(s);
    }

    private Movie[] parseJson(){

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Movie[] movies = null;
        try{
            movies = gson.fromJson(rawJSON, Movie[].class);
        }catch (Exception e){
            Log.d("GetMovieList", "onPostExecute" + e.toString());
        }
        return movies;
    }
}
