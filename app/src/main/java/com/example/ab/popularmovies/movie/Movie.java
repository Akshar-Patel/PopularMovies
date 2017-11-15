package com.example.ab.popularmovies.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ab on 14/11/17.
 */

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    int id;
    String title;
    String poster;
    Float votesAverage;
    String releaseDate;
    String overview;

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster = in.readString();
        this.votesAverage = (Float) in.readValue(Float.class.getClassLoader());
        this.releaseDate = in.readString();
        this.overview = in.readString();
    }

    public Float getVotesAverage() {
        return votesAverage;
    }

    public void setVotesAverage(Float votesAverage) {
        this.votesAverage = votesAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster);
        dest.writeValue(this.votesAverage);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
    }
}
