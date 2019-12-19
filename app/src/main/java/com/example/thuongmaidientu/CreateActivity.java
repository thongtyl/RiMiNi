package com.example.thuongmaidientu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thuongmaidientu.object.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateActivity extends AppCompatActivity {
    EditText username, editpassword, name;
    Button btn1, btn2;
    FirebaseAuth mFirebase;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        username = findViewById(R.id.reg_email2);
        editpassword = findViewById(R.id.reg_pass);
        name = findViewById(R.id.reg_email);

        btn1 = findViewById(R.id.reg_btn);
        btn2 = findViewById(R.id.reg_login_btn);
        mFirebase = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CreateActivity.this, LoginActivity.class));

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(CreateActivity.this);
                pd.setMessage("Vui lòng chờ...");
                pd.show();
                String str_username = username.getText().toString();
                String str_fullname = name.getText().toString();
                String str_editpassword = editpassword.getText().toString();


                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_editpassword) || TextUtils.isEmpty(str_fullname)) {
                    Toast.makeText(CreateActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    register(str_username, str_fullname, str_editpassword);
                }
            }
        });

    }
    private void register(final String username, final String fullname, final String editpassưord){

        mFirebase.createUserWithEmailAndPassword(username,editpassưord)
                .addOnCompleteListener(CreateActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebase.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Profile").child(userid);
                            User user = new User(userid,username,fullname,"Chưa có","Chưa có","Chưa có","Chưa có");
                            reference.setValue(user);

                            pd.dismiss();
                            Toast.makeText(CreateActivity.this, "Bạn đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } else {
                            pd.dismiss();
                            Toast.makeText(CreateActivity.this, "Không thể đăng ký với email trên", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

