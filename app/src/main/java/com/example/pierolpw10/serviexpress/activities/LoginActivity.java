package com.example.pierolpw10.serviexpress.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    EditText et_user;
    EditText et_pass;
    ImageButton bt_login;
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupViews();
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
                    //TODO: verify in firebase db
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
}
