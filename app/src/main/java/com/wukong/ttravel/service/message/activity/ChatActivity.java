package com.wukong.ttravel.service.message.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.im.AVImClientManager;
import com.wukong.ttravel.Base.im.ImTypeMessageEvent;
import com.wukong.ttravel.Base.views.RecordButton;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.StrUtil;
import com.wukong.ttravel.service.message.adapter.ChatAdapter;
import com.wukong.ttravel.service.message.model.TTIMMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by wukong on 16/3/7.
 */
public class ChatActivity extends BaseActivity implements ChatAdapter.MsgClickListener {

    @Bind(R.id.rootView)
    LinearLayout rootView;

    //聊天内容列表
    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.cellInputGroup)
    View cellInputGroup;
    @Bind(R.id.inputEdit)
    EditText inputEdit;
    @Bind(R.id.voiceRecord)
    RecordButton voiceRecord;
    @Bind(R.id.chatSend)
    TextView chatSend;
    @Bind(R.id.openAttach)
    View openAttach;


    private ChatAdapter adapter;
    protected AVIMConversation imConversation; //聊天会话对象
    private String receiverId; //对方的userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        //获取调用者传递过来的ConvesationId
        Bundle bundle = getIntent().getExtras();
        receiverId = bundle.getString("receiverId");

        adapter = new ChatAdapter(this, new ArrayList());
        adapter.setMsgClickListener(this);

        initListener();

        listView.setAdapter(adapter);
        //通过对方的ID获取conversation,成功拿到Conversation后拉去消息
        if (receiverId != null) {
            getConversation(receiverId);
        }
    }


    /**
     * 根据对方的Id获取 conversatio对象
     * 为了避免重复的创建，此处先 query 是否已经存在只包含该 member 的 conversation
     * 如果存在，则直接赋值给 ChatFragment，否者创建后再赋值
     */
    private void getConversation(final String memberId) {

        final AVIMClient client = AVImClientManager.getInstance().getClient();
        AVIMConversationQuery conversationQuery = client.getQuery();
        conversationQuery.withMembers(Arrays.asList(memberId), true);
        conversationQuery.whereEqualTo("type", 0);
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (filterException(e)) {
                    //注意：此处仍有漏洞，如果获取了多个 conversation，默认取第一个
                    if (null != list && list.size() > 0) {
                        imConversation = list.get(0);
                        fetchMessages();
                    } else {
                        HashMap<String, Object> attributes = new HashMap<String, Object>();
                        attributes.put("type", 0);  //这个很重要，要与iOS端对应
                        client.createConversation(Arrays.asList(memberId), null, attributes, false, new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation avimConversation, AVIMException e) {
                                if (avimConversation != null) {
                                    imConversation = avimConversation;
                                    fetchMessages();
                                }
                            }
                        });
                    }
                }
            }
        });
    }



    /**
     * 拉取消息，必须加入 conversation 后才能拉取消息
     */
    private void fetchMessages() {

        //查询最近的20条消息记录
        imConversation.queryMessages(new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (filterException(e)) {

                    //封装成TTIMMessage对象
                    if (list != null && list.size() > 0) {
                        ArrayList<TTIMMessage> ttimMessages = new ArrayList<TTIMMessage>();
                        for (int index = 0; index < list.size(); index++) {
                            System.out.println("获取到的消息内容为" + list.get(index).getContent());

                            TTIMMessage msg = new TTIMMessage(list.get(index));
                            ttimMessages.add(msg);
                        }
                        adapter.setMessageList(ttimMessages);
                        adapter.notifyDataSetChanged();
                        scrollToLast();
                        ;
                    }

                }
            }
        });
    }

    private void sendTextMessage(String text) {
        ///构造文本消息
        AVIMTextMessage message = new AVIMTextMessage();
        message.setText(text);

        TTIMMessage msg = new TTIMMessage(message);
        //更新UI
        adapter.addMessage(msg);
        adapter.notifyDataSetChanged();


        scrollToLast();
        //发送消息
        imConversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
//                itemAdapter.notifyDataSetChanged();
            }
        });

    }



    private void initListener() {


        inputEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                chatFaceGroup.setVisibility(View.GONE);
//                attachPanel.setVisibility(View.GONE);
//                chatFaceIcon.setText(getString(R.string.icon_chat_face));
            }
        });
        inputEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    chatFaceGroup.setVisibility(View.GONE);
//                    attachPanel.setVisibility(View.GONE);
//                    chatFaceIcon.setText(getString(R.string.icon_chat_face));
                }
            }
        });
        inputEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    chatSend.setVisibility(View.VISIBLE);
                    openAttach.setVisibility(View.GONE);
                } else {
                    chatSend.setVisibility(View.GONE);
                    openAttach.setVisibility(View.VISIBLE);
                }
                if (inputEdit.getTag() != null && (Boolean) inputEdit.getTag()) {
                    inputEdit.setTag(false);
                    return; // 是表情输入，忽略此次内容变化
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (view.getChildCount() > 0) {
                        View first = view.getChildAt(0);
                        if (first != null && view.getFirstVisiblePosition() == 0 && first.getTop() == 0) {
//                            loadOldMessages();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
//                attachPanel.setVisibility(View.GONE);
                return false;
            }
        });



        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputEdit.getText().toString();
                if (!StrUtil.isEmpty(text)) {
//                    wukongSender.sendTextMessage(text);
                    sendTextMessage(text);
                    finishSend();
                }
            }
        });


        openAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
//                chatFaceGroup.setVisibility(View.GONE);
//                attachPanel.setVisibility(View.VISIBLE);
            }
        });
    }


    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputEdit.getWindowToken(), 0);
    }

    public void finishSend() {
        inputEdit.setText(null);
        scrollToLast();
    }


    @Override
    protected void onStart() {
        super.onStart();
        initIM();
//        SHZApplication.getInstance().setCurrentChatUserId(receiverId);
    }

    private void initIM() {



    }

    private void scrollToLast() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(adapter.getCount() - 1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //--------------------Event Bus ----------------------------//
    /**
     * 处理推送过来的消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     */
    public void onEvent(ImTypeMessageEvent event) {
//        if (null != imConversation && null != event &&
//                imConversation.getConversationId().equals(event.conversation.getConversationId())) {
//            itemAdapter.addMessage(event.message);
//            itemAdapter.notifyDataSetChanged();
//            scrollToBottom();
//        }
        AVIMMessage message = event.message;
        TTIMMessage ttMessage = new TTIMMessage(message);
        adapter.addMessage(ttMessage);
        scrollToLast();
    }


    //--------------------MsgClickListener----------------------------//
    @Override
    public void avatarOnClickListener(String sendUserId) {

    }

    @Override
    public void itemOnClickListener(String itemId) {

    }

    @Override
    public void convertViewOnClickListener(TTIMMessage msg) {

    }
}
