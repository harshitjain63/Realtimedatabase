package com.example.realtimedatabase;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context; // we have created context

    List<Model> items; // Item is the class which we created earlier consisting list of items

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener; // New long click listener

    // Interface for the long click listener
    public interface OnItemLongClickListener {
        void onItemLongClick(Model model);
    }


    // Interface for the item click listener
    public interface OnItemClickListener {
        void onItemClick(Model model);
    }

    // Setter for the long click listener
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        itemLongClickListener = listener;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }


    public MyAdapter(Context context, List<Model> items) {
        this.context = context;
        this.items = items;
    }

    public void removeItem(Model model) {
        int position = items.indexOf(model);
        if (position != -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // now we will return the view holder by passing context and layout
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // setting items to the view
        holder.nameView.setText(items.get(position).getEmployeeName());
        holder.addressView.setText(items.get(position).getEmployeeAddress());
        holder.contactView.setText(items.get(position).getEmployeeContactNumber());

        Picasso.get().load(items.get(position).getImageUri()).fit().into(holder.imageView);
        // Set a click listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(items.get(holder.getAbsoluteAdapterPosition()));
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemLongClickListener != null) {
                    itemLongClickListener.onItemLongClick(items.get(holder.getAbsoluteAdapterPosition()));
                    return true; // Return true to indicate that the long click is consumed
                }
                return false; // Return false to indicate that the long click is not consumed
            }
        });


    }

    @Override
    public int getItemCount() {
        // here we have return the size of total items
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView,addressView,contactView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView=itemView.findViewById(R.id.RealName);
            imageView=itemView.findViewById(R.id.AdminImg);
            addressView =itemView.findViewById(R.id.RealAdress);
            contactView=itemView.findViewById(R.id.RealContact);
        }
    }
}

