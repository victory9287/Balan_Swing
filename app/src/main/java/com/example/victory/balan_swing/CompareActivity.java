package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class CompareActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    SharedPreferences pref;
    int lang, sample;

    int[] profileID = {R.drawable.profile_m, R.drawable.profile_w, R.drawable.profile_m};
    String[] select_sample;

    SurfaceHolder t_mSh;
    SurfaceHolder b_mSh;
    String sdRootPath;
    MediaPlayer mPlayer;
    MediaPlayer mPlayer2;

    boolean StartNStop = true;
    boolean mFirst = true;

    PlaybackParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        init();
        SurfaceView sv = (SurfaceView)findViewById(R.id.partnerVideo);
//        SurfaceView sv2 = (SurfaceView)findViewById(R.id.myVideo);
        t_mSh = sv.getHolder();
        t_mSh.addCallback(this);
//        b_mSh = sv2.getHolder();
//        b_mSh.addCallback(this);
    }

    public void init() {
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);
        sample = pref.getInt("sample", -1);

        if (sample == -1) {
            Toast.makeText(this, "no sample", Toast.LENGTH_SHORT).show();
            finish();
        }
        select_sample = getResources().getStringArray(R.array.select_sample);

        TextView compare_name = (TextView) findViewById(R.id.compare_name);
        compare_name.setText(select_sample[sample+lang*3]);

        ImageView compare_profile = (ImageView) findViewById(R.id.compare_profile);
        compare_profile.setImageResource(profileID[sample]);
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnCompareBack:
                intent = new Intent(CompareActivity.this, MenuActivity.class);
                startActivity(intent);
                deletePlayer();
                finish();
                break;
            case R.id.btnDetail:
                intent = new Intent(CompareActivity.this, DetailActivity.class);
                startActivity(intent);
                deletePlayer();
                break;
            case R.id.btnSetting:
                intent = new Intent(CompareActivity.this, SelectActivity.class);
                startActivity(intent);
                deletePlayer();
                // 설정 바꾸면 finish
                // 아니면 그대로
        }
    }

    public void loadVideoSource() {

        sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        String filePath[] = {sdRootPath + "/DCIM/Camera"+"/swing.mp4",
            sdRootPath + "/DCIM/Camera"+"/Mswing.mp4"};// 넥서스 영상


        try {

            mPlayer = new MediaPlayer();
            mPlayer2 = new MediaPlayer();

            //error 1, 2147483648 나는 코드
            //mPlayer.setDataSource(filePath);

            //에러 수정 코드
            FileInputStream fileInputStream1;
            FileInputStream fileInputStream2;

                fileInputStream1 = new FileInputStream(filePath[0]);

                mPlayer.setDataSource(fileInputStream1.getFD());
                fileInputStream1.close();

                fileInputStream2 = new FileInputStream(filePath[1]);

                mPlayer2.setDataSource(fileInputStream2.getFD());
                fileInputStream2.close();



            final float slow = 0.3f;

            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (StartNStop) {
                        params = mPlayer.getPlaybackParams();

                        mPlayer.setPlaybackParams(params.setSpeed(slow));
                        mPlayer.start();
                    } else {
                        mPlayer.pause();
                    }
                }
            });

            mPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (StartNStop) {
                        params = mPlayer2.getPlaybackParams();

                        mPlayer2.setPlaybackParams(params.setSpeed(slow));
                        mPlayer2.start();
                    } else {
                        mPlayer2.pause();
                    }
                }
            });

            mPlayer.prepareAsync();
//            mPlayer2.prepareAsync();


        } catch (IOException e) {

            return;

        }

    }


    public void onPrepared(MediaPlayer mp) {

        if (mFirst) {

            mFirst = false;

            mPlayer.start();
 //           mPlayer2.start();
        }

    }


    public void surfaceCreated(SurfaceHolder holder) {

        loadVideoSource();


        t_mSh.setFixedSize(mPlayer.getVideoWidth(), mPlayer.getVideoHeight());
        mPlayer.setDisplay(t_mSh);

//        b_mSh.setFixedSize(mPlayer2.getVideoWidth(), mPlayer2.getVideoHeight());
//        mPlayer2.setDisplay(b_mSh);
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
    }


    public void deletePlayer() {

        if (mPlayer != null && mPlayer2 !=null) {

            mPlayer.stop();
            mPlayer2.stop();

            mPlayer.release();
            mPlayer2.release();

            mPlayer = null;
            mPlayer2 = null;

        }

    }
}

