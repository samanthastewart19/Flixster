package samantha.stewart.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import models.Movie;
import okhttp3.Headers;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyDUvo5EMOfXQWwDb8YGzXH--nJmQ0VFv7Q";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    TextView tvTitle;
    TextView tvOverView;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverView = findViewById(R.id.tvOverView);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);


        //String title = getIntent().getStringExtra("title"); //retrieving the title
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie")); //retrieving the movie object
        tvTitle.setText(movie.getTitle()); //setting the title when movie title is clicked
        tvOverView.setText(movie.getOverView());
        ratingBar.setRating(movie.getRating());
        Log.d("DetailActivity", "ratingBar: " + ratingBar);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youtubeKey);
                    initializeYoutube(youtubeKey);

                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                    //e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });
    }


        private void initializeYoutube( final String youtubeKey) {
            youTubePlayerView.initialize(YOUTUBE_API_KEY,
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {
                            Log.d("DetailActivity", "onInitializationSuccess");
                            // do any work here to cue video, play video, etc.
                            youTubePlayer.cueVideo(youtubeKey);
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {
                            Log.d("DetailActivity", "onInitializationFailure");

                        }
            });
        }
    }