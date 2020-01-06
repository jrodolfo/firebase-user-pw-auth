package com.jrodolfo.firebaseuserpwauth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private TextView emailAddressText;
    private TextView idText;
    private TextView verifiedAccountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        emailAddressText = findViewById(R.id.email_address_text);
        idText = findViewById(R.id.id_text);
        verifiedAccountText = findViewById(R.id.verified_account_text);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            emailAddressText.setText(firebaseUser.getEmail());
            idText.setText(firebaseUser.getUid());
            checkAccountVerification();
        }

        Button updateEmailButton = findViewById(R.id.update_email_button);
        updateEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UpdateEmailActivity.class);
                startActivity(intent);
            }
        });

        Button updatePasswordButton = findViewById(R.id.update_password_button);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                Toast.makeText(HomeActivity.this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
            }
        });

        Button loadWebsiteButton = findViewById(R.id.load_website_button);
        loadWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoadWebViewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Logout?");
        builder.setMessage("Are you sure you want to log out?");
        AlertDialog dialog = builder.create();
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();
                finish();
                Toast.makeText(HomeActivity.this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            emailAddressText.setText(firebaseUser.getEmail());
            idText.setText(firebaseUser.getUid());
        }
    }

    public void checkAccountVerification() {
        boolean emailIsVerified = firebaseUser.isEmailVerified();
        if (emailIsVerified) {
            verifiedAccountText.setText("Account is verified");
            verifiedAccountText.setTextColor(getResources().getColor(R.color.green));
        }
    }
}
