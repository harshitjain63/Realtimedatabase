package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hbb20.CountryCodePicker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    
    NavigationView navigationView;
    EditText name, number, address;
    Button button;
    FloatingActionButton sms;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EmployeeInfo employeeInfo;
    CountryCodePicker codePicker;
    ProgressBar progressBar;
    ImageView imageView;
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView=findViewById(R.id.nav_view);
        sms=findViewById(R.id.SMS);
        name = findViewById(R.id.idEdtEmployeeName);
        number = findViewById(R.id.idEdtEmployeePhoneNumber);
        address = findViewById(R.id.idEdtEmployeeAddress);
        button = findViewById(R.id.idBtnSendData);
        progressBar = findViewById(R.id.Progressbar);
        imageView = findViewById(R.id.imgView);
        codePicker=findViewById(R.id.country_code);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("EmployeeInfo");
        employeeInfo = new EmployeeInfo();
        Dialog dialog = new Dialog(MainActivity.this);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SMS_sending.class);
             startActivity(intent);
            }
        });

        final DrawerLayout drawerLayout = findViewById(R.id.my_drawer_layout);
        findViewById(R.id.image_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);// to make linear layout act as a drawer
            }
        });

       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               if (item.getItemId()==R.id.nav_update){
                   Intent intent = new Intent(MainActivity.this, Update.class);
                   startActivity(intent);
               } else if (item.getItemId()==R.id.nav_Retrieve) {
                   Intent intent = new Intent(MainActivity.this, Retrieve.class);
                   startActivity(intent);
               } else if (item.getItemId()==R.id.nav_card) {
                   Intent intent = new Intent(MainActivity.this, AllData.class);
                   startActivity(intent);
               } else if (item.getItemId()==R.id.nav_leave) {
                   Intent intent = new Intent(MainActivity.this, LeaveCheck.class);
                   startActivity(intent);
               } else if (item.getItemId()==R.id.nav_logout) {
                   dialog.setContentView(R.layout.layout);
                   Button yes = dialog.findViewById(R.id.yes);
                   Button no = dialog.findViewById(R.id.no);
                   yes.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent intent = new Intent(MainActivity.this, Signup.class);
                           startActivity(intent);
                           dialog.dismiss();
                           finish();
                       }
                   });
                   no.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Toast.makeText(MainActivity.this, "Canceled!", Toast.LENGTH_SHORT).show();
                           dialog.dismiss();
                       }
                   });
                   dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                   dialog.setCancelable(false);
                   dialog.show();

               } else if (item.getItemId()==R.id.nav_attendance) {
                   Intent intent = new Intent(MainActivity.this, Attendance.class);
                   startActivity(intent);

               }

               return false;
           }
       });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show the visibility of progress bar to show loading
                progressBar.setVisibility(View.VISIBLE);
                String eName = name.getText().toString();
                String eNumber = number.getText().toString();
                String eAddress = address.getText().toString();
                // getting the country code
                String country_code=codePicker.getSelectedCountryCode();
                // Get the selected image's URI as a string
                String imageUriString = filePath.toString();
                if (!eName.isEmpty() && !eNumber.isEmpty() && !eAddress.isEmpty() && !imageUriString.isEmpty()) {
                    employeeInfo.setEmployeeName(eName);
                    employeeInfo.setEmployeeAddress(eAddress);
                    employeeInfo.setEmployeeContactNumber(country_code + eNumber);
                    employeeInfo.setImageUri(imageUriString); // Set the image URI
                    databaseReference.child(eName).setValue(employeeInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            name.setText("");
                            address.setText("");
                            number.setText("");
                            // imageView.setImageURI(Uri.parse(""));
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(MainActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }



            }

        });

    }

    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }




}