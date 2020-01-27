package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Headers;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

public class DetailActivity extends YouTubeBaseActivity {
    private static final String YOUTUBE_API_KEY="AIzaSyAcYpvSP3SJgvVjQy4BeRLhhLZvUfZAlmM";
    public static final String VIDEOS_URL="https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    TextView tvTitle;
    RatingBar ratingBar;
    TextView tvOverview;
    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Movie movie= Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle=findViewById(R.id.tvTitle);
        ratingBar=findViewById(R.id.ratingBar);
        youTubePlayerView=findViewById(R.id.player);
        tvOverview=findViewById(R.id.tvOverview);
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float)movie.getRating());

        AsyncHttpClient client= new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results =json.jsonObject.getJSONArray("results");
                    if(results.length()==0)
                    { return;}
                    String youTubeKey=results.getJSONObject(0).getString("key");
                    initializeYoutube(youTubeKey);


                } catch (JSONException e) {
                    Log.e("DetailsActivity","Did not parse JSON Array");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });





    }

    private void initializeYoutube(final String youTubeKey) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                Log.d("DetailActivity","InitializationSuccess");
                youTubePlayer.cueVideo(youTubeKey);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                Log.d("DetailActivity","InitializationFailure");

            }
        });
    }
}
