package com.glen.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.glen.news.R;

public class LaunchImage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_image);

//        final Intent intent = new Intent(this , ListNews.class);
        final Intent intent = new Intent(this , NewsListActivity.class);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                LaunchImage.this.finish();
            }
        } , 3000);

    }
}
