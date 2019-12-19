package com.example.thuongmaidientu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    // khai báo sql
    LoginHelper dbHelper;

    EditText username, editpassword;
    Button btn1;
    TextView btn2;
    FirebaseAuth mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.editText);
        editpassword = findViewById(R.id.editText2);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        mFirebase = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // tạo sql bảng user
        dbHelper = new LoginHelper(this,"User.sqlite",null,1);
        dbHelper.QueryData("CREATE TABLE IF NOT EXISTS User(Id INTEGER PRIMARY KEY AUTOINCREMENT, IdUser VARCHAR(200))");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Vui lòng chờ...");
                pd.show();
                String email = username.getText().toString();
                String password = editpassword.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else if(email.equals("admin")&& password.equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                else {
                    mFirebase.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        // lấy id user
                                        String IdUser = mFirebase.getCurrentUser().getUid();
                                        // Thêm vào sql
                                        dbHelper.QueryData("INSERT INTO User VALUES(null,'"+IdUser+"')");
                                        Toast.makeText(LoginActivity.this, "Bạn đã đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else{
                                        pd.dismiss();
                                        Toast.makeText(LoginActivity.this, "Quá trình xác thực thất bại!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }
}
