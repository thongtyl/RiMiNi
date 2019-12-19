package com.example.thuongmaidientu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuongmaidientu.object.Bill;
import com.example.thuongmaidientu.object.Feedback;
import com.example.thuongmaidientu.object.OrderDetail;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class OrderActivity extends AppCompatActivity {
    TextView masp, ten,gia,soluong, giam,thanhtien,ngaydat,ngaynhan,tinhtrang,thanhtoan,danhgia;
    Button huydon, btndanhgia,huydanhgia,guidanhgia;
    EditText noidungdanhgia;
    ImageView hinhanh;
    DatabaseReference fbPayment;
    DatabaseReference fbFeedback;
    DatabaseReference fbOrder;
    // sql
    LoginHelper loginHelper;
    // id người dùng login
    String IdUser;
    String keyPayment;
    String keyFeedback;
    String tinhtrangdonhang;
    String keyOrder;
    String IdBag;
    String IdOrder;
    RatingBar ratingBar;
    float diemso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AnhXa();
        GetIDUser();

        // in ra text
        Intent intent = getIntent();
        if (intent!=null)
        {
            OrderDetail orderDetail = (OrderDetail) intent.getSerializableExtra("14");
            masp.setText(orderDetail.getiDBag());
            ten.setText(orderDetail.getTen());
            gia.setText(orderDetail.getGia());
            soluong.setText(orderDetail.getSoLuong());
            giam.setText(orderDetail.getMaGiam());
            thanhtien.setText(orderDetail.getThanhTien());
            tinhtrang.setText(orderDetail.getTrangThai());
            ngaynhan.setText(orderDetail.getNgayNhan());
            ngaydat.setText(orderDetail.getNgayDat());
            Picasso.with(this).load(orderDetail.getHinhAnh()).into(hinhanh);
            keyFeedback=orderDetail.getiDDanhGia();
            keyPayment=orderDetail.getiDThanhToan();
            IdBag = orderDetail.getiDBag();
            IdOrder = orderDetail.getiDOrder();
            tinhtrangdonhang=orderDetail.getTrangThai();
        }

        //lấy payment in ra text
        fbPayment = FirebaseDatabase.getInstance().getReference();
        fbPayment.child("Users").child(IdUser).child("Payment").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill.getKey().equals(keyPayment))
                {
                    thanhtoan.setText(bill.getTinhTrang());
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

        //lấy feedback in ra text
        fbFeedback = FirebaseDatabase.getInstance().getReference();
        fbFeedback.child("Users").child(IdUser).child("Feedback").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Feedback feedback = dataSnapshot.getValue(Feedback.class);
                if (feedback.getKey().equals(keyFeedback))
                {
                    danhgia.setText(feedback.getNoiDung());
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

        //hủy đơn hàng nếu trajgn thái đang chờ
        huydon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tinhtrangdonhang.equals("Đang chờ"))
                {
                   // hủy đơn hàng với key vừa lấy, thông báo
                    fbOrder = FirebaseDatabase.getInstance().getReference();
                    fbOrder.child("Users").child(IdUser).child("OrderDetail").child(IdOrder).child("trangThai").setValue("Hủy");
                    AlertDialog.Builder alert = new AlertDialog.Builder(OrderActivity.this);
                    alert.setTitle("Thông Báo");
                    alert.setMessage("Đơn hàng đã được hủy");
                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();
                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(OrderActivity.this);
                    alert.setTitle("Thông Báo");
                    alert.setMessage("Đơn hàng đã được xác nhận. Bạn không thể hủy đơn hàng");
                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();
                }
            }
        });

        btndanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFeedback();
            }
        });

    }

    public void AnhXa()
    {
        masp =findViewById(R.id.txt_order_masp);
        ten =findViewById(R.id.txt_order_name);
        gia =findViewById(R.id.txt_order_gia);
        soluong =findViewById(R.id.txt_order_soluong);
        giam =findViewById(R.id.txt_order_giam);
        ngaydat =findViewById(R.id.txt_order_ngaydat);
        ngaynhan =findViewById(R.id.txt_order_ngaynhan);
        tinhtrang =findViewById(R.id.txt_order_trangthai);
        danhgia =findViewById(R.id.txt_order_danhgia);
        thanhtien =findViewById(R.id.txt_order_thanhtien);
        thanhtoan =findViewById(R.id.txt_order_thanhtoan);
        hinhanh =findViewById(R.id.image_order_hinhanh);

        huydon =findViewById(R.id.btn_huydon);
        btndanhgia=findViewById(R.id.btn_danhgia);
    }

    public void GetIDUser()
    {
        loginHelper = new LoginHelper(this, "User.sqlite", null, 1);
        Cursor data = loginHelper.GetData("SELECT * FROM User");
        while (data.moveToNext()) {
            IdUser = data.getString(1);
        }
    }

    public void DialogFeedback()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feedback);
        dialog.setCanceledOnTouchOutside(false);

        guidanhgia= dialog.findViewById(R.id.btn_dialog_guidanhgia);
        huydanhgia = dialog.findViewById(R.id.btn_dialog_huy);
        noidungdanhgia=dialog.findViewById(R.id.edt_dialog_noidungfeedback);

        ratingBar = dialog.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
              diemso = rating;

            }
        });

        guidanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung ="";
                noidung = noidungdanhgia.getText().toString();
                if(!noidung.isEmpty())
                {
                    Feedback feedback = new Feedback(keyFeedback,IdUser,IdBag,noidung,(int)diemso);
                    fbFeedback = FirebaseDatabase.getInstance().getReference();
                    fbFeedback.child("Users").child(IdUser).child("Feedback").child(keyFeedback).setValue(feedback);

                    fbFeedback = FirebaseDatabase.getInstance().getReference();
                    fbFeedback.child("Feedback").child(keyFeedback).setValue(feedback);
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(OrderActivity.this,"Bạn chưa nhập nội dung đánh giá",Toast.LENGTH_LONG).show();
                }
            }
        });

        huydanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
