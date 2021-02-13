package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    int movieId;
    String backdropPath;
    String poster_Path;
    String title;
    String overview;
    float rating;

    //empty constructor needed by Parcel library
    public Movie() {

    }

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        poster_Path = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = (float) jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int i=0; i<movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPoster_Path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", poster_Path);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }


//    public void setPoster_Path(String poster_Path) {
//
//        this.poster_Path = poster_Path;
//    }

    public String getTitle() {

        return title;
    }

//    public void setTitle(String title) {
//
//        this.title = title;
//    }

    public String getOverView() {

        return overview;
    }

//    public void setOverView(String overView) {
//
//        this.overview = overView;
//    }

    public float getRating() {
        return rating;
    }

    public int getMovieId() {
        return movieId;
    }
}
