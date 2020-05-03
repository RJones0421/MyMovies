package com.example.mymovies;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    private MainActivity mainActivity = new MainActivity();

    public void getUserID(View view) {
        String userID = "123abc";
        mainActivity.checkFavorites(userID);
    }

    /*
    private void initRecycle(ArrayList mPosterUrls, ArrayList mMovieNames) {
        RecyclerView recyclerView = getView().findViewById(R.id.favoritesRecycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mContext, mPosterUrls, mMovieNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }
    */
}
