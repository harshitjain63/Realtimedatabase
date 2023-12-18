package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Attendance_View extends AppCompatActivity {

    CalendarView calendarView;
    EditText name;
    String stringDateSelected,username;
    Button button;
    TextView attendance;
    DatabaseReference reference;
    DatabaseReference databasereference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_view);
        name=findViewById(R.id.tv1);
        attendance=findViewById(R.id.tv);
        button = findViewById(R.id.add);
        calendarView=findViewById(R.id.cv);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                stringDateSelected = Integer.toString(i2) + Integer.toString(i1 + 1) + Integer.toString(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = name.getText().toString();
                readData();
            }
        });
    }

    private void readData() {

        reference= FirebaseDatabase.getInstance().getReference("Attendance"); // here Attendance is the root node in realtime database
        reference.child(stringDateSelected+"|"+username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()){

                    DataSnapshot dataSnapshot = task.getResult();
                    String eAttendance = String.valueOf(dataSnapshot.child("employeeattendance").getValue());
                    attendance.setText(eAttendance);

                }else {
                    Toast.makeText(Attendance_View.this, "Failed!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}