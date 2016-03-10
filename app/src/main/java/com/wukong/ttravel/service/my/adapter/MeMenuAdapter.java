package com.wukong.ttravel.service.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wukong.ttravel.R;
import com.wukong.ttravel.service.my.model.TTMenuItem;

import java.util.ArrayList;

/**
 * Created by wukong on 3/11/16.
 */
public class MeMenuAdapter extends BaseAdapter {

    private ArrayList<TTMenuItem> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public MeMenuAdapter(Context context, ArrayList<TTMenuItem> data) {
        mContext = context;
        mData = data;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.fragment_me_item, parent, false);
            holder.icon = (ImageView)convertView.findViewById(R.id.icon);
            holder.title = (TextView)convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        TTMenuItem item = mData.get(position);
        holder.icon.setImageResource(item.getIconResId());
        holder.title.setText(item.getTitle());

        return convertView;
    }

    class ViewHolder {

        ImageView icon;
        TextView title;
    }
}
