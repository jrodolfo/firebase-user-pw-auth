package com.jrodolfo.firebaseuserpwauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmailActivity extends AppCompatActivity {

    private EditText currentEmail, newEmail;
    private FirebaseUser firebaseUser;
    private ValidateInput validateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        currentEmail = findViewById(R.id.current_email);
        newEmail = findViewById(R.id.new_email);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        validateInput = new ValidateInput(UpdateEmailActivity.this, newEmail);

        setCurrentEmail();

        ImageView arrowLeft = findViewById(R.id.arrow_left_second);
        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button updateEmail = findViewById(R.id.update_email_second_button);
        updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean emailIsVerified = validateInput.validateEmail();

                if (emailIsVerified && firebaseUser != null) {
                    String newEmailStr = newEmail.getText().toString().trim();
                    firebaseUser.updateEmail(newEmailStr);
                    Toast.makeText(UpdateEmailActivity.this,
                            "Email address successfully updated.", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    // wait 1 second to read new email from firebase and then update email EditText
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentEmail();
                        }
                    }, 1000);

                } else {
                    Toast.makeText(UpdateEmailActivity.this,
                            "Invalid email address. Please try it again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView sendVerificationEmailText = findViewById(R.id.send_verification_email_text);
        sendVerificationEmailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser != null) {
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(UpdateEmailActivity.this,
                                "This email, " + firebaseUser.getEmail() + ", was already verified", Toast.LENGTH_SHORT).show();
                    } else {
                        firebaseUser.sendEmailVerification();
                        Toast.makeText(UpdateEmailActivity.this,
                                "Verification email sent to " + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void setCurrentEmail() {
        if (firebaseUser != null) {
            currentEmail.setEnabled(true);
            currentEmail.setText(firebaseUser.getEmail());
            currentEmail.setEnabled(false);
        } else {
            Toast.makeText(this, "Please log in to continue.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
