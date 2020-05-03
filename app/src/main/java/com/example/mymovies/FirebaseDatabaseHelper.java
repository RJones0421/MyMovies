package com.example.mymovies;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefFav;
    private List<Favorites> favorites = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Favorites> favorites, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mRefFav = mDatabase.getReference("favorites");
    }

    public void readFavs(final DataStatus dataStatus) {
        String uid = getCurrentUser();
        mRefFav.orderByChild("userID").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favorites.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {

                    keys.add(keyNode.getKey());
                    Favorites favorite = keyNode.getValue(Favorites.class);
                    favorites.add(favorite);

                }
                dataStatus.DataIsLoaded(favorites, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addFavorite(Favorites favorite, final DataStatus dataStatus) {
        String key = mRefFav.push().getKey();
        mRefFav.child(key).setValue(favorite).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateFavorite(String key, Favorites favorite, final DataStatus dataStatus) {
        mRefFav.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteFavorite(String key, final DataStatus dataStatus) {
        mRefFav.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }

    public String getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return null;
        }
    }
}
