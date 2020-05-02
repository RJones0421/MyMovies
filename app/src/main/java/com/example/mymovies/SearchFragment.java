package com.example.mymovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class SearchFragment extends Fragment {

    //request types
    private final String BySearch = "BySearch";
    private final String ByIMDB = "ByIMDB";

    private Context mContext;

    //page number
    private double pageNum;
    private String pageNumStr;
    public JSONObject response;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    MainActivity mainActivity = new MainActivity();

    public void sendMessage(View view) {

        EditText inputText = getView().findViewById(R.id.movieSearch);
        String input = inputText.getText().toString();

        Log.d("inputTest1", input);

        mainActivity.requestType(input, BySearch);
    }

    //get the url depending on the request type
    /*
    public void requestType(String input, String type) {
        final String KEY = "&apikey=437cc919";
        final String searchURL = "https://www.omdbapi.com/?s=something&apikey=437cc919";
        final String imdbURL = "https://api.openweathermap.org/data/2.5/weather?zip=";
        final String pageURL = "&page=";
        String url;

        switch (type) {
            case BySearch:
                url = searchURL;
                makeRequest(url);
                Log.d("urlType", "search for " + url);
                break;
            case ByIMDB:
                url = imdbURL + input + KEY;
                makeRequest(url);
                Log.d("urlType", "IMDB");
                break;
        }
    }
     */
}
