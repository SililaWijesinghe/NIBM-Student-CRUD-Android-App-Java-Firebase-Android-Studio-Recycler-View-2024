package com.example.myapplication9777;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateActivity extends AppCompatActivity {

    ImageView updateImage;
    Button updateButton;
    EditText updateRegNo, updateName, updateAge, updateGender, updateContact, updateParent;
    String imageUrl, regNo, name,  gender,  key, oldImageUrl;
    Integer age,contactNumber, parentNumber;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateImage = findViewById(R.id.updateImage);
        updateButton = findViewById(R.id.updateButton);
        updateRegNo = findViewById(R.id.updateStdRegNo);
        updateName = findViewById(R.id.updateStdName);
        updateAge = findViewById(R.id.updateStdAge);
        updateGender = findViewById(R.id.updateStdGender);
        updateContact = findViewById(R.id.updateStdMobNo);
        updateParent = findViewById(R.id.updateStdParentNo);

        // Move this part to before using activityResultLauncher
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == Activity.RESULT_OK) {
                            Intent data = o.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);

                            // Use the updated method to get StorageReference
                            storageReference = FirebaseStorage.getInstance().getReference().child("Images").child(uri.getLastPathSegment());
                        } else {
                            Toast.makeText(UpdateActivity.this, "No Image Selected!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Other assignments remain the same
            updateName.setText(bundle.getString("Name"));
            updateRegNo.setText(bundle.getString("RegNo"));
            updateAge.setText(String.valueOf(bundle.getInt("Age"))); // Correct method for getting int
            updateGender.setText(bundle.getString("Gender"));
            updateContact.setText(String.valueOf(bundle.getInt("ContactNo"))); // Correct method for getting int
            updateParent.setText(String.valueOf(bundle.getInt("ParentNo"))); // Correct method for getting int
            key = bundle.getString("Key");
            oldImageUrl = bundle.getString("Image");

            // Load the image using Glide
            Glide.with(UpdateActivity.this)
                    .load(oldImageUrl)
                    .into(updateImage);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(key);

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(UpdateActivity.this, MainViewActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveData() {
        if (uri != null) {
            // If URI is not null, upload the new image
            storageReference = FirebaseStorage.getInstance().getReference().child("Images").child(uri.getLastPathSegment());

            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    updateData();
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
        } else {
            // If URI is null, proceed with the existing image
            imageUrl = oldImageUrl;
            updateData();
        }
    }



    public void updateData() {
        name = updateName.getText().toString().trim();
        regNo = updateRegNo.getText().toString().trim();
        age = Integer.valueOf(updateAge.getText().toString());
        gender = updateGender.getText().toString();
        contactNumber = Integer.valueOf(updateContact.getText().toString());
        parentNumber = Integer.valueOf(updateParent.getText().toString());

        DataClass dataClass = new DataClass(regNo, name, age, gender, contactNumber, parentNumber, imageUrl);

        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Update successful, now delete the old image
                    int lastSlashIndex = oldImageUrl.lastIndexOf('/');
                    String path = oldImageUrl.substring(lastSlashIndex + 1);

                    // Get the reference using the path
                    StorageReference reference = FirebaseStorage.getInstance().getReference().child(path);
                    reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Image deleted successfully
                            Toast.makeText(UpdateActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the failure to delete image, if necessary
                            Toast.makeText(UpdateActivity.this, "Updated! (Image deletion failed)", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}














