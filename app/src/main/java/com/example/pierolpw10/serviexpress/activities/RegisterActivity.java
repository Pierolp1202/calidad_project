package com.example.pierolpw10.serviexpress.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.utils.Utils;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_last_name;
    EditText et_user;
    EditText et_pass;
    EditText et_email;
    EditText et_address;
    ImageButton bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();
    }

    private void setupViews() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_user = (EditText) findViewById(R.id.et_user);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_email = (EditText) findViewById(R.id.et_email);
        et_address = (EditText) findViewById(R.id.et_address);
        bt_register = (ImageButton) findViewById(R.id.bt_register);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utils.verifyRegister(et_name,et_last_name,et_user,et_pass,et_email,et_address)){
                    //TODO: call firebase db
                }
            }
        });
    }
}
