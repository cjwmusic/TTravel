package com.wukong.ttravel.service.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.home.model.Comment;

import java.util.ArrayList;

/**
 * Created by wukong on 3/14/16.
 */
public class MeCommentsAdapter extends BaseAdapter {


    private ArrayList<Comment> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MeCommentsAdapter(Context context,ArrayList<Comment> comments) {
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
            convertView = mLayoutInflater.inflate(R.layout.comment_item, parent, false);
            holder.avatar = (SimpleDraweeView)convertView.findViewById(R.id.image_avatar);
            holder.nickName = (TextView)convertView.findViewById(R.id.nick_name);
            holder.commentTime = (TextView)convertView.findViewById(R.id.comment_time);
            holder.commentContent = (TextView)convertView.findViewById(R.id.comment_content);
            holder.serviceName = (TextView)convertView.findViewById(R.id.service_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Comment comment = mData.get(position);
        holder.avatar.setImageURI(ImgUtil.getCDNUrlWithPathStr(comment.getAvatar()));
        holder.nickName.setText(comment.getNickName());
        holder.commentContent.setText(comment.getCommentContent());
        holder.commentTime.setText(comment.getCommentTime());
        holder.serviceName.setText(comment.getServiceName());
        //TODO stars

        return convertView;
    }

    class ViewHolder {

        SimpleDraweeView avatar;
        TextView nickName;
        TextView commentTime;
        TextView commentContent;
        TextView serviceName;
    }

}
