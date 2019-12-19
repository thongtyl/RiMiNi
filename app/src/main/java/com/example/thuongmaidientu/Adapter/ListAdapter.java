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

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    ArrayList<Bag> arrayList;
    Context context;

    public ListAdapter(ArrayList<Bag> arrayList, Context context) {
        this.arrayList = arrayList;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(arrayList.get(position).getTen());
        holder.price.setText(arrayList.get(position).getGia() + " USD");
        holder.content.setText(arrayList.get(position).getMoTa());
        Picasso.with(context).load(arrayList.get(position).getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("bag",arrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView content;
        ImageView image;
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.text_bag_name);
            content=itemView.findViewById(R.id.text_bag_mota);
            price=itemView.findViewById(R.id.text_bag_price);
            image=itemView.findViewById(R.id.image_bag);
        }
    }

    public void filterList(ArrayList<Bag> filterlist)
    {
        arrayList=filterlist;
        notifyDataSetChanged();
    }
}
