package com.wukong.ttravel.service.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wukong.ttravel.R;
import com.wukong.ttravel.service.my.model.TTHelpItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wukong on 3/15/16.
 */
public class MeHelpAdapter extends BaseAdapter {

    private List<TTHelpItem> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public MeHelpAdapter(Context context, ArrayList<TTHelpItem> data) {
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
            convertView = mLayoutInflater.inflate(R.layout.activity_me_help_item, parent, false);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        TTHelpItem item = mData.get(position);
        holder.title.setText(item.getHelpTitle());

        return convertView;
    }

    class ViewHolder {
        TextView title;
    }


}
