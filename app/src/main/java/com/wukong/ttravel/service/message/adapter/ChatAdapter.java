package com.wukong.ttravel.service.message.adapter;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.Base.Helper;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.message.model.TTIMMessage;

import java.util.List;

/**
 * Created by wukong on 16/3/7.
 */
public class ChatAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    Context mContext;

    String TEXT_MESSAGE = "text";
    String TEXT_MESSAGE_MY = "text_my";
    String IMAGE_MESSAGE = "image";
    String IMAGE_MESSAGE_MY = "image_my";
    String TIME_LABEL = "time";
    String VOICE_MESSAGE = "voice";
    String VOICE_MESSAGE_MY = "voice_my";

    int KEY_TYPE = 1 << 26;
    int KEY_HOLDER = 1 << 27;

    private List<TTIMMessage> mData;
    private AudioManager mAudioManager;      //语音管理
    private ClipboardManager mClipboardManager;   //剪贴板

    private MsgClickListener msgClickListener;


    public ChatAdapter(Context context, List data) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = data;
        mContext = context;
        mClipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
//        chatHelper = ChatHelper.getInstance(context);
    }

    //消息Cell上的一些点击事件通过接口的形式传递出去
    public void setMsgClickListener(MsgClickListener msgClickListener) {
        this.msgClickListener = msgClickListener;
    }

    public void addMessage(TTIMMessage message) {
        mData.add(message);
//        notifyDataSetChanged();
    }

    public void setMessageList(List<TTIMMessage> messageList) {
        this.mData = messageList;
        notifyDataSetChanged();
    }

    public void addMessageList(List<TTIMMessage> messageList) {
        this.mData.addAll(messageList);
        notifyDataSetChanged();
    }

    public List<TTIMMessage> getMessageList() {
        return mData;
    }

    /*判断消息是不是自己发送的*/
    private Boolean isMy(TTIMMessage message) {
        return Helper.sharedHelper().getUserId().equals(message.getSendUserId());
    }

    //加载用户头像
    private void loadAvatar(SimpleDraweeView avatar, String url) {
        ViewGroup.LayoutParams avatarLayoutParams = avatar.getLayoutParams();
        avatar.setImageURI(ImgUtil.getCDNUrlWithPathStr(url));
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

        final TTIMMessage message = mData.get(position);
        final String senderAvatar = message.getSendUserAvatar();
        final String sendUserId = message.getSendUserId();

         /*
        文本类型
         */
        if (message.getType() == message.MESSAGE_TYPE_TEXT) {
            convertView = getTextMessageView(convertView, false);
            TextMessageHolder holder = (TextMessageHolder) convertView.getTag(KEY_HOLDER);

            //TO DO 需添加表情处理
            holder.content.setText(message.getText());
            loadAvatar(holder.avatar, senderAvatar);

            if (holder.loading != null) {
                holder.loading.setVisibility(message.getLoading() ? View.VISIBLE : View.GONE);
            }
            //点击头像
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Router.sharedRouter().open("profile/" + sendUserId);
                    msgClickListener.avatarOnClickListener(sendUserId);
                }
            });
            //长按消息体事件
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View contextMenu = mInflater.inflate(R.layout.message_delete_dialog, null);
                    final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
                    dialog.setView(contextMenu, 0, 0, 0, 0);
                    dialog.show();
                    TextView deleteTv = (TextView) contextMenu.findViewById(R.id.singleContextMenu);
                    deleteTv.setText("复制");
                    deleteTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            ClipData clipData = ClipData.newPlainText(null, message.getText());
                            mClipboardManager.setPrimaryClip(clipData);
                        }
                    });
                    return true;
                }
            });
        }

        return convertView;

    }

    //获取文本消息的聊天Cell
    private View getTextMessageView(View convertView, boolean isMy) {
        if (convertView != null && convertView.getTag() != null && convertView.getTag(KEY_TYPE) != null) {
            String type = (String) convertView.getTag(KEY_TYPE);
            if (isMy && TEXT_MESSAGE_MY.equals(type)) {
                return convertView;
            } else if (!isMy && TEXT_MESSAGE.equals(type)) {
                return convertView;
            }
        }
        if (isMy) {
            convertView = mInflater.inflate(R.layout.message_type_text_my, null);
            convertView.setTag(KEY_TYPE, TEXT_MESSAGE_MY);
        } else {
            convertView = mInflater.inflate(R.layout.message_type_text, null);
            convertView.setTag(KEY_TYPE, TEXT_MESSAGE);
        }
        TextMessageHolder holder = new TextMessageHolder();
        holder.avatar = (SimpleDraweeView) convertView.findViewById(R.id.avatar);
        holder.content = (TextView) convertView.findViewById(R.id.content);
        if (isMy) {
            holder.loading = (ProgressBar) convertView.findViewById(R.id.loading);
            holder.retry = (Button) convertView.findViewById(R.id.retry);
        }
        convertView.setTag(KEY_HOLDER, holder);
        return convertView;
    }


    static class TextMessageHolder {
        public SimpleDraweeView avatar;
        public TextView content;
        public ProgressBar loading;
        public Button retry;
    }

    static class ImageMessageHolder {
        public SimpleDraweeView avatar; //用户头像
        public SimpleDraweeView content; //文本消息内容
        public ProgressBar loading;  //加载进度的视图
    }

    static class TimeLabelHolder {
        public TextView time;    //显示时间
    }

    public interface MsgClickListener {
        public void avatarOnClickListener(String sendUserId);

        public void itemOnClickListener(String itemId);

        public void convertViewOnClickListener(TTIMMessage msg);
    }
}
