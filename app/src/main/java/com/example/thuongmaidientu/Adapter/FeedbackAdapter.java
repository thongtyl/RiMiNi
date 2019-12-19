package com.example.thuongmaidientu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuongmaidientu.R;
import com.example.thuongmaidientu.object.Feedback;
import com.example.thuongmaidientu.object.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder>{

    ArrayList<Feedback> arrayList;
    Context context;
    DatabaseReference fbUser;
    String name;

    public FeedbackAdapter(ArrayList<Feedback> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_feedback,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.diem.setText(String.valueOf(arrayList.get(position).getDiemSo()));
        holder.noidung.setText(" " + arrayList.get(position).getNoiDung());

        fbUser = FirebaseDatabase.getInstance().getReference();
        fbUser.child("Users").child(arrayList.get(position).getIdUser()).child("Profile").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                name = user.getTenUser();
                if (name.length()!=0)
                {
                    holder.ten.setText(" "+name);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ten, diem, noidung;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten=itemView.findViewById(R.id.txt_del_tennguoidanhgia);
            diem=itemView.findViewById(R.id.txt_del_sodiemdanhgia);
            noidung=itemView.findViewById(R.id.txt_del_noidungdanhgia);
        }
    }
}
