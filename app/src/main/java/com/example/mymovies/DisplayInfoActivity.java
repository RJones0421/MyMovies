package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DisplayInfoActivity extends AppCompatActivity {

    //declare class variables
    private String movieTitle;
    private String posterUrl;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        //get the incoming intent and use it for the parse
        getIncomingIntent();
    }

    //receive the data from the onClick
    private void getIncomingIntent() {
        if (getIntent().hasExtra("movie_title")) {
            String movieTitle = getIntent().getStringExtra("movie_title");
            key = getIntent().getStringExtra("key");

            requestType(movieTitle);
        }
    }

    //make the url for the api call
    public void requestType(String input) {
        final String KEY = "&apikey=437cc919";
        final String titleURL = "https://www.omdbapi.com/?t=";
        final String plotURL = "&plot=full";
        String url;

        url = titleURL + input + KEY + plotURL;
        makeRequest(url);
    }

    //call the API using the url
    public void makeRequest(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        DisplayMovieResults(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        queue.add(jsonObjectRequest);
    }

    //get the data from the call and display it in the UI
    protected void DisplayMovieResults(JSONObject response) {
        try {

            TextView titleDesc = findViewById(R.id.somethingElse);
            ImageView poster = findViewById(R.id.posterInfo);
            TextView plotDesc = findViewById(R.id.plotDesc);
            TextView actorsDesc = findViewById(R.id.actorDesc);
            TextView dirDesc = findViewById(R.id.dirDesc);

            movieTitle = response.getString("Title");
            posterUrl = response.getString("Poster");
            String plot = response.getString("Plot");
            String actors = response.getString("Actors");
            String director = response.getString("Director");

            titleDesc.setText(movieTitle);
            plotDesc.setText(plot);
            actorsDesc.setText(actors);
            dirDesc.setText(director);

            Glide.with(this)
                    .asBitmap()
                    .load(posterUrl)
                    .into(poster);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //add a new movie to favorites when button clicked
    public void addToFavorites(View view) {

        String uid = getCurrentUser();

        Favorites favorite = new Favorites();
        favorite.setUserID(uid);
        favorite.setMovieTitle(movieTitle);
        favorite.setPosterURL(posterUrl);

        new FirebaseDatabaseHelper().addFavorite(favorite, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Favorites> favorites, List<String> keys) {

            }

            @Override
            public void DataIsInserted() {
                Toast.makeText(DisplayInfoActivity.this, "This movie has been added to favorites", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    //delete the movie from favorites when clicked
    public void removeFromFavorites(View view) {

        key = getIntent().getStringExtra("key");

        new FirebaseDatabaseHelper().deleteFavorite(key, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Favorites> favorites, List<String> keys) {

            }

            @Override
            public void DataIsInserted() {
            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {
                Toast.makeText(DisplayInfoActivity.this, "This movie has been removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //find the current user to verify to add a fav to that user
    public String getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return null;
        }
    }
}
