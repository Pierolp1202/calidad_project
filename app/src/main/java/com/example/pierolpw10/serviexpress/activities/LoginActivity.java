package com.example.pierolpw10.serviexpress.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.Managers.FirebaseManager;
import com.example.pierolpw10.serviexpress.Models.User;
import com.example.pierolpw10.serviexpress.Utils.FirebaseConstants;
import com.example.pierolpw10.serviexpress.Utils.PreferenceManager;
import com.example.pierolpw10.serviexpress.Utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText et_user;
    EditText et_pass;
    ImageButton bt_login;
    TextView tv_register;

    FirebaseManager manager;
    PreferenceManager p_manager;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = new FirebaseManager();
        p_manager = PreferenceManager.getInstance(this);

        setupViews();

        getListOfUsers();

        verifySession();
    }

    private void verifySession() {
        if(!p_manager.getPreferenceSession().equals("") || !p_manager.getPreferenceSession().isEmpty()){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void getListOfUsers() {
        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.CHILD_USERS) != null){
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.CHILD_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        User user = new User();
                        for (DataSnapshot data_child : data.getChildren()){
                            user.setPassword(data_child.child("password").getValue(String.class));
                            user.setNombres(data_child.child("nombres").getValue(String.class));
                            user.setDireccion(data_child.child("direccion").getValue(String.class));
                            user.setMail(data_child.child("mail").getValue(String.class));
                            user.setUsername(data_child.child("username").getValue(String.class));
                            user.setApellidos(data_child.child("apellidos").getValue(String.class));
                        }
                        user.setUsername(data.getKey());
                        users.add(user);
                    }

                    bt_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!Utils.verifyLogin(et_user,et_pass)){
                                verifyLogin();
                            }
                        }
                    });
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

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void verifyLogin() {
        boolean logged = false;
        for (User u : users){
            if(u.getUsername().trim().equals(et_user.getText().toString().trim()) && u.getPassword().equals(et_pass.getText().toString())){
                logged = true;
                Gson gson = new Gson();
                String json = gson.toJson(u);

                p_manager.setPrefenceSession(json);

                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                break;
            }
        }

        if(!logged){
            Toast.makeText(LoginActivity.this,"Usuario o contraseña invalidos.",Toast.LENGTH_SHORT).show();
        }
    }
}
