package com.example.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //declaring class variables
    //type of link
    private final String BySearch = "BySearch";
    private final String ByPage = "ByPage";

    //recycler view
    private RecyclerView mRecyclerView;

    //pagination
    private int pageNum;
    private String totalResults;
    private String pageNumStr;
    final String pageURL = "&page=";
    private String input;

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

    //receive search entry
    public void sendMessage(View view) {

        EditText inputText = findViewById(R.id.movieSearch);
        input = inputText.getText().toString();

        pageNum = 1;

        //send string to make url
        requestType(BySearch);
    }

    //get the url depending on the request type
    public void requestType(String type) {
        final String KEY = "&apikey=437cc919";
        final String searchURL = "https://www.omdbapi.com/?s=";
        String url;

        switch (type) {
            //runs if searching
            case BySearch:
                url = searchURL + input + KEY + pageURL + pageNum;
                makeRequest(url);
                Log.d("urlType", "search");
                break;
            //runs if displaying data
            case ByPage:
                url = searchURL + input + KEY;
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

            //create new lists for the search
            ArrayList<String> mPosterUrls = new ArrayList<>();
            ArrayList<String> mMovieNames = new ArrayList<>();

            //parses the data
            JSONArray searchArray = response.getJSONArray("Search");
            totalResults = response.getString("totalResults");

            //runs for each search entry
            for (int i = 0; i < searchArray.length(); i++) {
                JSONObject movie = searchArray.getJSONObject(i);
                String movieTitle = movie.getString("Title");
                String moviePoster = movie.getString("Poster");

                mPosterUrls.add(moviePoster);
                mMovieNames.add(movieTitle);
            }

            //sends to recycler view
            initRecycle(mPosterUrls, mMovieNames);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //runs recycler view
    private void initRecycle(ArrayList mPosterUrls, ArrayList mMovieNames) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mPosterUrls, mMovieNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //allows user to log out
    public void logout(View view) {
        Toast.makeText(this, "Logging Out...", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    //checks the favorite movies for the person
    public void checkFavorites (View view) {

        //reads the favorites from database and assigns to recycler view
        mRecyclerView = findViewById(R.id.favoritesRecycler);
        new FirebaseDatabaseHelper().readFavs(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Favorites> favorites, List<String> keys) {
                new FavoritesRecycler_Config().setConfig(mRecyclerView, MainActivity.this, favorites, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    public void nextPage(View view) {
        int results = Integer.parseInt(totalResults);
        int totalPages = results/10;

        if (pageNum <= totalPages) {
            pageNum++;
            requestType(BySearch);
        } else {
            Toast.makeText(this, "No new pages", Toast.LENGTH_SHORT).show();
        }
    }

    public void prevPage(View view) {
        if (pageNum == 1) {
            Toast.makeText(this, "No previous pages", Toast.LENGTH_SHORT).show();
        } else {
            pageNum--;
            requestType(BySearch);
        }
    }
}