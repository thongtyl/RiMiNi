package com.example.thuongmaidientu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PersonalDetailActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference databaseRef;
    User user;
    String IdUser, gioitinh, email, diachi;
    EditText ten, sdt, ngaysinh;
    Button luuthaydoi;
    RadioButton nam,nu;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AnhXa();
        NgaySinh();
        Intent intent = getIntent();
        if(intent!=null)
        {
            user = (User) intent.getSerializableExtra("1");
            ten.setText(user.getTenUser());
            sdt.setText(user.getsDT());
            ngaysinh.setText(user.getNgaySinh());
            IdUser = user.getIdUser();
            email = user.getEmail();
            diachi = user.getDiaChi();
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
                if(ten.getText().toString().length()!=0||sdt.getText().toString().length()!=0||ngaysinh.getText().toString().length()!=0)
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
                    User user = new User(IdUser,email,ten.getText().toString(),sdt.getText().toString(),diachi,gioitinh,ngaysinh.getText().toString());
                    databaseReference.child("Users").child(IdUser).child("Profile").child(IdUser).setValue(user);
                    Toast.makeText(PersonalDetailActivity.this,"Đã cập nhật thông tin",Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    Toast.makeText(PersonalDetailActivity.this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void AnhXa()
    {
        ten = findViewById(R.id.txt_del_per_ten);
        sdt = findViewById(R.id.txt_del_per_sdt);
        ngaysinh = findViewById(R.id.txt_del_per_ngaysinh);
        luuthaydoi = findViewById(R.id.btn_del_per_luu);
        nam = findViewById(R.id.rdb_nam);
        nu = findViewById(R.id.rdb_nu);
    }

    public void NgaySinh() {
        ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PersonalDetailActivity.this, android.R.style.Theme_DeviceDefault_Dialog, dateSetListener, year, month, day);
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
