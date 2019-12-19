package com.example.thuongmaidientu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.thuongmaidientu.object.Notifications;
import com.example.thuongmaidientu.object.OrderDetail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class OrderUserMnActivity extends AppCompatActivity {
    TextView masp, ten,gia,soluong, giam,thanhtien,ngaydat,ngaynhan,tinhtrang,thanhtoan,danhgia;
    Button btnLuu, btnThanhToan;
    ImageView hinhanh;
    RadioButton rbtnHuy, rbtnXacNhan,rbtnDangGiao,rbtnHoanThanh;
    DatabaseReference fbPayment;
    DatabaseReference fbOrder;
    DatabaseReference fbNoti;

    // id người dùng login
    String IdUser;
    String keyPayment;
    String keyFeedback;
    String tinhtrangdonhang;
    String keyOrder;
    String IdOrder;
    String ngaythongbaonhanhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_user_mn);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AnhXa();

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
            tinhtrangdonhang=orderDetail.getTrangThai();
            ngaythongbaonhanhang = orderDetail.getNgayNhan();

            if(tinhtrangdonhang.equals("Xác Nhận"))
            {
                rbtnXacNhan.setChecked(true);
            }
            if(tinhtrangdonhang.equals("Đang Giao"))
            {
                rbtnDangGiao.setChecked(true);
            }
            if(tinhtrangdonhang.equals("Hoàn Thành"))
            {
                rbtnHoanThanh.setChecked(true);
            }
            if(tinhtrangdonhang.equals("Hủy"))
            {
                rbtnHuy.setChecked(true);
            }

            IdOrder = orderDetail.getiDOrder();
            IdUser=orderDetail.getiDKhachHang();
        }

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trangthai="";
                if (rbtnXacNhan.isChecked())
                {
                    trangthai="Xác Nhận";
                }
                if (rbtnDangGiao.isChecked())
                {
                    trangthai=" giao bởi CTVC vào ngày " +ngaythongbaonhanhang+ " , vui lòng giữ liên lạc";
                }
                if (rbtnHoanThanh.isChecked())
                {
                    trangthai="Hoàn Thành";
                }
                if (rbtnHuy.isChecked())
                {
                    trangthai="Hủy";
                }
                fbOrder = FirebaseDatabase.getInstance().getReference();
                fbOrder.child("Users").child(IdUser).child("OrderDetail").child(IdOrder).child("trangThai").setValue(trangthai);

                Notifications noti = new Notifications("Thông báo đơn hàng","Đơn hàng của bạn đã được "+trangthai+"");
                fbNoti=FirebaseDatabase.getInstance().getReference();
                String key = fbNoti.child("Users").child(IdUser).child("Notification").push().getKey();
                fbNoti.child("Users").child(IdUser).child("Notification").child(key).setValue(noti);
                finish();
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbPayment = FirebaseDatabase.getInstance().getReference();
                fbPayment.child("Users").child(IdUser).child("Payment").child(keyPayment).child("tinhTrang").setValue("Đã Thanh Toán");
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
        btnLuu = findViewById(R.id.btn_user_um_luu);
        btnThanhToan = findViewById(R.id.btn_del_um_thanhtoan);

        rbtnXacNhan = findViewById(R.id.rdb_um_xacnhan);
        rbtnDangGiao = findViewById(R.id.rdb_um_danggiao);
        rbtnHoanThanh = findViewById(R.id.rdb_um_hoanthanh);
        rbtnHuy = findViewById(R.id.rdb_um_huy);
    }
}
