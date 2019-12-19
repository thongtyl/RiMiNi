package com.example.thuongmaidientu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class UpdateItemActivity extends AppCompatActivity {
    String Description, Price, Pname, Size, Brand, saveCurrentDate,
            saveCurrentTime,productRandomKey, downloadImageUrl, CategoryName, Supplier, Type;
    TextView btnHuy, quaylai;
    ImageView bookImage;
    EditText ten, gia, kichthuoc, mota, thuonghieu, nhacc, loai;
    Button updatebtn, deletebtn;
    Uri ImageUri;
    static final int GalleryPick = 1;
    StorageReference BagImagesRef;
    DatabaseReference BagsRef;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        setContentView(R.layout.activity_add_item);
        quaylai = findViewById(R.id.quaylaiadditem);
        btnHuy = findViewById(R.id.btnHuy);
        updatebtn = findViewById(R.id.btnUpdate);
        deletebtn = findViewById(R.id.btnDelete);
        bookImage = findViewById(R.id.select_image);
        ten = findViewById(R.id.tensp);
        mota = findViewById(R.id.motasp);
        gia = findViewById(R.id.giasp);
        kichthuoc = findViewById(R.id.kichthuocsp);
        thuonghieu = findViewById(R.id.thuonghieusp);
        nhacc = findViewById(R.id.nhacungcapsp);
        loai = findViewById(R.id.loaisp);

        loadingBar = new ProgressDialog(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateItemActivity.this, ProductManagementActivity.class);
                startActivity(intent);
            }
        });

        //Huy OnClick
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateItemActivity.this);

                builder.setMessage("Bạn có chắc muốn quay lại?")
                        .setCancelable(false)
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

}
