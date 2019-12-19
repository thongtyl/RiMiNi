package com.example.thuongmaidientu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.thuongmaidientu.Adapter.ListOrderAdapter;
import com.example.thuongmaidientu.Adapter.ListOrderUserMnAdapter;
import com.example.thuongmaidientu.object.OrderDetail;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListOderUserMnActivity extends AppCompatActivity {
    String IdUser = "";
    DatabaseReference fbOrder;
    ArrayList<OrderDetail> arrayList;
    ListOrderUserMnAdapter listOderUserMn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_oder_user_mn);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        if(intent!=null)
        {
            IdUser = intent.getStringExtra("1");
        }

        arrayList = new ArrayList<>();
        listOderUserMn = new ListOrderUserMnAdapter(arrayList,getApplicationContext());
        //Gọi recylerview
        RecyclerView recyclerView = findViewById(R.id.recyclerview_list_order_user_mn);
        //Chỉnh recylerview
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //Chuyền listAdapter cho recyclerview
        recyclerView.setAdapter(listOderUserMn);

        fbOrder = FirebaseDatabase.getInstance().getReference();
        fbOrder.child("Users").child(IdUser).child("OrderDetail").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                OrderDetail orderDetail = dataSnapshot.getValue(OrderDetail.class);
                    arrayList.add(orderDetail);
                    listOderUserMn.notifyDataSetChanged();
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
}
