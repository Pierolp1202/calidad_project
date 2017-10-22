package com.example.pierolpw10.serviexpress.Managers;

import com.example.pierolpw10.serviexpress.Models.User;
import com.example.pierolpw10.serviexpress.Utils.FirebaseConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by NriKe on 27/06/2017.
 */

public class FirebaseManager {
    private FirebaseDatabase database;
    private DatabaseReference usersReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    User user;

    private static FirebaseManager instance;

    public FirebaseManager() {
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.CHILD_USERS);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public static FirebaseManager getInstance(){
        if (instance == null){
            instance = new FirebaseManager();
        }
        return instance;
    }

    public DatabaseReference getUsersReference() {
        return usersReference;
    }

    public void getUserInfo(){
        if (firebaseUser!=null){
            usersReference.child(firebaseUser.getUid()).removeEventListener(userValueEventListener);
            usersReference.child(firebaseUser.getUid()).addValueEventListener(userValueEventListener);
        }
    }

    ValueEventListener userValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            user = dataSnapshot.getValue(User.class);
            if(user != null){
                singleValueEventListener.onSuccess();
            }else{
                singleValueEventListener.onError();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    //Interfaces
    private OnSingleValueEventListener singleValueEventListener;

    public interface OnSingleValueEventListener{
        void onSuccess();
        void onError();
    }

    public void setOnSingleValueEventListener(OnSingleValueEventListener listener) {
        singleValueEventListener = listener;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public User getUser() {
        return user;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

}
