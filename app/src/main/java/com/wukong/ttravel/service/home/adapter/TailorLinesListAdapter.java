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
import com.wukong.ttravel.service.home.model.TailorLine;

import java.util.ArrayList;

/**
 * Created by wukong on 3/15/16.
 */
public class TailorLinesListAdapter extends BaseAdapter {

    private ArrayList<TailorLine> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TailorLinesListAdapter(Context context,ArrayList<TailorLine> comments) {
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
            convertView = mLayoutInflater.inflate(R.layout.tailor_line_item, parent, false);
            holder.lineImage = (SimpleDraweeView)convertView.findViewById(R.id.line_image);
            holder.lineName = (TextView)convertView.findViewById(R.id.line_name);
            holder.lineNumber = (TextView)convertView.findViewById(R.id.line_number);
            holder.linePrice = (TextView)convertView.findViewById(R.id.line_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        TailorLine line = mData.get(position);
        holder.lineImage.setImageURI(ImgUtil.getCDNUrlWithPathStr(line.getProdPicture()));
        holder.linePrice.setText("￥"+line.getProdPrice() + "");
        holder.lineNumber.setText(line.getProdLineNumber());
        holder.lineName.setText(line.getProdName());
        //TODO stars

        return convertView;
    }

    class ViewHolder {

        SimpleDraweeView lineImage;
        TextView lineName;
        TextView linePrice;
        TextView lineNumber;
    }
}
