package com.example.thuongmaidientu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreeActivity extends AppCompatActivity {

    private static int time = 4000;
    // sql
    LoginHelper loginHelper;
    // id người dùng login
    String IdUser="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scree);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        GetIDUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (IdUser.isEmpty()) {
                    Intent intent = new Intent(SplashScreeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(SplashScreeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        },time);
    }

    public void GetIDUser()
    {
        loginHelper = new LoginHelper(SplashScreeActivity.this, "User.sqlite", null, 1);
        Cursor data = loginHelper.GetData("SELECT * FROM User");
        while (data.moveToNext()) {
            IdUser = data.getString(1);
        }
    }
}
