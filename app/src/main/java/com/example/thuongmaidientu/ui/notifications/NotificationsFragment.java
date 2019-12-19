package com.example.thuongmaidientu.ui.notifications;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuongmaidientu.DetailNotificationActivity;
import com.example.thuongmaidientu.LoginHelper;
import com.example.thuongmaidientu.object.Notifications;
import com.example.thuongmaidientu.Adapter.NotificationsAdapter;
import com.example.thuongmaidientu.OnItemClickListener;
import com.example.thuongmaidientu.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
//Content provider
public class NotificationsFragment extends Fragment {
    private static final int REQUEST_INSERT = 1000;
    ArrayList<Notifications> notificationsArrayList;
    NotificationsAdapter notificationsAdapter;
    DatabaseReference fbNoti;
    DatabaseReference fbNoti2;
    // sql
    LoginHelper loginHelper;
    // id người dùng login
    String IdUser;

    private NotificationsViewModel mViewModel;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);

        GetIDUser();
        notificationsArrayList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationsAdapter= new NotificationsAdapter(notificationsArrayList,getActivity().getApplicationContext());
        recyclerView.setAdapter(notificationsAdapter);
        Toast.makeText(getContext(),String.valueOf(notificationsArrayList.size()),Toast.LENGTH_LONG);

        fbNoti = FirebaseDatabase.getInstance().getReference("Notification");
        fbNoti.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Notifications notifications = dataSnapshot.getValue(Notifications.class);
                notificationsArrayList.add(notifications);
                notificationsAdapter.notifyDataSetChanged();
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

        fbNoti2 = FirebaseDatabase.getInstance().getReference("Users").child(IdUser).child("Notification");
        fbNoti2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Notifications notifications = dataSnapshot.getValue(Notifications.class);
                notificationsArrayList.add(notifications);
                notificationsAdapter.notifyDataSetChanged();
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

        notificationsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), DetailNotificationActivity.class);
                intent.putExtra("notification",notificationsArrayList.get(position));
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
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