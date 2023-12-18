package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Manager extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText ed1,ed2,ed3,ed4,ed5;
    Button b;
    LeaveInfo leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        ed3 = findViewById(R.id.ed3);
        ed4 = findViewById(R.id.ed4);
        ed5 = findViewById(R.id.ed5);
        b = findViewById(R.id.b);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Leaving");
        leave=new LeaveInfo();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eName = ed1.getText().toString();
                String ePhone = ed2.getText().toString();
                String eStart = ed3.getText().toString();
                String eEnd =   ed4.getText().toString();
                String eReason = ed5.getText().toString();
                if (!eName.isEmpty() && !ePhone.isEmpty() && !eStart.isEmpty() && !eEnd.isEmpty() && !eReason.isEmpty()) {
                    leave.setName(eName);
                    leave.setPhone(ePhone);
                    leave.setStartingDate(eStart);
                    leave.setLastDate(eEnd);
                    leave.setReason(eReason);
                    databaseReference.child(eName).setValue(leave).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            ed1.setText("");
                            ed2.setText("");
                            ed3.setText("");
                            ed4.setText("");
                            ed5.setText("");
                            // imageView.setImageURI(Uri.parse(""));
                            Toast.makeText(Manager.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(Manager.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}