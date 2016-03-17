package com.wukong.ttravel.service.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.okhttp.Call;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.my.model.TTBlackItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wukong on 3/15/16.
 */
public class BlackListAdapter extends BaseAdapter implements View.OnClickListener{

    private List<TTBlackItem> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private CallBack mCallBack;

    public interface CallBack {
        public void onClickDeleteButton(View v);
    }


    public BlackListAdapter(Context context, ArrayList<TTBlackItem> data,CallBack callBack) {
        mCallBack = callBack;
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
            convertView = mLayoutInflater.inflate(R.layout.activity_black_list_item, parent, false);
            holder.userAvatar = (SimpleDraweeView)convertView.findViewById(R.id.image_avatar);
            holder.userNick = (TextView)convertView.findViewById(R.id.user_nick);
            holder.deleteButton = (Button)convertView.findViewById(R.id.delete_button);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        TTBlackItem item = mData.get(position);
        holder.userAvatar.setImageURI(ImgUtil.getCDNUrlWithPathStr(item.getUserAvatar()));
        holder.userNick.setText(item.getUserNick());
        holder.deleteButton.setOnClickListener(this);
        holder.deleteButton.setTag(position);

        return convertView;
    }

    class ViewHolder {
        SimpleDraweeView userAvatar;
        TextView userNick;
        Button deleteButton;
    }

    @Override
    public void onClick(View v) {
        if (mCallBack != null) {
            mCallBack.onClickDeleteButton(v);
        }
    }
}
