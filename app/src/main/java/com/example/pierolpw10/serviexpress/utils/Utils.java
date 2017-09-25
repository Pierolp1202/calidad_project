package com.example.pierolpw10.serviexpress.utils;

import android.widget.EditText;

/**
 * Created by NriKe on 24/09/2017.
 */

public class Utils {

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
        user.setError(null);
        pass.setError(null);
        email.setError(null);
        address.setError(null);

        if(name.getText().toString().isEmpty()){
            cancel = true;
            name.setError("Error");
        }

        if(lastName.getText().toString().isEmpty()){
            cancel = true;
            lastName.setError("Error");
        }

        if(user.getText().toString().isEmpty()){
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
