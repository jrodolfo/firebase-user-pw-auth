package com.jrodolfo.firebaseuserpwauth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText signInEmail, signInPassword;
    private ValidateInput validateInput;
    private FirebaseAuth mAuth;
    private LoadingAnimation loadingAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Authorization
        mAuth = FirebaseAuth.getInstance();

        // Loading animation
        loadingAnimation = new LoadingAnimation(MainActivity.this);

        signInEmail = findViewById(R.id.sign_in_email);
        signInPassword = findViewById(R.id.sign_in_password);
        validateInput = new ValidateInput(MainActivity.this, signInEmail, signInPassword);

        TextView createAccountTextView = findViewById(R.id.create_account_txt);
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInAccount();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Toast.makeText(this, "User: " + firebaseUser.getEmail() + " is already logged in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
        }
    }

    public void signInAccount() {

        boolean emailIsVerified = validateInput.validateEmail();
        if (!emailIsVerified) {
            signInEmail.setText("");
            signInEmail.requestFocus();
            return;
        }

        boolean passwordIsVerified = validateInput.validatePassword();
        if (!passwordIsVerified) {
            signInPassword.setText("");
            signInPassword.requestFocus();
            return;
        }

        String email = signInEmail.getText().toString().trim();
        String password = signInPassword.getText().toString().trim();

        loadingAnimation.loadingAnimationDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Toast.makeText(MainActivity.this, "Authentication " +
                                                "worked for user " + user.getEmail() + ".",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication " +
                                                "worked.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        loadingAnimation.dismissLoadingAnimation();
                    }
                });
    }

}
