package com.example.thuongmaidientu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.thuongmaidientu.Adapter.ListAdapter;
import com.example.thuongmaidientu.object.Bag;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Bag> arrayList;
    ArrayList<Bag> arrayListCopy;
    ListAdapter listAdapter;
    EditText editText;

    //Khai bao firebase
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // firebase
        arrayList = new ArrayList<>();
        arrayListCopy = new ArrayList<>();
        addChild();
        //getData();
        listAdapter = new ListAdapter(arrayList, getApplicationContext());
        //Gọi recylerview
        RecyclerView recyclerView = findViewById(R.id.recyclerview_list);
        //Chỉnh recylerview
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //Chuyền listAdapter cho recyclerview
        recyclerView.setAdapter(listAdapter);

        //sụ kiện tìm kiếm
        editText = findViewById(R.id.editText_search2);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                ArrayList<Bag> tmp = new ArrayList<>();
                for (Bag st : arrayList) {
                    if (st.getTen().toLowerCase().contains(s.toString().toLowerCase())) {
                        tmp.add(st);
                    }
                }
                listAdapter.filterList(tmp);
            }
        });
    }

    // cập nhật Adapter khi có bag được thêm
    public void addChild() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Bag");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bag bag = dataSnapshot.getValue(Bag.class);
                arrayList.add(bag);
                listAdapter.notifyDataSetChanged();
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