package com.wukong.ttravel.service.discover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.discover.model.DiscoverItem;

import java.util.ArrayList;

/**
 * Created by wukong on 2/21/16.
 */
public class DiscoverAdapter extends BaseAdapter {


    private ArrayList<DiscoverItem> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public DiscoverAdapter(Context context,ArrayList<DiscoverItem> discoverItems) {
        mContext = context;
        mData = discoverItems;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        ViewHolder viewHolder;
        DiscoverItem item = mData.get(position);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.discover_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.coverImage = (SimpleDraweeView)convertView.findViewById(R.id.cover_image);
            viewHolder.titleTextView = (TextView)convertView.findViewById(R.id.title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.coverImage.setImageURI(ImgUtil.getCDNUrlWithPathStr(item.getFounPicture()));
        viewHolder.titleTextView.setText(item.getFounTitle());

        return convertView;
    }


    class ViewHolder {
        SimpleDraweeView coverImage;
        TextView titleTextView;
    }
}
