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

    private MainActivity mainActivity = new MainActivity();

    public void sendMessage(View view) {

        EditText inputText = getView().findViewById(R.id.movieSearch);
        String input = inputText.getText().toString();

        Log.d("inputTest", input);

        mainActivity.requestType(input, BySearch);
    }


}
