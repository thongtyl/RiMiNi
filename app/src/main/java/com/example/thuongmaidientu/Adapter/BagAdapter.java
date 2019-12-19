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

import com.example.thuongmaidientu.DetailActivity;
import com.example.thuongmaidientu.R;
import com.example.thuongmaidientu.object.Bag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.ViewHolder> {
    ArrayList<Bag>  list;
    Context context;

    public BagAdapter(ArrayList<Bag> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_bag,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder ,final int position) {
        holder.name.setText(list.get(position).getTen());
        holder.mota.setText(list.get(position).getMoTa());
        holder.price.setText(list.get(position).getGia()+" USD");
        Picasso.with(context).load(list.get(position).getImage()).into(holder.image);

        //sự kiện click vào bag
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bag", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        ImageView image;
        TextView mota;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.text_bag_name);
            price=itemView.findViewById(R.id.text_bag_price);
            image=itemView.findViewById(R.id.image_bag);
            mota=itemView.findViewById(R.id.text_bag_mota);
        }
    }
    // sự kiện tìm kiếm
    public void filterList(ArrayList<Bag> filterlist)
    {
        list=filterlist;
        notifyDataSetChanged();
    }
}
