package com.example.thuongmaidientu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuongmaidientu.object.Bag;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddItemActivity extends AppCompatActivity {
    String moTa, Price, Pname, Size, Brand, saveCurrentDate,
            saveCurrentTime,productRandomKey, downloadImageUrl, CategoryName, Supplier, Type;
    String xten, xgia, xkichthuoc, xmota, xthuonghieu, xnhacc, xloai;

    TextView btnHuy, quaylai;
    ImageView bookImage;
    EditText ten, gia, kichthuoc, mota, thuonghieu, nhacc, loai;
    Button addbtn;
    Uri ImageUri;
    static final int GalleryPick = 1;
    StorageReference BagImagesRef;
    DatabaseReference BagsRef;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        quaylai = findViewById(R.id.quaylaiadditem);
        btnHuy = findViewById(R.id.btnHuy);
        addbtn = findViewById(R.id.btnAdd);
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


        BagsRef = FirebaseDatabase.getInstance().getReference().child("Bag");
        BagImagesRef = FirebaseStorage.getInstance().getReference().child("Book Images");

        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItemActivity.this, ProductManagementActivity.class);
                startActivity(intent);
            }
        });

        //Huy OnClick
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);

                builder.setMessage("Bạn có chắc muốn hủy?")
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

        //Add Book Image
        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Gallery
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GalleryPick);
            }
        });


        //Add Book Button

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBag();
            }
        });
    }

    private void AddBag() {
        //ten, gia, kichthuoc, mota, thuonghieu, nhacc, loai;
        xten= ten.getText().toString();
        xgia= gia.getText().toString();
        xkichthuoc= kichthuoc.getText().toString();
        xmota= mota.getText().toString();
        xthuonghieu= thuonghieu.getText().toString();
        xnhacc= nhacc.getText().toString();
        xloai= loai.getText().toString();

        //Required Book Image
        if (ImageUri == null) {
            Toast.makeText(AddItemActivity.this, "Chưa thêm ảnh Bag!", Toast.LENGTH_SHORT).show();
        }
        //Required Book Desc
        else if (TextUtils.isEmpty(xmota)) {
            Toast.makeText(AddItemActivity.this, "Vui lòng nhập Mô tả Bag!", Toast.LENGTH_SHORT).show();
        }
        //Required Book Price
        else if (TextUtils.isEmpty(xgia)) {
            Toast.makeText(AddItemActivity.this, "Vui lòng nhập Giá Bag!", Toast.LENGTH_SHORT).show();
        }
        //Required Book Name
        else if (TextUtils.isEmpty(xten)) {
            Toast.makeText(AddItemActivity.this, "Vui lòng nhập Tên Bag!", Toast.LENGTH_SHORT).show();
        } else {
            BagInformation();
        }
    }

    //Generate Book Information
    private void BagInformation() {
        loadingBar.setTitle("Thêm Bag mới");
        loadingBar.setMessage("Xin vui lòng chờ trong giây lát!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        //ID (date + time)
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = BagImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddItemActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }
    //Upload to Database
    private void SaveProductInfoToDatabase() {
       /* HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("idBag", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("moTa", moTa);
        productMap.put("image", downloadImageUrl);
        productMap.put("Loai", CategoryName);
        productMap.put("Gia", Price);
        productMap.put("ten", Pname);
        productMap.put("iDNhaCungCap", Supplier);
        productMap.put("iDLoai", Type);



        BagsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(AddItemActivity.this, ProductManagementActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AddItemActivity.this, "Thêm Bag mới thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddItemActivity.this, "Lỗi: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                */
       BagsRef = FirebaseDatabase.getInstance().getReference("Bag");
       String key = BagsRef.push().getKey();
        Bag bag = new Bag(key,xten,xgia,xmota,xkichthuoc,xnhacc,xloai,downloadImageUrl);
        BagsRef.child(key).setValue(bag);
        loadingBar.dismiss();
        Toast.makeText(AddItemActivity.this, "Thêm Bag mới thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            bookImage.setImageURI(ImageUri);
        }
    }
}







