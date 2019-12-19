package com.example.thuongmaidientu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuongmaidientu.object.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UserMnActivity extends AppCompatActivity {
    EditText ten, sdt, diachi;
    TextView ngaysinh;
    Button luuthaydoi,listorder;
    RadioButton nam,nu;
    User user;
    String IdUser,gioitinh, email;
    DatabaseReference databaseReference;
    DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mn);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AnhXa();
        NgaySinh();

        final Intent intent = getIntent();
        if(intent!=null)
        {
            user = (User) intent.getSerializableExtra("1412");
            ten.setText(user.getTenUser());
            email= user.getEmail();
            sdt.setText(user.getsDT());
            ngaysinh.setText(user.getNgaySinh());
            diachi.setText(user.getDiaChi());
            IdUser = user.getIdUser();
            String gt = user.getGioiTinh();
            if (gt.equals("Nam"))
            {
                nam.setChecked(true);
            }
            else
            {
                nu.setChecked(true);
            }
        }

        luuthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ten.getText().toString().length()!=0||sdt.getText().toString().length()!=0||ngaysinh.getText().toString().length()!=0||diachi.getText().toString().length()!=0)
                {
                    if(nam.isChecked())
                    {
                        gioitinh = "Nam";
                    }
                    else
                    {
                        gioitinh = "Nữ";
                    }
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    User user = new User(IdUser,email,ten.getText().toString(),sdt.getText().toString(),diachi.getText().toString(),gioitinh,ngaysinh.getText().toString());
                    databaseReference.child("Users").child(IdUser).child("Profile").child(IdUser).setValue(user);
                    Toast.makeText(UserMnActivity.this,"Đã cập nhật thông tin người dùng",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(UserMnActivity.this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }
            }
        });

        listorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMnActivity.this,ListOderUserMnActivity.class);
                intent.putExtra("1",IdUser);
                startActivity(intent);
            }
        });

    }

    public void AnhXa()
    {
        ten = findViewById(R.id.txt_del_um_ten);
        sdt = findViewById(R.id.txt_del_um_sdt);
        ngaysinh = findViewById(R.id.txt_del_um_ngaysinh);
        luuthaydoi = findViewById(R.id.btn_del_um_luu);
        nam = findViewById(R.id.um_rdb_nam);
        nu = findViewById(R.id.um_rdb_nu);
        diachi= findViewById(R.id.txt_del_um_diachi);
        listorder = findViewById(R.id.btn_del_um_listorder);
    }

    public void NgaySinh() {
        ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(UserMnActivity.this, android.R.style.Theme_DeviceDefault_Dialog, dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                ngaysinh.setText(date);
            }
        };
    }
}
