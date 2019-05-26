package com.media;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.media.Audio.ADAudioMediaPlayer;
import com.media.Audio.ADAudioTrackPlayer;
import com.media.Audio.ADOpenSLES;
import com.media.Audio.AudioPlayInterface;
import com.media.Common.DDlog;
import com.media.Common.PathTool;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity{

    private Spinner mTestSpinner;
    private AudioPlayInterface mPlayer;

    private static final String[] Test_items = {
            "MediaPlayer 播放音频 By RawId",
            "MediaPlayer 播放音频 By local",
            "MediaPlayer 播放音频 By https",
            "AduioTrack 播放音频",
            "OpenSL ES 播放音频"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTestSpinner = (Spinner) findViewById(R.id.splinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Test_items);
        mTestSpinner.setAdapter(adapter);
        requestPermission();

        // 测试手机存储
//        PathTool.testPath(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void onClickStart(View v) {
        switch (mTestSpinner.getSelectedItemPosition()) {
            case 0:{
                DDlog.logd("播放1 " + mTestSpinner.getSelectedItem());
                mPlayer = new ADAudioMediaPlayer(this,R.raw.test_mp3_1);
                mPlayer.play();
            }
            break;
            case 1:{
                DDlog.logd("播放2 " + mTestSpinner.getSelectedItem());
                mPlayer = new ADAudioMediaPlayer(this,"test_mp3.mp3");
                mPlayer.play();
            }
            break;
            case 2:{
                DDlog.logd("播放3 " + mTestSpinner.getSelectedItem());
                mPlayer = new ADAudioMediaPlayer(this, PathTool.getUriByString("https://img.flypie.net/test-mp3-1.mp3"));
                mPlayer.play();
            }
            break;
            case 3:{
                DDlog.logd("播放4 " + mTestSpinner.getSelectedItem());
                mPlayer = new ADAudioTrackPlayer(this,"test_441_s16be_2.amr",44100,
                        AudioFormat.ENCODING_PCM_16BIT,AudioFormat.CHANNEL_OUT_STEREO,true);
//                mPlayer = new ADAudioTrackPlayer(this,"test_441_s16le_2.amr",44100,
//                        AudioFormat.ENCODING_PCM_16BIT,AudioFormat.CHANNEL_OUT_STEREO,false);
//                mPlayer = new ADAudioTrackPlayer(this,"test_441_s16be_2.amr",44100,
//                        AudioFormat.ENCODING_PCM_16BIT,AudioFormat.CHANNEL_OUT_STEREO,true);
//                mPlayer = new ADAudioTrackPlayer(this,"test_441_f32le_2.amr",44100,
//                        AudioFormat.ENCODING_PCM_FLOAT,AudioFormat.CHANNEL_OUT_STEREO,false);
                mPlayer.play();
            }
            break;
            case 4:{
                DDlog.logd("播放5 " + mTestSpinner.getSelectedItem());
                File exFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                final String cPath = exFile.getAbsolutePath() + "/audio.pcm";
                mPlayer = new ADOpenSLES(cPath, 44100, 1, 1);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("路径", "onCreate: " + cPath);
                        PathTool.copyAssetsToDst(MainActivity.this,"test_441_s16le_2.amr",cPath);

                        // 拷贝完成后开始播放
                        mPlayer.play();
                    }
                }).start();
            }
            break;
            default:
                break;

        }
    }

    public void onClickStop(View v) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }
    }

    //  ====== 权限申请 6.0以上要访问应用内目录必须要进行运行时权限申请======= //
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    public void requestPermission(){
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO}, EXTERNAL_STORAGE_REQ_CODE);
            }
        }
        else {
            Log.d("测试", "已经有权限了");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQ_CODE: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作

                } else {
                    //申请失败，可以继续向用户解释。
                }
                return;
            }
        }
    }
    //  ====== 权限申请 ======= //
}
