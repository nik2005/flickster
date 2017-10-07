package com.akurghin.flickster.models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    private static final String IMAGE_URL_PREFIX = "http://image.tmdb.org/t/p/%s/%s";
    private static final String POSTER_WIDTH = "w342";
    private static final String BACKDROP_WIDTH = "w780";

    private int id;
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private String releaseDate;
    private int voteCount;
    private double voteAverage;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.releaseDate = jsonObject.getString("release_date");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.voteCount = jsonObject.getInt("vote_count");
    }

    private Movie (Parcel in) {
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readInt();
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return String.format(IMAGE_URL_PREFIX, POSTER_WIDTH, posterPath);
    }

    public String getBackdropPath() {
        return String.format(IMAGE_URL_PREFIX, BACKDROP_WIDTH, backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public static List<Movie> fromJsonArray(JSONArray jsonArray) {
        List<Movie> result = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                result.add(new Movie(jsonArray.getJSONObject(i)));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.originalTitle);
        parcel.writeString(this.overview);
        parcel.writeString(this.posterPath);
        parcel.writeString(this.backdropPath);
        parcel.writeString(this.releaseDate);
        parcel.writeDouble(voteAverage);
        parcel.writeInt(voteCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
