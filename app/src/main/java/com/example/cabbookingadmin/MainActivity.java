package com.example.cabbookingadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    private int PICK;

    private EditText editTextName;
    private EditText editTextArea;
    private EditText editTextRating, editTextPrice, editTextphoneNumber, editTextdes;
    private Spinner type;

    CheckBox exclusive;

    ImageView imageView;

    private Button add;

    private FirebaseFirestore db;
    private CollectionReference houseRef;

    StorageReference reference;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PICK = 1;

        editTextName = findViewById(R.id.edit_text_name);
        editTextArea = findViewById(R.id.edit_text_area);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextphoneNumber = findViewById(R.id.edit_text_phone);

        editTextRating = findViewById(R.id.edit_text_rating);

        editTextdes = findViewById(R.id.edit_text_des);
        exclusive = findViewById(R.id.exclusive);

        imageView = findViewById(R.id.imageView);

        add = (Button) findViewById(R.id.addBtn);
        type = findViewById(R.id.type);

        db = FirebaseFirestore.getInstance();
        houseRef = db.collection("Cab");

        reference = FirebaseStorage.getInstance().getReference("Cab");
        String[] arraySpinner = new String[]{"Car", "Auto rickshaw" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
    }

    public void chooseImage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK);
    }

    public void add(View view) {

        if (imageUri != null) {
            add.setEnabled(false);

            ContentResolver cr = getContentResolver();
            MimeTypeMap mine = MimeTypeMap.getSingleton();

            final StorageReference mref = reference.child(System.currentTimeMillis() + "." + mine.getExtensionFromMimeType(cr.getType(imageUri)));

            mref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();

                    mref.getDownloadUrl().addOnCompleteListener(
                            task -> {
                                String downloadUrl = task.getResult().toString();

                                String name = editTextName.getText().toString();
                                String area = editTextArea.getText().toString();
                                String price = editTextPrice.getText().toString();
                                String phoneNumber = editTextphoneNumber.getText().toString();
                                String rating = editTextRating.getText().toString();
                                String des = editTextdes.getText().toString();

                                String exclusiveS;
                                if (exclusive.isChecked()) {
                                    exclusiveS = "true";
                                } else {
                                    exclusiveS = "false";
                                }

                                cabItem note;
                                note = new cabItem(downloadUrl, name, area, price, rating, phoneNumber, des, exclusiveS, (String) type.getSelectedItem());

                                houseRef.add(note).addOnCompleteListener(
                                        task1 -> {
                                            Toast.makeText(MainActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                                            add.setEnabled(true);
                                        }).addOnFailureListener(e -> {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    add.setEnabled(true);
                                });
                            });
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(MainActivity.this, "Upload Fail", Toast.LENGTH_SHORT).show();
                add.setEnabled(true);
            });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();

            imageView.setImageURI(data.getData());
        }
    }
}