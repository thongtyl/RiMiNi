package com.example.thuongmaidientu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminActivity extends AppCompatActivity {
    ImageView imgview1;
    private CardView qlsanpham;
    CardView qlnguoidung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        imgview1 = (ImageView)findViewById(R.id.imgview);
        qlsanpham = findViewById(R.id.qlsp);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        imgview1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent m1 = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(m1);
            }
        });

        qlsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ProductManagementActivity.class);
                startActivity(intent);
            }
        });

        qlnguoidung = findViewById(R.id.qlnguoidung);
        qlnguoidung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,UserManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}
