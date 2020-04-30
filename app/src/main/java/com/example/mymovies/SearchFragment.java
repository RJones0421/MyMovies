package com.example.mymovies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


public class SearchFragment extends Fragment {

    //request types
    private final String BySearch = "BySearch";
    private final String ByIMDB = "ByIMDB";

    //page number
    private double pageNum;
    private String pageNumStr;

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

}
