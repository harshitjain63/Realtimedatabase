package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Retrieve extends AppCompatActivity {

    TextView name,number,address;
    Button data;
    EditText uniquekey;
    DatabaseReference reference;
    String username;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        name=findViewById(R.id.Name);
        number=findViewById(R.id.Number);
        address=findViewById(R.id.Address);
        data=findViewById(R.id.Data);
        uniquekey=findViewById(R.id.Uniquekey);
        imageView=findViewById(R.id.Image);




        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 username = uniquekey.getText().toString();
                readData();
            }
        });


    }

    private void readData() {
        reference= FirebaseDatabase.getInstance().getReference("EmployeeInfo"); // here Employeeinfo is the root node in realtime database
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()){

                    DataSnapshot dataSnapshot = task.getResult();
                    String eAddress = String.valueOf(dataSnapshot.child("employeeAddress").getValue());
                    String eName = String.valueOf(dataSnapshot.child("employeeName").getValue());
                    String eNumber = String.valueOf(dataSnapshot.child("employeeContactNumber").getValue());
                    String imageUriString = String.valueOf(dataSnapshot.child("imageUri").getValue());

                    name.setText(eName);
                    number.setText(eNumber);
                    address.setText(eAddress);
                    Picasso.get().load(imageUriString).into(imageView);

                }else {
                    Toast.makeText(Retrieve.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}