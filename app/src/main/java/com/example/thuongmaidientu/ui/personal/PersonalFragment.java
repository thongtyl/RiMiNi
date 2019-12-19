package com.example.thuongmaidientu.ui.personal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.thuongmaidientu.DetailActivity;
import com.example.thuongmaidientu.HistoryActivity;
import com.example.thuongmaidientu.ListOrderActivity;
import com.example.thuongmaidientu.LoginActivity;
import com.example.thuongmaidientu.LoginHelper;
import com.example.thuongmaidientu.OrderActivity;
import com.example.thuongmaidientu.PersonalDetailActivity;
import com.example.thuongmaidientu.R;
import com.example.thuongmaidientu.object.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalFragment extends Fragment {

    // sql
    LoginHelper loginHelper;
    // id người dùng login
    String IdUser;
    User user;

    DatabaseReference databaseReference;
    TextView ten, email;

    private PersonalViewModel mViewModel;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_personal, container, false);
        GetIDUser();
        ten = v.findViewById(R.id.text_per_name);
        email = v.findViewById(R.id.text_per_email);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(IdUser).child("Profile").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                user = dataSnapshot.getValue(User.class);
                ten.setText(user.getTenUser());
                email.setText(user.getEmail());
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

        Button button = (Button) v.findViewById(R.id.button_per_cart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListOrderActivity.class);
                intent.putExtra("IdUser",IdUser);
                startActivity(intent);
            }
        });
        Button button2 = (Button) v.findViewById(R.id.button_per_history);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                intent.putExtra("IdUser",IdUser);
                startActivity(intent);
            }
        });
        Button button3 = (Button) v.findViewById(R.id.button_per_det);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalDetailActivity.class);
                intent.putExtra("1",user);
                startActivity(intent);
            }
        });
        Button button4 = (Button) v.findViewById(R.id.button_Logout);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginHelper.QueryData("DELETE FROM User");
                Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent2);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PersonalViewModel.class);
        // TODO: Use the ViewModel
    }

    public void GetIDUser()
    {
        loginHelper = new LoginHelper(getActivity(), "User.sqlite", null, 1);
        Cursor data = loginHelper.GetData("SELECT * FROM User");
        while (data.moveToNext()) {
            IdUser = data.getString(1);
        }
    }
}