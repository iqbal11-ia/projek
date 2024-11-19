package com.example.finalprojectkelompok22;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
     private FirebaseAuth mAuth;
     private EditText emailInput, passswordInput;
     private Button loginButton;
     private TextView regristerTextView;
     private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.emailInput);
        passswordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        regristerTextView = findViewById(R.id.regristerTextView);

        loginButton.setOnClickListener(view -> loginUser());
        regristerTextView.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));


    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passswordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText( this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        if(auth != null) {
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            Toast.makeText(this, "Login Berhasil",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Login Gagal : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this,"Firebase Auth tidak terinisialisasi",Toast.LENGTH_SHORT).show();
        }
    }
}