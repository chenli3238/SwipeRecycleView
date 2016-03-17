package com.higo.zhangyipeng.swiperecycleview.activity;

import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zhangyipeng.swipelibrary.BaseSwipeListViewListener;
import com.example.zhangyipeng.swipelibrary.SwipeRecycleView;
import com.higo.zhangyipeng.swiperecycleview.R;
import com.higo.zhangyipeng.swiperecycleview.RecyclerItemClickListener;
import com.higo.zhangyipeng.swiperecycleview.adapter.PackageAdapter;
import com.higo.zhangyipeng.swiperecycleview.bean.PackageItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SwipeRecycleViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.example_lv_list)
    SwipeRecycleView swipeRecycleView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private PackageAdapter adapter;
    private List<PackageItem> data;
    private ProgressDialog progressDialog;


    Handler handler = new Handler() {
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        ButterKnife.bind(this);

        data = new ArrayList<PackageItem>();

        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);


        adapter = new PackageAdapter(this, data);


        mLayoutManager = new LinearLayoutManager(this);
        swipeRecycleView.setLayoutManager(mLayoutManager);



        swipeRecycleView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    data.remove(position);
                }
                adapter.notifyDataSetChanged();
            }

        });

        swipeRecycleView.setAdapter(adapter);
        swipeRecycleView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        Toast.makeText(SwipeRecycleViewActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    }
                })
        );

        int width = getWindow().getWindowManager().getDefaultDisplay().getWidth();

        swipeRecycleView.setOffsetLeft(width - convertDpToPixel(300));

        swipeRecycleView.addOnScrollListener(listener);

        new ListAppTask().execute();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            int limit = 10;//小于10条不加载
            if (mLayoutManager.getItemCount() >= limit && mLayoutManager.findLastVisibleItemPosition() == mLayoutManager.getItemCount() - 1) { // 向下滑动，判断最后一个item是不是显示中
                onLoadMore();
            }

        }
    };


    public void onLoadMore() {
        Toast.makeText(this, "加载更多", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (swipeRefreshLayout.isRefreshing()) {

                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 2000);


    }



    public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }


    public class ListAppTask extends AsyncTask<Void, Void, List<PackageItem>> {

        protected List<PackageItem> doInBackground(Void... args) {
            PackageManager appInfo = getPackageManager();
            List<ApplicationInfo> listInfo = appInfo.getInstalledApplications(0);
            Collections.sort(listInfo, new ApplicationInfo.DisplayNameComparator(appInfo));

            List<PackageItem> data = new ArrayList<PackageItem>();

            for (int index = 0; index < listInfo.size(); index++) {
                try {
                    ApplicationInfo content = listInfo.get(index);
                    if ((content.flags != ApplicationInfo.FLAG_SYSTEM) && content.enabled) {
                        if (content.icon != 0) {
                            PackageItem item = new PackageItem();
                            item.setName(getPackageManager().getApplicationLabel(content).toString());
                            item.setPackageName(content.packageName);
                            item.setIcon(getPackageManager().getDrawable(content.packageName, content.icon, content));
                            data.add(item);
                        }
                    }
                } catch (Exception e) {

                }
            }

            return data;
        }

        protected void onPostExecute(List<PackageItem> result) {
            data.clear();
            data.addAll(result);
            adapter.notifyDataSetChanged();
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }

        }
    }

}
