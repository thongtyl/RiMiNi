package com.example.thuongmaidientu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuongmaidientu.R;
import com.example.thuongmaidientu.UserMnActivity;
import com.example.thuongmaidientu.object.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserManagementAdapter extends RecyclerView.Adapter<UserManagementAdapter.ViewHolder> {

    ArrayList<User> list;
    Context context;

    public UserManagementAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_user,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.ten.setText(list.get(position).getTenUser());
        holder.id.setText(list.get(position).getIdUser());
        Picasso.with(context).load("chuaco").into(holder.avatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserMnActivity.class);
                intent.putExtra("1412",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ten, id;
        ImageView avatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten=itemView.findViewById(R.id.txt_um_ten);
            id=itemView.findViewById(R.id.txt_um_id);
            avatar=itemView.findViewById(R.id.imgavatar);
        }
    }
}
