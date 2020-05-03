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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FavoritesRecycler_Config {

    private Context mContext;
    private FavoritesAdapter mFavoritesAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Favorites> favorites, List<String> keys) {
        mContext = context;
        mFavoritesAdapter = new FavoritesAdapter(favorites, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mFavoritesAdapter);
    }

    class FavoriteItemView extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mPoster;
        private String mUserID;

        private String key;

        public FavoriteItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.layout_listitem, parent, false));

            mTitle = itemView.findViewById(R.id.movieTitle);
            mPoster = itemView.findViewById(R.id.moviePoster);
        }

        public void bind(Favorites favorite, String key) {
            mUserID = favorite.getUserID();

            if(mUserID.equals("123abc")) {
                mTitle.setText(favorite.getMovieTitle());

                Glide.with(mContext)
                        .asBitmap()
                        .load(favorite.getPosterURL())
                        .into(mPoster);

                this.key = key;
            }
        }
    }

    class FavoritesAdapter extends RecyclerView.Adapter<FavoriteItemView> {
        private List<Favorites> mFavList;
        private List<String> mKeys;

        public FavoritesAdapter(List<Favorites> mFavList, List<String> mKeys) {
            this.mFavList = mFavList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public FavoriteItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FavoriteItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteItemView holder, int position) {
            holder.bind(mFavList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mFavList.size();
        }
    }
}
