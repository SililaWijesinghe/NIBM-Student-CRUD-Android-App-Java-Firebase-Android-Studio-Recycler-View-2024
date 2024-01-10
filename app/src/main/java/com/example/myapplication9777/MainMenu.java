package com.example.myapplication9777;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth auth;

    public TextView user_name, user_email;

    private Button signoutBtn;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        auth = FirebaseAuth.getInstance();

        user_name = findViewById(R.id.name);
        user_email = findViewById(R.id.email);
        signoutBtn = findViewById(R.id.signOutBtn);

        // Initialize fab here
        fab = findViewById(R.id.fab);

        //Later I need to pass the data to those below by storing them it in the Login
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

        // Check if fab is not null before setting OnClickListener
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainMenu.this, MainViewActivity.class);
                    startActivity(intent);
                }
            });
        }
    }


    private void signOut() {
        auth.signOut();
        Toast.makeText(this, "Signed out Successfully!", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(MainMenu.this, LogIn.class));
    }
}



