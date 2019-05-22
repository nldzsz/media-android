package com.media.Audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.media.Common.DDlog;
import com.media.Common.PathTool;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;

public class ADAudioMediaPlayer {

    // 上下文
    private Context mContext;

    /** MediaPlayer类
     * 1、它位于安卓的多媒体框架android.media包下
     * 2、它可以直接播放音视频文件，既可以播放本地音视频，也可以播放网络音视频
     * 本例实现播放音频
     * */
    // 音频播放器
    private MediaPlayer madioPlayer;

    public ADAudioMediaPlayer(Context ct) {
        mContext = ct;
    }

    // 直接用Raw资源文件播放
    public void playWithRawId(int rawId) {
        // 初始化
        madioPlayer = MediaPlayer.create(mContext,rawId);
        // 直接播放，不需要调用preapare()函数
        madioPlayer.start();
    }

    public void playLocal(String path) {
        if (madioPlayer == null) {
            madioPlayer = new MediaPlayer();
        }

        /**
         *  通过这种方式，只需要创建一次MediaPlayer对象即可，当需要更换播放文件时，不用重复创建对象；这对于播放列表文件或重复播放使用
         * */
        try {
            AssetFileDescriptor as = PathTool.getAssetFileDescriptor(mContext,path);
            madioPlayer.setDataSource(as.getFileDescriptor());
            madioPlayer.prepare();
            madioPlayer.start();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /** 播放远程音频文件
     * 注意要添加访问网络的权限 <uses-permission android:name="android.permission.INTERNET" />
     * */
    public void playRomote(String url) {
        if (madioPlayer == null) {
            madioPlayer = new MediaPlayer();
        }

        try {
            Uri uri = PathTool.getUriByString(url);
            DDlog.logd(uri.toString());
            madioPlayer.setDataSource(mContext,uri);
            madioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            madioPlayer.prepareAsync();
            madioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    madioPlayer.start();
                }
            });

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    // 播放完成后 要释放资源
    public void stop() {
        if (madioPlayer != null) {
            madioPlayer.stop();
            madioPlayer.release();
        }
    }
}
