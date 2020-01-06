package com.jrodolfo.firebaseuserpwauth;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

class ValidateInput {

    private Context context;
    private EditText email, password, repeatPassword;
    private String passwordStr;


    ValidateInput(Context context, EditText email, EditText password) {
        this.context = context;
        this.email = email;
        this.password = password;
    }

    ValidateInput(Context context, EditText email) {
        this.context = context;
        this.email = email;
    }

    ValidateInput(Context context, EditText email, EditText password, EditText repeatPassword) {
        this.context = context;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    boolean validateEmail() {
        String emailStr = email.getText().toString().trim();
        if (emailStr.isEmpty()) {
            Toast.makeText(context, "Please enter your email address.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            Toast.makeText(context, "Invalid email address. Please try it again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    boolean validatePassword() {
        passwordStr = password.getText().toString().trim();
        if (passwordStr.isEmpty()) {
            Toast.makeText(context, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordStr.length() < 8) {
            Toast.makeText(context, "Password is too short. It needs to have at least 8 characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    boolean validateRepeatPassword() {
        String repeatPasswordInput = repeatPassword.getText().toString().trim();
        if (repeatPasswordInput.isEmpty()) {
            Toast.makeText(context, "Please enter your password again.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordStr.equals(repeatPasswordInput)) {
            Toast.makeText(context, "Passwords do not match. Please enter the values again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
