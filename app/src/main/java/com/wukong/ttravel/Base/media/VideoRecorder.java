package com.wukong.ttravel.Base.media;

import android.media.MediaRecorder;
import android.os.Environment;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoRecorder implements RecordStrategy {

    private MediaRecorder recorder;
    private String fileName;
    private String fileFolder = Environment.getExternalStorageDirectory().getPath() + "/com.kongge";

    private boolean isRecording = false;
    SurfaceHolder surfaceHolder;

    public VideoRecorder(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void ready() {
        File file = new File(fileFolder);
        if (!file.exists()) {
            file.mkdir();
        }
        fileName = getCurrentDate();

        recorder = new MediaRecorder();// 创建mediaRecorder对象

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        // 设置录制视频源为Camera(相机)
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
        //必须在设置声音编码格式、图像编码格式之前
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        // 设置MediaRecorder录制音频的编码为amr
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // 设置录制的视频编码h263 h264
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
        recorder.setVideoSize(176, 144);
        // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
        recorder.setVideoFrameRate(20);
        recorder.setPreviewDisplay(surfaceHolder.getSurface());
        // 设置视频文件输出的路径

        recorder.setOrientationHint(90);
        recorder.setOutputFile(fileFolder + "/" + fileName + ".mp4");
    }

    // 以当前时间作为文件名
    private String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    @Override
    public void start() {
        if (!isRecording) {
            try {
                recorder.prepare();
                recorder.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            isRecording = true;
        }

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        if (isRecording) {
            recorder.stop();
            recorder.release();
            isRecording = false;
        }

    }

    @Override
    public void deleteOldFile() {
        // TODO Auto-generated method stub
        File file = new File(fileFolder + "/" + fileName + ".mp4");
        file.deleteOnExit();
    }

    @Override
    public double getAmplitude() {
        // TODO Auto-generated method stub
        if (!isRecording) {
            return 0;
        }
        return recorder.getMaxAmplitude();
    }

    @Override
    public String getFilePath() {
        // TODO Auto-generated method stub
        return fileFolder + "/" + fileName + ".mp4";
    }

}
