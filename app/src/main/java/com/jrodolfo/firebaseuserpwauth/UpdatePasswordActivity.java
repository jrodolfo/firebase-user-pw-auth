package com.jrodolfo.firebaseuserpwauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText newPassword, repeatNewPassword;
    private String newPasswordStr, repeatNewPasswordStr;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        newPassword = findViewById(R.id.new_password);
        repeatNewPassword = findViewById(R.id.repeat_new_password);


        ImageView arrowLeft = findViewById(R.id.arrow_left_third);
        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button updatePassword = findViewById(R.id.update_password_button);
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPasswordStr = newPassword.getText().toString().trim();
                repeatNewPasswordStr = repeatNewPassword.getText().toString().trim();
                if (firebaseUser != null) {
                    if (newPasswordStr.equals(repeatNewPasswordStr)) {
                        firebaseUser.updatePassword(newPasswordStr);
                        Toast.makeText(UpdatePasswordActivity.this,
                                "Password successfully updated.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdatePasswordActivity.this,
                                "Passwords don't match. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdatePasswordActivity.this,
                            "Not able to change password since user is null", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
