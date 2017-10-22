package com.example.pierolpw10.serviexpress.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.Managers.FirebaseManager;
import com.example.pierolpw10.serviexpress.Models.User;
import com.example.pierolpw10.serviexpress.Utils.FirebaseConstants;
import com.example.pierolpw10.serviexpress.Utils.Utils;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_last_name;
    EditText et_pass;
    EditText et_email;
    EditText et_user;
    EditText et_address;
    ImageButton bt_register;

    FirebaseManager manager;
    User newUser;
    List<String> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        manager = new FirebaseManager();

        users = Utils.getListOfUsers(manager);

        setupViews();
    }

    private void setupViews() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_email = (EditText) findViewById(R.id.et_email);
        et_user = (EditText) findViewById(R.id.et_user);
        et_address = (EditText) findViewById(R.id.et_address);
        bt_register = (ImageButton) findViewById(R.id.bt_register);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utils.verifyRegister(et_name,et_last_name,et_user,et_pass,et_email,et_address)){
                    if(!verifyUser(et_user)){
                        createUser(et_name,et_last_name,et_user,et_pass,et_email,et_address);
                    }else{
                        Toast.makeText(RegisterActivity.this,"Usuario existente.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean verifyUser(EditText et_user) {
        boolean cancel = false;
        if(users != null){
            for(String s : users){
                if(s.trim().equals(et_user.getText().toString().trim())){
                    cancel = true;
                    break;
                }
            }
        }

        return cancel;
    }

    private void createUser(EditText name, EditText lastName, EditText user, EditText pass, EditText email, EditText address) {
        newUser = new User();
        newUser.setNombres(name.getText().toString().trim());
        newUser.setApellidos(lastName.getText().toString().trim());
        newUser.setMail(email.getText().toString().trim());
        newUser.setPassword(pass.getText().toString().trim());
        newUser.setUsername(user.getText().toString().trim());
        newUser.setDireccion(address.getText().toString().trim());

        if(manager.getDatabase().getReference(FirebaseConstants.CHILD_USERS) == null){
            manager.getUsersReference().child(FirebaseConstants.CHILD_USERS).push();
        }

        DatabaseReference newRef = manager.getUsersReference().child(newUser.getUsername()).push();
        newRef.setValue(newUser);

        Intent i = new Intent(RegisterActivity.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}
