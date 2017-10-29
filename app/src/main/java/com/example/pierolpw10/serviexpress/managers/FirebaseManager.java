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
    private DatabaseReference workersReference;
    private DatabaseReference worksReference;

    private static FirebaseManager instance;

    public FirebaseManager() {
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.CHILD_USERS);
        workersReference = database.getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORKERS_REF);
        worksReference = database.getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF);
    }

    public FirebaseDatabase getDatabase() {
        return database;
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

    public DatabaseReference getWorkersReference() {
        return workersReference;
    }

    public DatabaseReference getWorksReference() {
        return worksReference;
    }
}
