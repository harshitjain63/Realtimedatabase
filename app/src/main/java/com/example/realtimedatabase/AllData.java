package com.example.realtimedatabase;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AllData extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Model> items;
    DatabaseReference reference =  FirebaseDatabase.getInstance().getReference("EmployeeInfo");;
    MyAdapter add;
     SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);
        items = new ArrayList<Model>();
        recyclerView=findViewById(R.id.recyclerView);
        add = new MyAdapter(this,items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(add);
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        getData();



//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    Model model= dataSnapshot.getValue(Model.class);
//                    items.add(model);
//                }
//                add.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        add.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Model model) {
                // Handle item click here
                // For example, you can open a new activity or show a toast
                Toast.makeText(AllData.this, "Clicked: " + model.getEmployeeName(), Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Model model) {
                // Handle long click here
                String DelName = model.getEmployeeName();
                if (!DelName.isEmpty()){
                     deletedata(DelName);
                    add.removeItem(model);
                }
            }

            private void deletedata(String delName) {
                reference = FirebaseDatabase.getInstance().getReference("EmployeeInfo");
                reference.child(delName).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()){
                         Toast.makeText(AllData.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                     }else {
                         Toast.makeText(AllData.this, "Failed deletion", Toast.LENGTH_SHORT).show();
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

                    Model model= dataSnapshot.getValue(Model.class);
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



