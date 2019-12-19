package com.example.thuongmaidientu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProductManagementActivity extends AppCompatActivity {
    TextView quaylaiadmin;
    CardView themsanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        quaylaiadmin = findViewById(R.id.quaylai);
        themsanpham = findViewById(R.id.themsp);

        quaylaiadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductManagementActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        themsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ProductManagementActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });
    }
}
