package com.example.myapplication9777;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth auth;

    public TextView user_name, user_email;

    private Button signoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        auth = FirebaseAuth.getInstance();

        user_name = findViewById(R.id.name);
        user_email = findViewById(R.id.email);
        signoutBtn = findViewById(R.id.signOutBtn);

        String userName = getIntent().getStringExtra("USER_NAME");
        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        user_name.setText(userName);
        user_email.setText(userEmail);

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    private void signOut() {
        auth.signOut();
        Toast.makeText(this, "Signed out Successfully!", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(MainMenu.this, LogIn.class));
    }
}



