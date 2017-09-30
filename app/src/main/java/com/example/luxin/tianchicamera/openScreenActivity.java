package com.example.luxin.tianchicamera;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

public class openScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openscreen_page);

        //展示开屏图片1.5秒，跳转到主页面
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);

                Intent intent = new Intent(openScreenActivity.this, MainActivity.class);

                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                openScreenActivity.this.finish();

            }
        }).start();

    }
}
