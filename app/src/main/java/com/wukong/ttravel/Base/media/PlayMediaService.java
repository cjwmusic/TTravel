package com.wukong.ttravel.Base.media;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;


import com.wukong.ttravel.Utils.CommonUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 通用声音播放服务
 */
public class PlayMediaService extends Service {

    private static final String TAG = "PlayMediaService";

    public static final String INTENT_MEDIA_PLAY = "INTENT_MEDIA_PLAY";

    private AtomicBoolean isPlaying;

    private LinkedBlockingQueue<String> mediaQueue;

    private ExecutorService executorService;

    private Object syncObj;

    private DMediaPlayer dMediaPlayer;

    private TelephonyManager mTelephonyManager;

    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newFixedThreadPool(2);
        isPlaying = new AtomicBoolean(false);
        mediaQueue = new LinkedBlockingQueue<String>();
        syncObj = new Object();
        dMediaPlayer = new DMediaPlayer(this);
        executorService.execute(dMediaPlayer);
        mTelephonyManager = (TelephonyManager) getSystemService(Activity.TELEPHONY_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (dMediaPlayer != null) {
                dMediaPlayer.destroy();
            }
            if (executorService != null) {
                executorService.shutdown();
                executorService = null;
            }
            if (mediaQueue != null) {
                mediaQueue.clear();
                mediaQueue = null;
            }
            mTelephonyManager = null;
        } catch (Exception e) {
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "PlayMediaService start");
        if (intent != null) {
            String filePath = intent.getStringExtra(INTENT_MEDIA_PLAY);
            if (!TextUtils.isEmpty(filePath)
                    && mTelephonyManager.getCallState() == TelephonyManager.CALL_STATE_IDLE) {
                try {
                    mediaQueue.put(filePath);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MediaSender implements Runnable {

        @Override
        public void run() {
            try {
                Log.d(TAG, "MediaSender start");
                while (!Thread.interrupted()) {
                    String mediaFile = mediaQueue.take();
                    synchronized (syncObj) {
                        while (!isPlaying.compareAndSet(false, true)) {
                            syncObj.wait();
                        }
                    }
                    Message obtainMessage = dMediaPlayer.handler.obtainMessage(
                            DMediaPlayer.MSG_START, mediaFile);
                    dMediaPlayer.handler.sendMessage(obtainMessage);
                }
                Log.d(TAG, "MediaSender end");
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

    }

    private class DMediaPlayer implements Runnable, MediaPlayer.OnCompletionListener,
            MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

        public static final int MSG_START = 1;

        private Looper looper;

        public Handler handler;

        private MediaPlayer mediaPlayer;

        private Context context;

        DMediaPlayer(Context context) {
            this.context = context;
            AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            if (audioManager != null) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
            }
        }

        @Override
        public void run() {
            Log.d(TAG, "DMediaPlayer start");
            Looper.prepare();
            looper = Looper.myLooper();
            handler = new Handler(looper) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case DMediaPlayer.MSG_START:
                            play((String) msg.obj);
                            break;

                        default:
                            break;
                    }
                }
            };
            executorService.execute(new MediaSender());
            Looper.loop();
        }

        private void play(String resFile) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.setOnErrorListener(this);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.setDataSource(context, Uri.parse(resFile));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                destroyMediaPlayer();
            }
        }

        @Override
        public boolean onError(MediaPlayer mp, int arg1, int arg2) {
            Log.e(TAG, "onError");
            destroyMediaPlayer();
            return false;
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.d(TAG, "onCompletion");
            destroyMediaPlayer();
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.d(TAG, "onPrepared");
            CommonUtil.acquireLock(context);
        }

        private void destroyMediaPlayer() {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            CommonUtil.releaseLock(context);
            synchronized (syncObj) {
                isPlaying.set(false);
                syncObj.notifyAll();
            }
        }

        public void destroy() {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            if (looper != null) {
                looper.quit();
            }
            destroyMediaPlayer();
        }
    }

}
