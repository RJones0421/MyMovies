package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("movie_title")) {
            String movieTitle = getIntent().getStringExtra("movie_title");

            requestType(movieTitle);
        }
    }

    public void requestType(String input) {
        final String KEY = "&apikey=437cc919";
        final String titleURL = "https://www.omdbapi.com/?t=";
        final String plotURL = "&plot=full";
        String url;

        url = titleURL + input + KEY + plotURL;
        makeRequest(url);
        Log.d("urlType", "IMDB");

    }

    //call the API using the url
    public void makeRequest(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        DisplayMovieResults(response);
                        Log.d("test", "request passed");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("test", "request failed");
                    }
                });
        queue.add(jsonObjectRequest);
    }

    protected void DisplayMovieResults(JSONObject response) {
        try {

            TextView titleDesc = findViewById(R.id.somethingElse);
            ImageView poster = findViewById(R.id.posterInfo);
            TextView plotDesc = findViewById(R.id.plotDesc);
            TextView actorsDesc = findViewById(R.id.actorDesc);
            TextView dirDesc = findViewById(R.id.dirDesc);

            String movieTitle = response.getString("Title");
            String posterUrl = response.getString("Poster");
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
}
