package com.wukong.ttravel.Base.views;

/**
 * Created by shijian on 15/9/26.
 */

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wukong.ttravel.Base.media.RecordStrategy;
import com.wukong.ttravel.R;

import java.util.ArrayList;
import java.util.List;

public class RecordButton extends Button {

    private static final int MIN_RECORD_TIME = 1; // 最短录音时间，单位秒
    private static final int RECORD_OFF = 0; // 不在录音
    private static final int RECORD_ON = 1; // 正在录音

    private Dialog mRecordDialog;
    private RecordStrategy mAudioRecorder;
    private Thread mRecordThread;
    private RecordListener listener;

    private int recordState = 0; // 录音状态
    private float recodeTime = 0.0f; // 录音时长，如果录音时间太短则录音失败
    private double voiceValue = 0.0; // 录音的音量值
    private boolean isCanceled = false; // 是否取消录音
    private float downY;

    private TextView dialogTextView;
    private TextView voiceIcon;
    private View volumeGroup;

    private List<View> volumeList = new ArrayList<>();
    private Context mContext;

    Dialog mShortTimeDialog;//提示输入语音时间太短Dialog
    //    private Timer timer;//关闭提示输入语音时间太短计时器
    private final int CLOSE_DIALOG = 0;  //定义关闭对话框的动作信号标志

    public RecordButton(Context context) {
        super(context);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        this.setText("按住 说话");
        if (!isInEditMode()) {
//            setTypeface(FontManager.getTypeface());
        }
    }

    public void setAudioRecord(RecordStrategy record) {
        this.mAudioRecorder = record;
    }

    public void setRecordListener(RecordListener listener) {
        this.listener = listener;
    }

    // 录音时显示Dialog
    private void showVoiceDialog(int flag) {
        if (mRecordDialog == null) {
            mRecordDialog = new Dialog(mContext, R.style.DialogStyle);
            mRecordDialog.setContentView(R.layout.message_type_voice_dialog);
            voiceIcon = (TextView) mRecordDialog.findViewById(R.id.voice_icon);
            volumeGroup = mRecordDialog.findViewById(R.id.volume_group);
            dialogTextView = (TextView) mRecordDialog.findViewById(R.id.record_dialog_txt);
            initVolume();
        }
        switch (flag) {
            case 1:
                voiceIcon.setText("退出录音的图片");
                volumeGroup.setVisibility(GONE);
                dialogTextView.setText("松开手指可取消录音");
                this.setText("松开手指 取消录音");
                break;
            default:
                voiceIcon.setText("正在录音的图片");
                volumeGroup.setVisibility(VISIBLE);
                dialogTextView.setText("向上滑动可取消录音");
                this.setText("松开手指 完成录音");
                break;
        }
        mRecordDialog.show();
    }

    // 录音时间太短时Toast显示
    private void showWarnToast() {
        if (mShortTimeDialog == null) {
            mShortTimeDialog = new Dialog(mContext, R.style.DialogStyle);
//            View warnView = LayoutInflater.from(mContext).inflate(R.layout.message_type_voice_short, null);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) Helper.convertDpToPixel(200, mContext), (int) Helper.convertDpToPixel(150, mContext));
//            warnView.setLayoutParams(params);
            mShortTimeDialog.setContentView(R.layout.message_type_voice_short);
        }

        mShortTimeDialog.show();
        //通过 timer 实现倒计时
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                mainHandler.sendEmptyMessage(CLOSE_DIALOG);
//            }
//        }, 1000);

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

    // 录音Dialog图片随录音音量大小切换
    private void setDialogImage() {
        if (voiceValue < 600.0) {
            updateVolume(1);
        } else if (voiceValue > 600.0 && voiceValue < 1000.0) {
            updateVolume(2);
        } else if (voiceValue > 1000.0 && voiceValue < 1400.0) {
            updateVolume(3);
        } else if (voiceValue > 1400.0 && voiceValue < 1800.0) {
            updateVolume(4);
        } else if (voiceValue > 1800.0 && voiceValue < 2600.0) {
            updateVolume(5);
        } else if (voiceValue > 2600.0 && voiceValue < 6000.0) {
            updateVolume(6);
        } else if (voiceValue > 6000.0 && voiceValue < 8000.0) {
            updateVolume(7);
        } else if (voiceValue > 8000.0) {
            updateVolume(8);
        }
    }

    private void initVolume() {
        volumeList.add(mRecordDialog.findViewById(R.id.volume_1));
        volumeList.add(mRecordDialog.findViewById(R.id.volume_2));
        volumeList.add(mRecordDialog.findViewById(R.id.volume_3));
        volumeList.add(mRecordDialog.findViewById(R.id.volume_4));
        volumeList.add(mRecordDialog.findViewById(R.id.volume_5));
        volumeList.add(mRecordDialog.findViewById(R.id.volume_6));
        volumeList.add(mRecordDialog.findViewById(R.id.volume_7));
        volumeList.add(mRecordDialog.findViewById(R.id.volume_8));
    }

    private void updateVolume(int maxVolume) {
        for (int i = 0; i < 8; i++) {
            View view = volumeList.get(i);
            if (i < maxVolume) {
                view.setVisibility(VISIBLE);
            } else {
                view.setVisibility(GONE);
            }
        }
    }

    // 录音线程
    private Runnable recordThread = new Runnable() {
        @Override
        public void run() {
            recodeTime = 0.0f;
            while (recordState == RECORD_ON) {
                try {
                    Thread.sleep(100);
                    recodeTime += 0.1;
                    // 获取音量，更新dialog
                    if (!isCanceled) {
                        voiceValue = mAudioRecorder.getAmplitude();
                        recordHandler.sendEmptyMessage(1);
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
            setDialogImage();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 按下按钮
                if (recordState != RECORD_ON) {
                    showVoiceDialog(0);
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
                    showVoiceDialog(1);
                }
                if (downY - moveY < 20) {
                    isCanceled = false;
                    showVoiceDialog(0);
                }
                break;
            case MotionEvent.ACTION_UP: // 松开手指
                if (recordState == RECORD_ON) {
                    recordState = RECORD_OFF;
                    if (mRecordDialog.isShowing()) {
                        mRecordDialog.dismiss();
                    }
                    mAudioRecorder.stop();
                    mRecordThread.interrupt();
                    voiceValue = 0.0;
                    if (isCanceled) {
                        mAudioRecorder.deleteOldFile();
                    } else {
                        if (recodeTime < MIN_RECORD_TIME) {
                            showWarnToast();
                            mAudioRecorder.deleteOldFile();
                        } else {
                            if (listener != null) {
                                listener.recordEnd(mAudioRecorder.getFilePath(), (long) recodeTime);
                            }
                        }
                    }
                    isCanceled = false;
                    this.setText("按住 说话");
                }
                break;
        }
        return true;
    }

    public interface RecordListener {
        public void recordEnd(String filePath, long recordTime);
    }
}