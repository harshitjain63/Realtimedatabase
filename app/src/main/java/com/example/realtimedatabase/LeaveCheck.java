package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LeaveCheck extends AppCompatActivity {
    RecyclerView recyclerView;
    List<LeaveInfo> items;
    DatabaseReference reference =  FirebaseDatabase.getInstance().getReference("Leaving");
    MyleaveAdapter add;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_check);
        items = new ArrayList<LeaveInfo>();
        recyclerView=findViewById(R.id.recyclerView);
        add = new MyleaveAdapter(this,items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(add);
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        getData();

        if (ContextCompat.checkSelfPermission(LeaveCheck.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            // if permission is not granted then we are requesting for the permissions on below line.
            ActivityCompat.requestPermissions(LeaveCheck.this, new String[]{Manifest.permission.SEND_SMS}, 100);
        }
        add.setOnItemClickListener(new MyleaveAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LeaveInfo model) {
                // Handle item click here
                // For example, you can open a new activity or show a toast
                String msg = "Your Leave Have Been Granted!!";
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(model.getPhone(), null,msg , null, null);
                    Toast.makeText(getApplicationContext(), "Leave Accepted", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Some Technical Error Occurred", Toast.LENGTH_LONG).show();
                }

                String DelName = model.getName();
                if (!DelName.isEmpty()){
                    deletedata(DelName);
                    add.removeItem(model);
                }
            }

            private void deletedata(String delName) {
                reference = FirebaseDatabase.getInstance().getReference("Leaving");
                reference.child(delName).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LeaveCheck.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LeaveCheck.this, "Failed deletion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }


        });


        add.setOnItemLongClickListener(new MyleaveAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(LeaveInfo model) {
                String msg = "Your Leave Request Have Been Rejected!!";
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(model.getPhone(), null,msg , null, null);
                    Toast.makeText(getApplicationContext(), "Leave Rejected", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Some Technical Error Occurred", Toast.LENGTH_LONG).show();
                }
                // Handle long click here
                String DelName = model.getName();
                if (!DelName.isEmpty()){
                    deletedata(DelName);
                    add.removeItem(model);
                }
            }

            private void deletedata(String delName) {
                reference = FirebaseDatabase.getInstance().getReference("Leaving");
                reference.child(delName).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LeaveCheck.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LeaveCheck.this, "Failed deletion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Collections.shuffle(items, new Random(System.currentTimeMillis()));
//               recyclerView.setAdapter(add);
////                shuffle();
//                add.notifyDataSetChanged();
//                mSwipeRefreshLayout.setRefreshing(false);

                int size = items.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        items.remove(0);
                    }
                    add.notifyItemRangeRemoved(0,size);
                }
                getData();
                add.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);


            }
        });
    }
    public void getData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    LeaveInfo model= dataSnapshot.getValue(LeaveInfo.class);
                    items.add(model);
                }
                add.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}