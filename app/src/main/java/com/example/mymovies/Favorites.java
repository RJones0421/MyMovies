package com.example.mymovies;

public class Favorites {
    private String userID;
    private String posterURL;
    private String movieTitle;

    public Favorites() {
    }

    public Favorites(String userID, String posterURL, String movieTitle) {
        this.userID = userID;
        this.posterURL = posterURL;
        this.movieTitle = movieTitle;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
