package com.higo.zhangyipeng.swiperecycleview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.higo.zhangyipeng.swiperecycleview.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void btSwipelistview(View view) {
        startActivity(new Intent(this, SwipeListViewActivity.class));
    }


    public void btSwiperecycleview(View view) {
        startActivity(new Intent(this, SwipeRecycleViewActivity.class));

    }
}
