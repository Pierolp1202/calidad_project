package com.example.pierolpw10.serviexpress.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.managers.FirebaseManager;
import com.example.pierolpw10.serviexpress.models.User;
import com.example.pierolpw10.serviexpress.utils.FirebaseConstants;
import com.example.pierolpw10.serviexpress.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText et_user;
    EditText et_pass;
    ImageButton bt_login;
    TextView tv_register;

    FirebaseManager manager;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = new FirebaseManager();

        getListOfUsers();

        setupViews();
    }

    private void getListOfUsers() {
        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.CHILD_USERS) != null){
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.CHILD_USERS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        User user = new User();
                        for (DataSnapshot data_child : data.getChildren()){
                            user.setPassword(data_child.child("password").getValue(String.class));
                        }
                        user.setUsername(data.getKey());
                        users.add(user);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void setupViews() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_pass = (EditText) findViewById(R.id.et_pass);
        bt_login = (ImageButton) findViewById(R.id.bt_login);
        tv_register = (TextView) findViewById(R.id.tv_register);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utils.verifyLogin(et_user,et_pass)){
                    verifyLogin();
                }
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void verifyLogin() {
        for (User u : users){
            if(u.getUsername().trim().equals(et_user.getText().toString().trim()) && u.getPassword().equals(et_pass.getText().toString())){
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                break;
            }else{
                Toast.makeText(LoginActivity.this,"Usuario o contraseña invalidos.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
