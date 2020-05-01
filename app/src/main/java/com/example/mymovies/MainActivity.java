package com.example.mymovies;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymovies.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String BySearch = "BySearch";
    private final String ByIMDB = "ByIMDB";

    private double pageNum = 1;
    private String pageNumStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


    public void sendMessage(View view) {

        EditText inputText = findViewById(R.id.movieSearch);
        String input = inputText.getText().toString();

        Log.d("inputTest", input);

        requestType(input, BySearch);
    }

    //get the url depending on the request type
    public void requestType(String input, String type) {
        final String KEY = "&apikey=437cc919";
        final String searchURL = "https://www.omdbapi.com/?s=";
        final String imdbURL = "https://www.omdbapi.com/?i=";
        final String pageURL = "&page=";
        String url;

        switch (type) {
            case BySearch:
                url = searchURL + input + KEY;
                makeRequest(url);
                Log.d("urlType", "search");
                break;
            case ByIMDB:
                url = imdbURL + input + KEY;
                makeRequest(url);
                Log.d("urlType", "IMDB");
                break;
        }
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

            ArrayList<String> mPosterUrls = new ArrayList<>();
            ArrayList<String> mMovieNames = new ArrayList<>();


            JSONArray searchArray = response.getJSONArray("Search");

            for (int i = 0; i < searchArray.length(); i++) {
                JSONObject movie = searchArray.getJSONObject(i);
                String movieTitle = movie.getString("Title");
                String moviePoster = movie.getString("Poster");

                mPosterUrls.add(moviePoster);
                mMovieNames.add(movieTitle);
            }

            initRecycle(mPosterUrls, mMovieNames);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecycle(ArrayList mPosterUrls, ArrayList mMovieNames) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mPosterUrls, mMovieNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}