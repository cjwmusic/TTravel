package com.wukong.ttravel.service.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.order.model.Order;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by wukong on 2/24/16.
 */
public class OrderListAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<Order> mData;
    private LayoutInflater mInflater;

    public OrderListAdapter(Context context,ArrayList<Order> orders) {
        mContext = context;
        mData = orders;
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
            convertView = mInflater.inflate(R.layout.order_item,parent,false);
            holder = new ViewHolder();
            holder.avatar = (SimpleDraweeView)convertView.findViewById(R.id.image_avatar);
            holder.nickName = (TextView)convertView.findViewById(R.id.nick_name);
            holder.serviceName = (TextView)convertView.findViewById(R.id.service_title);
            holder.createTime = (TextView)convertView.findViewById(R.id.order_time);
            holder.status = (TextView)convertView.findViewById(R.id.order_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Order order = mData.get(position);
        holder.avatar.setImageURI(ImgUtil.getCDNUrlWithPathStr(order.getTravelerPhoto()));
        holder.nickName.setText(order.getTravelerNickName());
        holder.serviceName.setText(order.getProductName());
        holder.createTime.setText(order.getOrderCreate());
        holder.status.setText(order.getOrderStatus());

        return convertView;
    }

    class ViewHolder {
        SimpleDraweeView avatar;
        TextView nickName;
        TextView serviceName;
        TextView createTime;
        TextView status;
    }
}
