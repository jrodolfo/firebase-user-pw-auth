package com.jrodolfo.firebaseuserpwauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUpEmail, signUpPassword, signUpRepeatPassword;
    private ValidateInput validateInput;
    private FirebaseAuth mAuth;
    private LoadingAnimation loadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Authorization
        mAuth = FirebaseAuth.getInstance();

        // Loading animation
        loadingAnimation = new LoadingAnimation(SignUpActivity.this);

        ImageView arrowLeft = findViewById(R.id.arrow_left);
        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signUpEmail = findViewById(R.id.sign_up_email);
        signUpPassword = findViewById(R.id.sign_up_password);
        signUpRepeatPassword = findViewById(R.id.sign_up_repeat_password);
        validateInput = new ValidateInput(SignUpActivity.this, signUpEmail, signUpPassword, signUpRepeatPassword);

        Button signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpNewAccount();
            }
        });
    }

    public void signUpNewAccount() {

        boolean emailIsVerified = validateInput.validateEmail();
        if (!emailIsVerified) {
            signUpEmail.setText("");
            signUpEmail.requestFocus();
            return;
        }

        boolean passwordIsVerified = validateInput.validatePassword();
        if (!passwordIsVerified) {
            signUpPassword.setText("");
            signUpPassword.requestFocus();
            return;
        }

        boolean repeatPasswordIsVerified = validateInput.validateRepeatPassword();
        if (!repeatPasswordIsVerified) {
            signUpPassword.setText("");
            signUpRepeatPassword.setText("");
            signUpPassword.requestFocus();
            return;
        }

        loadingAnimation.loadingAnimationDialog();

        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Toast.makeText(SignUpActivity.this, "User '" + user.getEmail() + "' was successfully created.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "User was successfully created.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            loadingAnimation.dismissLoadingAnimation();
                        }
                    }
                });
    }

}
