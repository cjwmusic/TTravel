package com.wukong.ttravel.service.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.message.model.TTContact;

import java.util.ArrayList;

/**
 * Created by wukong on 3/10/16.
 */
public class ContactListAdapter extends BaseAdapter {


    private ArrayList<TTContact> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public ContactListAdapter(Context context, ArrayList<TTContact> data) {
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
            convertView = mLayoutInflater.inflate(R.layout.comment_item, parent, false);
            holder.avatar = (SimpleDraweeView)convertView.findViewById(R.id.image_avatar);
            holder.nickName = (TextView)convertView.findViewById(R.id.nick_name);
            holder.commentTime = (TextView)convertView.findViewById(R.id.comment_time);
            holder.messgeContent = (TextView)convertView.findViewById(R.id.msg_content);
            holder.unReadCount = (TextView)convertView.findViewById(R.id.unreadHighlight);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        TTContact contact = mData.get(position);
        holder.avatar.setImageURI(ImgUtil.getCDNUrlWithPathStr(contact.getLinkManPhoto()));
        holder.nickName.setText(contact.getLinkManNickName());
        if (contact.getLatestMessage() != null) {
            holder.messgeContent.setText(contact.getLatestMessage());
        }
        holder.commentTime.setText(contact.getLinkModify());

        return convertView;
    }


    class ViewHolder {

        SimpleDraweeView avatar;
        TextView nickName;
        TextView commentTime;
        TextView messgeContent;
        TextView unReadCount;
    }

}
