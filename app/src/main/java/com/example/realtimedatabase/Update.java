package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class Update extends AppCompatActivity {

    TextView name,number,address;
    Button update;
    CountryCodePicker codePicker;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name=findViewById(R.id.Name1);
        number=findViewById(R.id.Number1);
        address=findViewById(R.id.Address1);
        update=findViewById(R.id.Update);
        codePicker=findViewById(R.id.country_code);
        String country_code=codePicker.getSelectedCountryCode();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ename = name.getText().toString();
                String employeeContactNumber = number.getText().toString();
                employeeContactNumber = country_code+employeeContactNumber;
                String employeeAddress = address.getText().toString();

                updatedata(Ename,employeeContactNumber,employeeAddress);

            }
        });
    }

    private void updatedata(String ename, String enumber, String eaddress) {

        HashMap<String, Object> User = new HashMap<String, Object>();
        User.put("employeeAddress",eaddress);
        User.put("employeeContactNumber",enumber);

        databaseReference = FirebaseDatabase.getInstance().getReference("EmployeeInfo");
        databaseReference.child(ename).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()) {
                    name.setText("");
                    number.setText("");
                    address.setText("");
                    Toast.makeText(Update.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Update.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}