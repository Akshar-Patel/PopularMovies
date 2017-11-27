package com.example.ab.popularmovies.movie;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

  @SuppressWarnings("unused")
  public static final Creator<Movie> CREATOR =
      new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
          return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
          return new Movie[size];
        }
      };
  private int mId;
  private String mTitle;
  private String mPoster;
  private Float mVoteAverage;
  private String mReleaseDate;
  private String mOverview;

  public Movie() {
  }

  private Movie(Parcel in) {
    this.mId = in.readInt();
    this.mTitle = in.readString();
    this.mPoster = in.readString();
    this.mVoteAverage = (Float) in.readValue(Float.class.getClassLoader());
    this.mReleaseDate = in.readString();
    this.mOverview = in.readString();
  }

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    this.mId = id;
  }

  public Float getVoteAverage() {
    return mVoteAverage;
  }

  public void setVoteAverage(Float voteAverage) {
    this.mVoteAverage = voteAverage;
  }

  public String getReleaseDate() {
    return mReleaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.mReleaseDate = releaseDate;
  }

  public String getOverview() {
    return mOverview;
  }

  public void setOverview(String overview) {
    this.mOverview = overview;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    this.mTitle = title;
  }

  public String getPoster() {
    return mPoster;
  }

  public void setPoster(String poster) {
    this.mPoster = poster;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mId);
    dest.writeString(this.mTitle);
    dest.writeString(this.mPoster);
    dest.writeValue(this.mVoteAverage);
    dest.writeString(this.mReleaseDate);
    dest.writeString(this.mOverview);
  }
}
