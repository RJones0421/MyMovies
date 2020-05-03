package com.example.mymovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //declaring class variables
    private ArrayList<String> mMoviePosters;
    private ArrayList<String> mMovieTitles;
    private Context mContext;

    //assigning the data to variables in the class
    public RecyclerViewAdapter(Context context, ArrayList<String> moviePoster, ArrayList<String> movieTitle) {
        mMoviePosters = moviePoster;
        mMovieTitles = movieTitle;
        mContext = context;
    }

    //adds a new section for the next movie
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //assigns the data to the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //poster
        Glide.with(mContext)
                .asBitmap()
                .load(mMoviePosters.get(position))
                .into(holder.moviePoster);

        //title
        holder.movieTitle.setText(mMovieTitles.get(position));

        //displays info when clicked
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(mContext, DisplayInfoActivity.class);
                intent.putExtra("movie_title", mMovieTitles.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    //returns the number of movies in this page
    @Override
    public int getItemCount() {
        return mMovieTitles.size();
    }

    //creates and assigns the variables to the individual view
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView moviePoster;
        TextView movieTitle;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
