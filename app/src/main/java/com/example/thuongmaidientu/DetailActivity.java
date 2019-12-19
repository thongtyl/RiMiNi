package com.example.thuongmaidientu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuongmaidientu.Adapter.FeedbackAdapter;
import com.example.thuongmaidientu.object.Bag;
import com.example.thuongmaidientu.object.BagCart;
import com.example.thuongmaidientu.object.Feedback;
import com.example.thuongmaidientu.ui.cart.CartFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    Button btn1;
    ImageButton btn2;
    TextView ten, gia, thuongHieu, kichThuoc, masp, moTa;
    ImageView hinhAnh;
    TextView search;
    Bag bag;
    DatabaseReference myRef;
    DatabaseReference fbFeedback;
    // sql
    LoginHelper loginHelper;
    // id người dùng login
    String IdUser;
    //list feedback
    ArrayList<Feedback> list;
    // adapter feedback
    FeedbackAdapter feedbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

            //ánh xạ
            ten =findViewById(R.id.txt_del_ten);
            gia =findViewById(R.id.txt_del_gia);
            thuongHieu =findViewById(R.id.txt_del_thuonghieu);
            kichThuoc =findViewById(R.id.txt_del_kichthuoc);
            masp =findViewById(R.id.txt_del_masp);
            moTa =findViewById(R.id.txt_del_mota);
            hinhAnh=findViewById(R.id.image_del_product);
            // lấy id người dừng
            GetIDUser();
            list = new ArrayList<>();
            //lấy dữ liệu đổ ra textview
            Intent intent = getIntent();
            if (getIntent()!=null) {
                bag = (Bag) intent.getSerializableExtra("bag");
                ten.setText(bag.getTen());
                gia.setText(bag.getGia());
                kichThuoc.setText(bag.getKichThuoc());
                moTa.setText(bag.getMoTa());
                masp.setText(bag.getIdBag());
                thuongHieu.setText(bag.getiDNhaCungCap());
                Picasso.with(this).load(bag.getImage()).into(hinhAnh);

                //lấy đánh giá
                list = new ArrayList<>();
                LayDanhGia();
                feedbackAdapter = new FeedbackAdapter(list, getApplicationContext());
                //Gọi recylerview
                RecyclerView recyclerView = findViewById(R.id.recyclerview_feedback);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
                //Chuyền listAdapter cho recyclerview
                recyclerView.setAdapter(feedbackAdapter);


            }

            //sự kiện click search mở list
            search=findViewById(R.id.text_search);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailActivity.this, ListActivity.class);
                    startActivity(intent);
                }
            });

            //sụ kiện click button mua
            btn1 = findViewById(R.id.button_mua);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRef = FirebaseDatabase.getInstance().getReference();
                    String idBagCart = myRef.child("Users").child(IdUser).child("Cart").push().getKey();
                    BagCart bagCart = new BagCart(idBagCart,bag.getIdBag(),bag.getTen(),bag.getGia(),bag.getImage(),1);
                    myRef.child("Users").child(IdUser).child("Cart").child(idBagCart).setValue(bagCart);
                    Toast.makeText(getApplication(), "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

            // sự kiện click cart
            btn2 = findViewById(R.id.imageButton_cart);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn2.setVisibility(View.GONE);
                    FragmentManager fmm = getSupportFragmentManager();
                    FragmentTransaction fmt = fmm.beginTransaction();
                    CartFragment cartFragment = new CartFragment();
                    fmt.add(R.id.detailactivity,cartFragment);
                    fmt.commit();
                }
            });
    }

    public void GetIDUser()
    {
        loginHelper = new LoginHelper(DetailActivity.this, "User.sqlite", null, 1);
        Cursor data = loginHelper.GetData("SELECT * FROM User");
        while (data.moveToNext()) {
            IdUser = data.getString(1);
        }
    }

    //lấy đánh giá của người dùng
    public void LayDanhGia()
    {
        fbFeedback = FirebaseDatabase.getInstance().getReference("Feedback");
        fbFeedback.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Feedback feedback = dataSnapshot.getValue(Feedback.class);
                if (feedback.getIdBag().equals(bag.getIdBag()))
                {
                    list.add(feedback);
                }
                feedbackAdapter.notifyDataSetChanged();
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
