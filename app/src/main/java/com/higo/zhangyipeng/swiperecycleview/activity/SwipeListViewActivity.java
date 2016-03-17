package com.higo.zhangyipeng.swiperecycleview.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.zhangyipeng.swipelibrary.BaseSwipeListViewListener;
import com.example.zhangyipeng.swipelibrary.SwipeListView;
import com.example.zhangyipeng.swipelibrary.SwipeListView2;
import com.higo.zhangyipeng.swiperecycleview.R;
import com.higo.zhangyipeng.swiperecycleview.adapter.DataAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SwipeListViewActivity extends AppCompatActivity {

    @Bind(R.id.swipeListView)
    SwipeListView2 mSwipeListView;


    protected static final String TAG = "Activity";

    private DataAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        initDatas();


        mAdapter = new DataAdapter(this, mDatas , mSwipeListView);
        mSwipeListView.setAdapter(mAdapter);

        mSwipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onChoiceChanged(int position, boolean selected) {
                Log.d(TAG, "onChoiceChanged:" + position + ", " + selected);
            }

            @Override
            public void onChoiceEnded() {
                Log.d(TAG, "onChoiceEnded");
            }

            @Override
            public void onChoiceStarted() {
                Log.d(TAG, "onChoiceStarted");
            }

            @Override
            public void onClickBackView(int position) {
                Log.d(TAG, "onClickBackView:" + position);
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d(TAG, "onClickFrontView:" + position);
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
                Log.d(TAG, "onClosed:" + position + "," + fromRight);
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                Log.d(TAG, "onDismiss");


            }

            @Override
            public void onFirstListItem() {
                Log.d(TAG, "onFirstListItem");
            }

            @Override
            public void onLastListItem() {
                Log.d(TAG, "onLastListItem");
            }

            @Override
            public void onListChanged() {
                Log.d(TAG, "onListChanged");

                mSwipeListView.closeOpenedItems();

            }

            @Override
            public void onMove(int position, float x) {
                Log.d(TAG, "onMove:" + position + "," + x);
            }

            @Override
            public void onOpened(int position, boolean toRight) {
                Log.d(TAG, "onOpened:" + position + "," + toRight);
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d(TAG, "onStartClose:" + position + "," + right);
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d(TAG, "onStartOpen:" + position + "," + action + ","
                        + right);
            }
        });



        int width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        Toast.makeText(this, "width:" + width, Toast.LENGTH_SHORT).show();
        float density = getResources().getDisplayMetrics().density;

        mSwipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_BOTH);
        mSwipeListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
        mSwipeListView.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
        mSwipeListView.setOffsetLeft(width-dip2px(this,60));
        mSwipeListView.setOffsetRight(width);
        mSwipeListView.setAnimationTime(200);
        mSwipeListView.setSwipeOpenOnLongPress(true);


    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private void initDatas()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i <= 'Z'; i++)
            mDatas.add((char) i + "");
    }
}
