package com.example.pierolpw10.serviexpress.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pierolpw10.serviexpress.Managers.FirebaseManager;
import com.example.pierolpw10.serviexpress.Models.User;
import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.Utils.PreferenceManager;
import com.google.gson.Gson;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText old_pass;
    EditText new_pass;
    EditText confirm_new_pass;
    Button bt_save;

    PreferenceManager p_manager;
    FirebaseManager manager;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        old_pass = (EditText) findViewById(R.id.et_old_pass);
        new_pass = (EditText) findViewById(R.id.et_new_pass);
        confirm_new_pass = (EditText) findViewById(R.id.et_confirm_old_pass);
        bt_save = (Button) findViewById(R.id.bt_save);

        p_manager = PreferenceManager.getInstance(this);

        Gson gson = new Gson();
        user = gson.fromJson(p_manager.getPreferenceSession(), User.class);

        manager = FirebaseManager.getInstance();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!confirmar()){
                    //TODO: actualizar password
                    Toast.makeText(ChangePasswordActivity.this,"Contraseña actualizada",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean confirmar() {
        boolean cancel = false;

        new_pass.setError(null);
        confirm_new_pass.setError(null);
        old_pass.setError(null);

        if(new_pass.getText().toString().isEmpty() || confirm_new_pass.getText().toString().isEmpty()){
            new_pass.setError("Ingrese una nueva contraseña");
            cancel = true;
        }

        if(!confirm_new_pass.getText().toString().equals(new_pass.getText().toString())){
            confirm_new_pass.setError("Las contraseñas no coinciden");
            cancel = true;
        }

        if(!old_pass.getText().toString().equals(user.getPassword().trim())){
            old_pass.setError("La contraseña anterior no coincide");
            cancel = true;
        }

        return cancel;
    }
}
