package com.example.realtimedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyleaveAdapter extends RecyclerView.Adapter<MyleaveAdapter.MyViewHolder>{

    Context context; // we have created context

    List<LeaveInfo> items; // Item is the class which we created earlier consisting list of items

    private MyleaveAdapter.OnItemClickListener itemClickListener;
    private MyleaveAdapter.OnItemLongClickListener itemLongClickListener; // New long click listener



    // Interface for the long click listener
    public interface OnItemLongClickListener {
        void onItemLongClick(LeaveInfo model);
    }


    // Interface for the item click listener
    public interface OnItemClickListener {
        void onItemClick(LeaveInfo model);
    }

    // Setter for the long click listener
    public void setOnItemLongClickListener(MyleaveAdapter.OnItemLongClickListener listener) {
        itemLongClickListener = listener;
    }


    public void setOnItemClickListener(MyleaveAdapter.OnItemClickListener listener) {
        itemClickListener = listener;
    }


    public MyleaveAdapter(Context context, List<LeaveInfo> items) {
        this.context = context;
        this.items = items;
    }
    public void removeItem(LeaveInfo model) {
        int position = items.indexOf(model);
        if (position != -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyleaveAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.leaveitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // setting items to the view
        holder.nameView.setText(items.get(position).getName());
        holder.addressView.setText(items.get(position).getStartingDate());
        holder.contactView.setText(items.get(position).getPhone());
        holder.endView.setText(items.get(position).getLastDate());
        holder.reasonView.setText(items.get(position).getReason());

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
        return items.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView,addressView,contactView,endView,reasonView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView=itemView.findViewById(R.id.RealName);
            imageView=itemView.findViewById(R.id.AdminImg);
            addressView =itemView.findViewById(R.id.RealAdress);
            contactView=itemView.findViewById(R.id.RealContact);
            endView=itemView.findViewById(R.id.RealEnd);
            reasonView=itemView.findViewById(R.id.Realreason);
        }
    }
}

