package com.wukong.ttravel.service.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.home.model.DestCity;
import com.wukong.ttravel.service.home.model.TailorLine;

import java.util.ArrayList;

/**
 * Created by wukong on 3/16/16.
 */
public class CityListAdapter extends BaseAdapter {

    private ArrayList<DestCity> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CityListAdapter (Context context,ArrayList<DestCity> comments) {
        mContext = context;
        mData = comments;
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
            convertView = mLayoutInflater.inflate(R.layout.item_city_list, parent, false);
            holder.cityName = (TextView)convertView.findViewById(R.id.city_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        DestCity destCity = mData.get(position);
        holder.cityName.setText(destCity.getDestCityName());

        return convertView;
    }

    class ViewHolder {
        TextView  cityName;
    }
}
