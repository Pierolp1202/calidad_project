package com.example.pierolpw10.serviexpress.Utils;

import android.widget.EditText;

import com.example.pierolpw10.serviexpress.Managers.FirebaseManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NriKe on 24/09/2017.
 */

public class Utils {

    public static List<String> getListOfUsers(FirebaseManager manager){
        final List<String> users = new ArrayList<>();
        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.CHILD_USERS) != null){
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.CHILD_USERS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        users.add(data.getKey());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        return users;
    }

    public static boolean verifyLogin(EditText user, EditText pass){
        boolean cancel = false;
        user.setError(null);
        pass.setError(null);

        if(user.getText().toString().isEmpty()){
            cancel = true;
            user.setError("Error");
        }

        if(pass.getText().toString().isEmpty()){
            cancel = true;
            pass.setError("Error");
        }

        return cancel;
    }

    public static boolean verifyRegister(EditText name, EditText lastName, EditText user, EditText pass, EditText email, EditText address){
        boolean cancel = false;
        name.setError(null);
        lastName.setError(null);
        pass.setError(null);
        email.setError(null);
        address.setError(null);
        user.setError(null);

        if(name.getText().toString().isEmpty()){
            cancel = true;
            name.setError("Error");
        }

        if(lastName.getText().toString().isEmpty()){
            cancel = true;
            lastName.setError("Error");
        }

        if(user.getText().toString().isEmpty() || user.getText().toString().contains(".") || user.getText().toString().contains("$") || user.getText().toString().contains("#")){
            cancel = true;
            user.setError("Error");
        }

        if(pass.getText().toString().isEmpty()){
            cancel = true;
            pass.setError("Error");
        }

        if(email.getText().toString().isEmpty()){
            cancel = true;
            email.setError("Error");
        }

        if(address.getText().toString().isEmpty()){
            cancel = true;
            address.setError("Error");
        }

        return cancel;
    }
}
