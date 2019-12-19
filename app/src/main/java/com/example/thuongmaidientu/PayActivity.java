package com.example.thuongmaidientu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.thuongmaidientu.object.BagCart;
import com.example.thuongmaidientu.object.Bill;
import com.example.thuongmaidientu.object.Feedback;
import com.example.thuongmaidientu.object.OrderDetail;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

public class PayActivity extends AppCompatActivity {
    //sql id người dùng
    LoginHelper loginHelper;
    String IdUser;
    //Địa chỉ, số tài khoản
    EditText DiaChiNhan;
    // đặt, hủy
    Button DatHang, Huy;
    //ngày đặt, ngày nhận
    TextView NgayDat, NgayNhan;
    DatePickerDialog.OnDateSetListener dateSetListener;
    //phương thức thanah toán
    RadioButton ThanhToanKhiNhan, ThanhToanQuanNganHang;
    //firebase cart, hóa đơn, đánh giá, đơn hàng
    DatabaseReference fbCart, fbHoaDon, fbDanhGia, fbOrder;
    //id paypal
    String idPaypal="" , thanhtien="";
    //danh sách mua
    ArrayList<BagCart> list;
    // mã giảm
    int MaGiam;
    int tt=0;
    // id paypal AQ9gbcfhW8lFEvjG2_g_ATgCqmSmWtpHrQJubsLgPUyxeKeApvR1KSlsC87TeyfUyQ5YNezL3RhBzYjM

    //code
    public static final int PAYPAL_REQUEST_CODE = 1111;
    //thêm
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId("AQ9gbcfhW8lFEvjG2_g_ATgCqmSmWtpHrQJubsLgPUyxeKeApvR1KSlsC87TeyfUyQ5YNezL3RhBzYjM");

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AnhXa();
        NgayDat();
        ChonNgay();

        loginHelper = new LoginHelper(this, "User.sqlite", null, 1);
        Cursor data = loginHelper.GetData("SELECT * FROM User");
        while (data.moveToNext()) {
            IdUser = data.getString(1);
        }

        // chajy server
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        // lấy danh sách mua
        list = new ArrayList<>();
        DanhSachMua();

        DatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                if (getIntent() != null) {
                    MaGiam = Integer.parseInt(intent.getStringExtra("magiam"));
                    if (DiaChiNhan.getText().toString().length() != 0) {
                        tt= TinhToan(list);
                        if (ThanhToanKhiNhan.isChecked())
                        {
                            fbHoaDon = FirebaseDatabase.getInstance().getReference();
                            String keyPayment =fbHoaDon.child("Users").child(IdUser).child("Payment").push().getKey();
                            Bill bill = new Bill(keyPayment,"Thanh Toán Khi Nhận Hàng","Null",String.valueOf(tt),"Chưa Thanh Toán");
                            fbHoaDon.child("Users").child(IdUser).child("Payment").child(keyPayment).setValue(bill);

                            for (int i = 0; i < list.size(); i++) {
                                BagCart bagCart = list.get(i);
                                String id2 = IDdanhGia(bagCart);
                                ChiTietDonHang(bagCart, keyPayment, id2);
                            }
                            XoaDanhSachMua();
                        }
                        else
                        {
                            if (ThanhToanQuanNganHang.isChecked())
                            {
                                Paypal(tt);
                            }
                        }

                    }
                    else
                    {
                        String s = "Vui lòng điền địa chỉ nhận hàng";
                        ThongBao(s);
                    }
                }
            }
        });

        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void AnhXa() {
        DatHang = findViewById(R.id.btnthanhtoandonhang);
        Huy = findViewById(R.id.btnhuydonhang);

        ThanhToanKhiNhan = findViewById(R.id.thanhtoankhinhan);
        ThanhToanQuanNganHang = findViewById(R.id.thanhtoanquanganhang);

        DiaChiNhan = findViewById(R.id.edtdiachinhan);

        NgayNhan = findViewById(R.id.txtngaynhan);
        NgayDat = findViewById(R.id.txtngaydat);
    }

    public void ChonNgay() {
        NgayNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PayActivity.this, android.R.style.Theme_DeviceDefault_Dialog, dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                NgayNhan.setText(date);
            }
        };
    }

    public void NgayDat() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        String date = day + "/" + month + "/" + year;
        NgayDat.setText(date);
        day = day + 7;
        String date2 = day + "/" + month + "/" + year;
        NgayNhan.setText(date2);
    }

    public void DanhSachMua() {
        fbCart = FirebaseDatabase.getInstance().getReference();
        fbCart.child("Users").child(IdUser).child("Cart").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BagCart bagCart = dataSnapshot.getValue(BagCart.class);
                list.add(bagCart);
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

    public void XoaDanhSachMua() {
        fbCart = FirebaseDatabase.getInstance().getReference();
        fbCart.child("Users").child(IdUser).child("Cart").removeValue();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông Báo");
        alert.setMessage("Đặt hàng thành công, đơn hàng của bạn đang chờ được xác nhận");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PayActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alert.show();
    }

    // thêm đánh giá
    public String IDdanhGia(BagCart bagCart) {
        fbDanhGia = FirebaseDatabase.getInstance().getReference();
        String IdDG = fbDanhGia.child("Users").child(IdUser).child("Feedback").push().getKey();
        Feedback feedback = new Feedback(IdDG,IdUser,bagCart.getIdBag(), "Chưa đánh giá", 0);
        fbDanhGia.child("Users").child(IdUser).child("Feedback").child(IdDG).setValue(feedback);
        return IdDG;
    }

    //thêm phương thức thanh toán hóa đơn
    public int TinhToan(ArrayList<BagCart> list) {

        int tinhtien=0;
        for (int i = 0; i < list.size(); i++) {
            BagCart bagCart = list.get(i);
            tinhtien = bagCart.getSoLuong() * Integer.parseInt(bagCart.getGia());
        }
        tinhtien = tinhtien - ((tinhtien * MaGiam) / 100);

        return  tinhtien;
    }

    // thêm order
    public void ChiTietDonHang(BagCart bagCart,String IdThanhtoan,String IdDanahGia)
    {
        fbOrder = FirebaseDatabase.getInstance().getReference();
        String idorder= fbOrder.child("Users").child(IdUser).child("OrderDetail").push().getKey();
        OrderDetail orderDetail = new OrderDetail(idorder,bagCart.getIdBag(), IdUser, "NhanVien", IdThanhtoan, IdDanahGia, bagCart.getTen(), bagCart.getGia(),bagCart.getHinhAnh(), String.valueOf(bagCart.getSoLuong()), String.valueOf(MaGiam), String.valueOf(tt), DiaChiNhan.getText().toString(), NgayDat.getText().toString(), NgayNhan.getText().toString(), "Đang chờ");
        fbOrder.child("Users").child(IdUser).child("OrderDetail").child(idorder).setValue(orderDetail);
    }

    public void ThongBao(String s)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(PayActivity.this);
        alert.setTitle("Thông Báo");
        alert.setMessage(s);
        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    //thanh taosn paypal
    public void Paypal(int tinhtien)
    {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(tinhtien),"USD","Thanh Toán",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    //dữ liệu trả về

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        String details = confirm.toJSONObject().toString(4);
                        JSONObject jsonObject = new JSONObject(details);

                        JSONObject response = jsonObject.getJSONObject("response");
                        String keypay = response.getString("id");
                        fbHoaDon = FirebaseDatabase.getInstance().getReference();
                        Bill bill = new Bill(keypay,"Thanh Toán PayPal",keypay,String.valueOf(tt),"Đã Thanh Toán");
                        fbHoaDon.child("Users").child(IdUser).child("Payment").child(keypay).setValue(bill);

                        for (int i = 0; i < list.size(); i++) {
                            BagCart bagCart = list.get(i);
                            String id2 = IDdanhGia(bagCart);
                            ChiTietDonHang(bagCart, keypay, id2);
                        }
                        XoaDanhSachMua();

                    } catch (JSONException e) {
                        Log.e("paymentExample", "Đã xảy ra lỗi: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "Đã thoát.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }
}
// nguồn paypal https://www.youtube.com/watch?v=k5lPy_50f0Y&t=15s EDMT Dev