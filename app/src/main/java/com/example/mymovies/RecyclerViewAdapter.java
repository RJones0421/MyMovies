package com.example.mymovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mMoviePosters = new ArrayList<>();
    private ArrayList<String> mMovieTitles = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> moviePoster, ArrayList<String> movieTitle) {
        mMoviePosters = moviePoster;
        mMovieTitles = movieTitle;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mMoviePosters.get(position))
                .into(holder.moviePoster);

        holder.movieTitle.setText(mMovieTitles.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(mContext, mMovieTitles.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieTitles.size();
    }

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
