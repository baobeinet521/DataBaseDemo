package com.zd.databasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zd.databasedemo.activity.ContentProviderActivity;
import com.zd.databasedemo.activity.GreenDaoActivity;

public class MainActivity extends AppCompatActivity {

    private Button mGreenDaoBtn;
    private Button mContentProviderBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGreenDaoBtn = findViewById(R.id.greendao_btn);
        mContentProviderBtn  =findViewById(R.id.content_provider_btn);
        mGreenDaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GreenDaoActivity.class);
                startActivity(intent);
            }
        });

        mContentProviderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ContentProviderActivity.class);
                startActivity(intent);
            }
        });
    }


}
