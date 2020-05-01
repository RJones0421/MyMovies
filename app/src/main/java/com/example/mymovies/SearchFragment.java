package com.example.mymovies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    //request types
    private final String BySearch = "BySearch";
    private final String ByIMDB = "ByIMDB";

    //page number
    private double pageNum;
    private String pageNumStr;
    public JSONObject response;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void sendMessage(View view) {

        EditText inputText = getView().findViewById(R.id.movieSearch);
        String input = inputText.getText().toString();

        Log.d("inputTest", input);

        requestType(input, BySearch);
    }

<<<<<<< HEAD
=======
    RequestActivity requestActivity = new RequestActivity();

    //get the url depending on the request type
    public void requestType(String input, String type) {
        final String KEY = "&apikey=437cc919";
        final String searchURL = "https://www.omdbapi.com/?s=something&apikey=437cc919";
        final String imdbURL = "https://api.openweathermap.org/data/2.5/weather?zip=";
        final String pageURL = "&page=";
        String url;

        switch (type) {
            case BySearch:
                url = searchURL;
                requestActivity.makeRequest(url);
                Log.d("urlType", "search for " + url);
                break;
            case ByIMDB:
                url = imdbURL + input + KEY;
                requestActivity.makeRequest(url);
                Log.d("urlType", "IMDB");
                break;
        }
    }
>>>>>>> efe6457d57df1988a20f16ef9bb88320a1a070d3

}
