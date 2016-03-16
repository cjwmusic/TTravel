package com.wukong.ttravel.service.trade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.wukong.ttravel.R;
import com.wukong.ttravel.service.home.model.TailorLine;

import java.util.ArrayList;

/**
 * Created by wukong on 3/16/16.
 */
public class PreBookAdapter extends BaseAdapter {

    private ArrayList<TailorLine> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    private int currentSelectedIndex = -1;

    public PreBookAdapter(Context context,ArrayList<TailorLine> comments) {
        mContext = context;
        mData = comments;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public TailorLine getItem(int position) {
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
            convertView = mLayoutInflater.inflate(R.layout.prebook_tailor_item, parent, false);
            holder.lineName = (TextView)convertView.findViewById(R.id.line_name);
            holder.lineNumber = (TextView)convertView.findViewById(R.id.line_number);
            holder.chooseButton = (Button)convertView.findViewById(R.id.choose_button);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        TailorLine line = mData.get(position);
        holder.lineNumber.setText(line.getProdLineNumber());
        holder.lineName.setText(line.getProdName());
        if (position == currentSelectedIndex) {
            holder.chooseButton.setBackgroundResource(R.drawable.selected);
        } else {
            holder.chooseButton.setBackgroundResource(R.drawable.unselected);
        }

        return convertView;
    }

    class ViewHolder {
        TextView lineName;
        TextView lineNumber;
        Button chooseButton;

    }

    public int getCurrentSelectedIndex() {
        return currentSelectedIndex;
    }

    public void setCurrentSelectedIndex(int currentSelectedIndex) {
        this.currentSelectedIndex = currentSelectedIndex;
    }
}
