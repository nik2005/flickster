package com.akurghin.flickster;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akurghin.flickster.models.Movie;
import com.akurghin.flickster.models.YouTubeTrailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class MoviewDetailActivity extends YouTubeBaseActivity {
    public static final String MOVIE_KEY = "movie";

    private static final int MAX_RATING = 10;
    private static final String YOUTUBE_API_KEY = "AIzaSyBzxUV0FWVoVa-7Fq1wHuvX5CxyhsniCR8";
    private static final String MOVIE_TRAILER_URL = "https://api.themoviedb.org/3/movie/%s/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private List<YouTubeTrailer> youTubeTrailers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moview_detail);

        Movie movie = getIntent().getParcelableExtra(MOVIE_KEY);

        TextView tvMovieTitle = findViewById(R.id.tvMovieTitle);
        TextView tvMovieOverview = findViewById(R.id.tvMovieOverview);
        TextView tvMovieRating = findViewById(R.id.tvMovieRating);
        TextView tvMovieReviewCount = findViewById(R.id.tvMovieReviewCount);
        TextView tvMovieReleaseDate = findViewById(R.id.tvMovieReleaseDate);

        RatingBar rbMovieRating = findViewById(R.id.rbMovieRating);
        rbMovieRating.setRating((float) movie.getVoteAverage() / 2);

        tvMovieTitle.setText(movie.getOriginalTitle());
        tvMovieOverview.setText(movie.getOverview());
        tvMovieRating.setText(String.format("Average rating: %s/%s", movie.getVoteAverage(), MAX_RATING));
        tvMovieReviewCount.setText(String.format("Review count: %s", movie.getVoteCount()));
        tvMovieReleaseDate.setText(String.format("Release date: %s", movie.getReleaseDate()));

        final YouTubePlayerView playerView = findViewById(R.id.player);

        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(String.format(MOVIE_TRAILER_URL, movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray trailersJsonResult;

                try {
                    trailersJsonResult = response.getJSONArray("youtube");
                    youTubeTrailers.addAll(YouTubeTrailer.fromJSONArray(trailersJsonResult));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final List<String> youTubeMovieIds = new ArrayList<>();

                for(YouTubeTrailer youtube : youTubeTrailers) {
                    youTubeMovieIds.add(youtube.getSource());
                }

                playerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                            YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer.cueVideos(youTubeMovieIds);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                            YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
