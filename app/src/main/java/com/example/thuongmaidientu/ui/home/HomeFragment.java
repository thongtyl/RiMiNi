package com.example.thuongmaidientu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuongmaidientu.object.Bag;
import com.example.thuongmaidientu.Adapter.BagAdapter;
import com.example.thuongmaidientu.DetailActivity;
import com.example.thuongmaidientu.ListActivity;
import com.example.thuongmaidientu.LoginHelper;
import com.example.thuongmaidientu.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    TextView textView;
    ArrayList<Bag> bagArrayList;
    ArrayList<Bag> bagArrayListCopy;
    private HomeViewModel mViewModel;
    ViewFlipper viewFlipper;

    //Khai bao firebase
    DatabaseReference database;
    DatabaseReference fbAdd;
    DatabaseReference fbQC;

    //khai báo adapder của bag
    BagAdapter bagAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //viewvipper
        viewFlipper = v.findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Banner();

        // chuyen sản phẩm từ hình ảnh qunagr cáo
        Button btnSau = v.findViewById(R.id.btn_qcsau);
        btnSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewFlipper.showNext();
            }
        });

        Button btnTruoc = v.findViewById(R.id.btn_qctruoc);
        btnTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 viewFlipper.showPrevious();
            }
        });

        //sự kiện tìm kiếm
        textView = v.findViewById(R.id.editText_search);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                startActivity(intent);
            }
        });

        bagArrayList = new ArrayList<>();
        bagArrayListCopy= new ArrayList<>();
        addChild();
        //khai báo, định dạng Adapter
        bagAdapter = new BagAdapter(bagArrayList,getActivity().getApplicationContext());
        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_bag);
        recyclerView.setAdapter(bagAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    // cập nhật Adapter khi có bag được thêm
    public void addChild()
    {
        database = FirebaseDatabase.getInstance().getReference("Bag");
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bag bag = dataSnapshot.getValue(Bag.class);
                bagArrayList.add(bag);
                bagAdapter.notifyDataSetChanged();
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

    public void Banner()
    {
        fbQC = FirebaseDatabase.getInstance().getReference("Banner");
        fbQC.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String qc = dataSnapshot.getValue(String.class);
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(getActivity()).load(qc).into(imageView);
                viewFlipper.addView(imageView);
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

    public void CreateBag()
    {
        fbAdd = FirebaseDatabase.getInstance().getReference();
        String key1 = fbAdd.child("Banner").push().getKey();
        fbAdd.child("Banner").child(key1).setValue("https://firebasestorage.googleapis.com/v0/b/thuongmaidientu-5f70a.appspot.com/o/QC%2FCr53rRSVYAA1ePZ.jpg?alt=media&token=d9fec5ae-3304-4627-8c05-6e71afe382b8");
        fbAdd = FirebaseDatabase.getInstance().getReference();
        String key2 = fbAdd.child("Banner").push().getKey();
        fbAdd.child("Banner").child(key2).setValue("https://firebasestorage.googleapis.com/v0/b/thuongmaidientu-5f70a.appspot.com/o/QC%2FCr9gZOcUMAAhiiN.jpg?alt=media&token=399e4841-ca8e-491d-9436-220ad083f7e8");
        fbAdd = FirebaseDatabase.getInstance().getReference();
        String key3 = fbAdd.child("Banner").push().getKey();
        fbAdd.child("Banner").child(key3).setValue("https://firebasestorage.googleapis.com/v0/b/thuongmaidientu-5f70a.appspot.com/o/QC%2Fnd.jpg?alt=media&token=ff15a0a7-7a03-441f-83a9-e69bce76a321");


        /*
        Bag bag = new Bag(key,"Túi đeo dọc","500000","Mô tả","70-70-70","Nhà cung cấp","Loại","https://firebasestorage.googleapis.com/v0/b/thuongmaidientu-5f70a.appspot.com/o/ic_004.jpg?alt=media&token=4872c972-ce8d-4e8d-b7b3-cd9ad30bf24d");
        fbAdd.child("Bag").child(key).setValue(bag);
        String key1 = fbAdd.child("Bag").push().getKey();
        Bag bag2 = new Bag(key,"Túi xách","600000","Mô tả","70-70-70","Nhà cung cấp","Loại","https://firebasestorage.googleapis.com/v0/b/thuongmaidientu-5f70a.appspot.com/o/ic_005.jpg?alt=media&token=2204f6e3-ccb2-4f06-92a4-153b6075fcc3");
        fbAdd.child("Bag").child(key1).setValue(bag2);
        String key2 = fbAdd.child("Bag").push().getKey();
        Bag bag3 = new Bag(key,"Túi đeo vai","700000","Mô tả","70-70-70","Nhà cung cấp","Loại","https://firebasestorage.googleapis.com/v0/b/thuongmaidientu-5f70a.appspot.com/o/ic_006.jpg?alt=media&token=e3827594-54dd-4102-98df-c8f7e016d039");
        fbAdd.child("Bag").child(key2).setValue(bag3);
        String key3 = fbAdd.child("Bag").push().getKey();
        Bag bag4 = new Bag(key,"Túi đeo xéo","800000","Mô tả","70-70-70","Nhà cung cấp","Loại","https://firebasestorage.googleapis.com/v0/b/thuongmaidientu-5f70a.appspot.com/o/ic_007.jpg?alt=media&token=c428e426-b3d4-4f18-bd3f-c298038425e6");
        fbAdd.child("Bag").child(key3).setValue(bag4);
        String key4 = fbAdd.child("Bag").push().getKey();
        Bag bag5 = new Bag(key,"Túi đeo chéo","900000","Mô tả","70-70-70","Nhà cung cấp","Loại","https://firebasestorage.googleapis.com/v0/b/thuongmaidientu-5f70a.appspot.com/o/ic_007.jpg?alt=media&token=c428e426-b3d4-4f18-bd3f-c298038425e6");
        fbAdd.child("Bag").child(key4).setValue(bag5);
        String key5 = fbAdd.child("Bag").push().getKey();
        Bag bag6 = new Bag(key,"Túi đeo ngang","400000","Mô tả","70-70-70","Nhà cung cấp","Loại","https://firebasestorage.googleapis.com/v0/b/thuongmaidientu-5f70a.appspot.com/o/ic_008.jpg?alt=media&token=8aee0bdf-968f-4542-b4b4-f53d22b0e042");
        fbAdd.child("Bag").child(key5).setValue(bag6);*/
    }
}