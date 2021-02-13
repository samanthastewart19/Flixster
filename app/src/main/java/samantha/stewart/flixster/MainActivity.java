package samantha.stewart.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.MovieAdapter;
import models.Movie;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = " Flixster Main";
    public static String NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    List<Movie> movies = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv =findViewById(R.id.rvMovies);

        final MovieAdapter movieAdapter = new MovieAdapter(this,movies);

       rv.setLayoutManager(new LinearLayoutManager(this));
       rv.setAdapter(movieAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess: Successfully loaded");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.e(TAG,"8**********************************************************");
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Results: "+ results.toString());
                    Log.i(TAG, "Movies: "+ movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "onSuccess: JSON ARRAY failed to return results" );
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
            }
        });

    }
}