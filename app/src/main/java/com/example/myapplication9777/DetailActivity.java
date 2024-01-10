package com.example.myapplication9777;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {

    TextView detailName, detailRegNo, detailAge, detailGender, detailContact, detailParent;
    ImageView detailImage;

    FloatingActionButton deleteBtn, editBtn;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName = findViewById(R.id.detailName);
        detailRegNo = findViewById(R.id.detialRegNo);
        detailAge = findViewById(R.id.detialAge);
        detailGender = findViewById(R.id.detialGender);
        detailContact = findViewById(R.id.detialContact);
        detailParent = findViewById(R.id.detialParentNo);
        detailImage = findViewById(R.id.detailImage);
        deleteBtn = findViewById(R.id.deleteBtn);
        editBtn = findViewById(R.id.editBtn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailName.setText(bundle.getString("Name"));
            detailRegNo.setText(bundle.getString("RegNo"));
            detailAge.setText(String.valueOf(bundle.getInt("Age"))); // Convert to String
            detailGender.setText(bundle.getString("Gender"));
            detailContact.setText(String.valueOf(bundle.getInt("ContactNo"))); // Convert to String
            detailParent.setText(String.valueOf(bundle.getInt("ParentNo"))); // Convert to String
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(DetailActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainViewActivity.class));
                        finish();
                    }
                });
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                        .putExtra("Name", detailName.getText().toString())
                        .putExtra("RegNo", detailRegNo.getText().toString())
                        .putExtra("Age", Integer.parseInt(detailAge.getText().toString()))
                        .putExtra("Gender", detailGender.getText().toString())
                        .putExtra("ContactNo", Integer.parseInt(detailContact.getText().toString()))
                        .putExtra("ParentNo", Integer.parseInt(detailParent.getText().toString()))
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);

                startActivity(intent);
            }
        });
    }
}