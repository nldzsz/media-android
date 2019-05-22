package com.media;

import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.media.Audio.ADAudioMediaPlayer;
import com.media.Audio.ADAudioTrackPlayer;
import com.media.Common.DDlog;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ADAudioMediaPlayer aplayer;
    private ADAudioTrackPlayer bPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        aplayer = new ADAudioMediaPlayer(this);

//        aplayer.playWithRawId(R.raw.test_mp3_1);

//        aplayer.playLocal("test_mp3.mp3");
//        aplayer.playRomote("https://img.flypie.net/test-mp3-1.mp3");


        //// ========= audiotrack 播放asserts目录下的音频文件 =========/////
        /** 遇到问题 1、当文件大小超过1M时，无法通过AssetManager获取InputStream
         *  解决方案 1、MP3，arm等格式文件不受此限制，将PCM文件后缀改为.amr即可
         * */
        /** 遇到问题2：通过getAssets().openFd().getFileDescriptor()获取到的FileDescriptor来构造FileInputStream对象，读取数据时不是从文件开头读取
         *  解决方案：getAssets().open()方式获取InputStream来读取文件中数据，是从文件头开始的。
         * */
//        bPlayer = new ADAudioTrackPlayer(this,"test_441_s16be_2.amr",44100,
//                AudioFormat.ENCODING_PCM_16BIT,AudioFormat.CHANNEL_OUT_STEREO,true);

        bPlayer = new ADAudioTrackPlayer(this,"test_441_s16le_2.amr",44100,
                AudioFormat.ENCODING_PCM_16BIT,AudioFormat.CHANNEL_OUT_STEREO,false);

//        bPlayer = new ADAudioTrackPlayer(this,"test_441_f32le_2.amr",44100,
//                AudioFormat.ENCODING_PCM_FLOAT,AudioFormat.CHANNEL_OUT_STEREO,false);

        bPlayer.play();
    }
}
