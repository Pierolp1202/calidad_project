package com.example.pierolpw10.serviexpress.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pierolpw10.serviexpress.Activities.ChangePasswordActivity;
import com.example.pierolpw10.serviexpress.Models.User;
import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.Managers.PreferenceManager;
import com.google.gson.Gson;

/**
 * Created by NriKe on 22/10/2017.
 */

public class AccountFragment extends Fragment {

    EditText et_name;
    EditText et_last_name;
    EditText et_address;
    EditText et_email;
    EditText et_user;
    Button bt_save;
    TextView tv_change_pass;

    PreferenceManager p_manager;

    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account,container,false);

        et_name = (EditText) v.findViewById(R.id.et_name);
        et_last_name = (EditText) v.findViewById(R.id.et_last_name);
        et_address = (EditText) v.findViewById(R.id.et_address);
        et_email = (EditText) v.findViewById(R.id.et_email);
        et_user = (EditText) v.findViewById(R.id.et_user);
        bt_save = (Button) v.findViewById(R.id.bt_save);
        tv_change_pass = (TextView) v.findViewById(R.id.tv_change_pass);

        tv_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(i);
            }
        });

        p_manager = PreferenceManager.getInstance(getActivity());

        Gson gson = new Gson();
        user = gson.fromJson(p_manager.getPreferenceSession(), User.class);

        fillData();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verifyEdit()){
                    Toast.makeText(getActivity(),"Datos editados correctamente",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private boolean verifyEdit() {
        boolean cancel = false;

        et_name.setError(null);
        et_last_name.setError(null);
        et_address.setError(null);
        et_email.setError(null);
        et_user.setError(null);

        if(et_name.getText().toString().isEmpty() || et_name.getText().toString().equals("")){
            et_name.setError("Requerido");
            cancel = true;
        }

        if(et_last_name.getText().toString().isEmpty() || et_last_name.getText().toString().equals("")){
            et_last_name.setError("Requerido");
            cancel = true;
        }

        if(et_address.getText().toString().isEmpty() || et_address.getText().toString().equals("")){
            et_address.setError("Requerido");
            cancel = true;
        }

        if(et_email.getText().toString().isEmpty() || et_email.getText().toString().equals("")){
            et_email.setError("Requerido");
            cancel = true;
        }

        if(et_user.getText().toString().isEmpty() || et_user.getText().toString().equals("")){
            et_user.setError("Requerido");
            cancel = true;
        }

        return cancel;
    }

    private void fillData() {
        et_name.setText(user.getNombres());
        et_last_name.setText(user.getApellidos());
        et_address.setText(user.getDireccion());
        et_email.setText(user.getMail());
        et_user.setText(user.getUsername());
    }
}
