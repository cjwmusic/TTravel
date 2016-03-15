package com.wukong.ttravel.service.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.custom.model.CustomServiceItem;

import java.util.ArrayList;

/**
 * Created by wukong on 3/15/16.
 */
public class CustomServiceListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CustomServiceItem> mData;
    private LayoutInflater mInflater;

    public CustomServiceListAdapter(Context context,ArrayList<CustomServiceItem> items) {
        mContext = context;
        mData = items;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            convertView = mInflater.inflate(R.layout.custom_service_item,parent,false);
            holder = new ViewHolder();
            holder.serviceName = (TextView)convertView.findViewById(R.id.service_name);
            holder.serviceStatus = (TextView)convertView.findViewById(R.id.service_status);
            holder.serviceImage = (SimpleDraweeView)convertView.findViewById(R.id.service_image);
            holder.priceLabel = (TextView)convertView.findViewById(R.id.price_label);
            holder.serviceUnit = (TextView)convertView.findViewById(R.id.service_unit);
            holder.serviceDesc = (TextView)convertView.findViewById(R.id.servie_desc);
            holder.updateTime = (TextView)convertView.findViewById(R.id.update_time);
            holder.rightButton1 = (Button)convertView.findViewById(R.id.right_button1);
            holder.rightButton2 = (Button)convertView.findViewById(R.id.right_button2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        CustomServiceItem item = mData.get(position);
        holder.serviceName.setText(item.getProdName());
        holder.serviceStatus.setText(item.getProdStatus());
        holder.serviceImage.setImageURI(ImgUtil.getCDNUrlWithPathStr(item.getProdPicture()));
        holder.priceLabel.setText(item.getProdPrice());
        holder.serviceUnit.setText(item.getUnitText());
        holder.serviceDesc.setText(item.getProdDescription());
        holder.updateTime.setText(item.getUpdateTime());
        if (item.isHideRightButton1()) {
            holder.rightButton1.setVisibility(View.GONE);
        } else {
            holder.rightButton1.setVisibility(View.VISIBLE);
            holder.rightButton1.setText(item.getRightButton1Text());
        }

        if (item.isHideRightButton2()) {
            holder.rightButton2.setVisibility(View.GONE);
        } else {
            holder.rightButton2.setVisibility(View.VISIBLE);
            holder.rightButton2.setText(item.getRightButton2Text());
        }

        return convertView;
    }

    class ViewHolder {
        TextView serviceName;
        TextView serviceStatus;
        SimpleDraweeView serviceImage;
        TextView priceLabel;
        TextView serviceUnit;
        TextView serviceDesc;
        TextView updateTime;
        Button rightButton1;
        Button rightButton2;
    }
}
