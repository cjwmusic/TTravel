package com.wukong.ttravel.Base.views;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.wukong.ttravel.Base.media.RecordStrategy;
import com.wukong.ttravel.R;


/**
 * update by zuozheng
 */
public class RecordTextView extends TextView {

    private static final int MIN_RECORD_TIME = 1; // 最短录音时间，单位秒
    private static final int MAX_RECORD_TIME = 30; // 最长录音时间，单位秒
    private static final int RECORD_OFF = 0; // 不在录音
    private static final int RECORD_ON = 1; // 正在录音

    //    private Dialog mRecordDialog;
    private RecordStrategy mAudioRecorder;
    private Thread mRecordThread;
    private RecordListener listener;

    private int recordState = 0; // 录音状态
    private float recodeTime = 0.0f; // 录音时长，如果录音时间太短则录音失败
    private boolean isCanceled = false; // 是否取消录音
    private float downY;

//    private TextView dialogTextView;
//    private TextView voiceIcon;
//    private View volumeGroup;

    private Context mContext;

    Dialog mShortTimeDialog;//提示输入语音时间太短Dialog
    //    private Timer timer;//关闭提示输入语音时间太短计时器
    private final int CLOSE_DIALOG = 0;  //定义关闭对话框的动作信号标志

    public RecordTextView(Context context) {
        super(context);
        init(context);
    }

    public RecordTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RecordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
//        this.setText("按住 说话");
//        if (!isInEditMode()) {
//            setTypeface(FontManager.getTypeface());
//        }
    }

    public void setAudioRecord(RecordStrategy record) {
        this.mAudioRecorder = record;
    }

    public void setRecordListener(RecordListener listener) {
        this.listener = listener;
    }


    // 录音时间太短时Toast显示
    private void showWarnToast() {
        if (mShortTimeDialog == null) {
            mShortTimeDialog = new Dialog(mContext, R.style.DialogStyle);
            mShortTimeDialog.setContentView(R.layout.message_type_voice_short);
        }

        mShortTimeDialog.show();

        //通过handler实现延迟
        Message messageFinish = mainHandler.obtainMessage();
        messageFinish.what = CLOSE_DIALOG;
        mainHandler.sendMessageDelayed(messageFinish, 1000);
    }


    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLOSE_DIALOG:
                    if (mShortTimeDialog != null && mShortTimeDialog.isShowing()) {
                        mShortTimeDialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    // 开启录音计时线程
    private void callRecordTimeThread() {
        mRecordThread = new Thread(recordThread);
        mRecordThread.start();
    }


    // 录音线程
    private Runnable recordThread = new Runnable() {
        @Override
        public void run() {
            float recordTime = 0.0f;
            while (recordState == RECORD_ON) {
                try {
                    Thread.sleep(100);
                    recordTime += 0.1;
                    // 获取音量，更新dialog

                    Message msg = recordHandler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putFloat("timeValue", recordTime);

                    if (!isCanceled && recodeTime <= MAX_RECORD_TIME) {
                        double voiceValue = mAudioRecorder.getAmplitude();
                        bundle.putDouble("voiceValue", voiceValue);
                        msg.setData(bundle);
                        msg.what = 1;
                        recordHandler.sendMessage(msg);
                    } else {
                        msg.setData(bundle);
                        recordHandler.sendEmptyMessage(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler recordHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.getData() != null && listener != null) {
                recodeTime = msg.getData().getFloat("timeValue");
                if (msg.what == 1) {
                    listener.recording(msg.getData().getDouble("voiceValue"), recodeTime);
                } else {
                    recordStop();
                }
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 按下按钮
                if (recordState != RECORD_ON) {
                    if (listener != null) {
                        listener.recordStart();
                    }

                    downY = event.getY();
                    if (mAudioRecorder != null) {
                        mAudioRecorder.ready();
                        recordState = RECORD_ON;
                        mAudioRecorder.start();
                        callRecordTimeThread();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: // 滑动手指
                float moveY = event.getY();
                if (downY - moveY > 50) {
                    isCanceled = true;
                    if (listener != null) {
                        listener.recordCancel();
                    }
                } else {
                    isCanceled = false;
                    if (listener != null) {
                        listener.recordStart();
                    }
                }
//                if (downY - moveY < 20) {
//                    isCanceled = false;
//                    if (listener != null) {
//                        listener.recordStart();
//                    }
//                }
                break;
            case MotionEvent.ACTION_UP: // 松开手指
                if (recordState == RECORD_ON) {
                    isCanceled = false;
                    recordStop();
                }
                break;
        }
        return true;
    }

    public interface RecordListener {
        public void recordStart();

        public void recording(double voiceValue, float time);

        public void recordCancel();

        public void recordEnd(String filePath, long recordTime);
    }

    void recordStop() {
        recordState = RECORD_OFF;
        mAudioRecorder.stop();
        mRecordThread.interrupt();
        if (isCanceled) {
            mAudioRecorder.deleteOldFile();
        } else {
            if (recodeTime < MIN_RECORD_TIME) {
                showWarnToast();
                mAudioRecorder.deleteOldFile();
            } else {
                //时间太长 默认不处理 直接结束
                if (listener != null) {
                    listener.recordEnd(mAudioRecorder.getFilePath(), (long) recodeTime);
                }
            }
        }
    }
}