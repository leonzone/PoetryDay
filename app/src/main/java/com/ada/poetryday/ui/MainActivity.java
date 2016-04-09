package com.ada.poetryday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ada.poetryday.R;

public class MainActivity extends AppCompatActivity {
    View mLayoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定视图
        initView();
        //注册事件
        initEvent();
    }

    private void initView() {
        mLayoutMain = findViewById(R.id.layout_main);

    }

    private void initEvent() {
        mLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllDaysActivity.class));
            }
        });
    }
}
