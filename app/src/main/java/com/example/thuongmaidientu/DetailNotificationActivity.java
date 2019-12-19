package com.example.thuongmaidientu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.thuongmaidientu.object.Notifications;

public class DetailNotificationActivity extends AppCompatActivity {
    TextView ten, noidung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notification);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ten = findViewById(R.id.text_not_name);
        noidung = findViewById(R.id.text_not_content);

        Intent intent = getIntent();
        if (intent!=null)
        {
            Notifications notifications = (Notifications) intent.getSerializableExtra("notification");
            ten.setText(notifications.getName());
            noidung.setText(notifications.getContent());
        }
    }
}
