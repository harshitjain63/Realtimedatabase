package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Attendance extends AppCompatActivity {

    EditText name,attendance;
    CalendarView calendarView;
    String stringDateSelected;
    FirebaseDatabase firebaseDatabase;
    Button button,view;
    DatabaseReference databasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        name=findViewById(R.id.tv1);
        attendance=findViewById(R.id.tv);
        button = findViewById(R.id.add);
        view=findViewById(R.id.View);
        calendarView=findViewById(R.id.cv);
        Attendance_Model attend = new Attendance_Model();
        databasereference = FirebaseDatabase.getInstance().getReference("Attendance");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Attendance.this,Attendance_View.class);
                startActivity(intent);
            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
               stringDateSelected = Integer.toString(i2) + Integer.toString(i1 + 1) + Integer.toString(i);//***
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eDate = stringDateSelected;
                String eName = name.getText().toString();
                String eAttandance = attendance.getText().toString();
                attend.setDate(eDate);
                attend.setEmployeeName(eName);
                attend.setEmployeeattendance(eAttandance);

                databasereference.child(eDate+"|"+eName).setValue(attend).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        name.setText("");
                        attendance.setText("");
                        Toast.makeText(Attendance.this, "Success", Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });

    }

}