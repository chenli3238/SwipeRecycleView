package com.higo.zhangyipeng.swiperecycleview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhangyipeng.swipelibrary.SwipeListView2;
import com.higo.zhangyipeng.swiperecycleview.R;

import java.util.List;

/**
 * Created by zhangyipeng on 15/11/19.
 */
public class DataAdapter extends BaseAdapter
{

    private List<String> mDatas;
    private LayoutInflater mInflater;
    private SwipeListView2 mSwipeListView ;

    public DataAdapter(Context context, List<String> datas , SwipeListView2 swipeListView)
    {
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
        mSwipeListView = swipeListView;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = mInflater.inflate(R.layout.item, null);

        TextView tv = (TextView) convertView.findViewById(R.id.id_text);
        Button del = (Button) convertView.findViewById(R.id.id_remove);
        tv.setText(mDatas.get(position));
        del.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDatas.remove(position);
                notifyDataSetChanged();
                /**
                 * 关闭SwipeListView
                 * 不关闭的话，刚删除位置的item存在问题
                 * 在监听事件中onListChange中关闭，会出现问题
                 */
                mSwipeListView.closeOpenedItems();
            }
        });

        return convertView;
    }

}