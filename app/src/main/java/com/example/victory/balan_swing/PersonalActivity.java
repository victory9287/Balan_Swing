package com.example.victory.balan_swing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class PersonalActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    SurfaceHolder mSh;
    String sdRootPath;
    MediaPlayer mPlayer;

    boolean StartNStop = true;
    boolean mFirst = true;

    PlaybackParams params;

    SharedPreferences pref;
    int lang, club;

    private LinearLayout mPDRField;
    MYView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        SurfaceView sv = (SurfaceView) findViewById(R.id.svVideo);
        mSh = sv.getHolder();
        mSh.addCallback(this);

        mView = new MYView(this);
        mPDRField = (LinearLayout)findViewById(R.id.personalGraphView);
        mPDRField.addView(mView);
    }

    public void btnClick(View view) {
        switch (view.getId())
        {
            case R.id.btnPersonalBack:
                deletePlayer();
                Intent intent = new Intent(PersonalActivity.this, MenuActivity.class);
                startActivity(intent);
                deletePlayer();
                finish();
                break;
        }
    }

    public void loadVideoSource() {

        sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        //자기꺼 주석 풀어서 쓰기!

        //String filePath = sdRootPath+"/DCIM/Camera"+"/20170426_162440.mp4"; //영서 오빠 영상
        //String filePath = sdRootPath+"/DCIM/Camera"+"/20170314_225610.mp4"; // 종현이 영상
        String filePath = sdRootPath + "/DCIM/Camera"+"/Mswing.mp4"; // 넥서스 영상


        try {

            mPlayer = new MediaPlayer();

            //error 1, 2147483648 나는 코드
            //mPlayer.setDataSource(filePath);

            //에러 수정 코드
            FileInputStream fileInputStream = new FileInputStream(filePath);
            mPlayer.setDataSource(fileInputStream.getFD());
            fileInputStream.close();

            mPlayer.setDisplay(mSh);

            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (StartNStop) {
                        mPlayer.start();
                    } else {
                        mPlayer.pause();
                    }
                }
            });
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mPlayer.start();
                }
            });

            mPlayer.prepareAsync();


        } catch (IOException e) {

            return;

        }

    }


    public void onPrepared(MediaPlayer mp) {

        if (mFirst) {

            mFirst = false;

            mPlayer.start();

            Toast.makeText(getApplicationContext(), "비디오 소스 로드 완료", Toast.LENGTH_SHORT).show();
        }

    }


    public void surfaceCreated(SurfaceHolder holder) {

        loadVideoSource();

        mSh.setFixedSize(mPlayer.getVideoWidth(), mPlayer.getVideoHeight());

    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
    }


    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonPlay:
                mPlayer.start();
                break;

            case R.id.buttonPause:
                mPlayer.pause();
                break;

            case R.id.buttonStop:
                deletePlayer();
                StartNStop = false;
                loadVideoSource();
                StartNStop = true;
                break;

            case R.id.buttonSlow:
                SlowPlayer();
                break;

            case R.id.buttonReturn:
                ReturnVelocity();
                break;

            case R.id.buttonFast:
                FastPlayer();
                break;

        }

    }

    private void FastPlayer() {
        //재생속도
        params = mPlayer.getPlaybackParams();

        float speed = 1.5f;
        mPlayer.setPlaybackParams(params.setSpeed(speed));
    }

    private void ReturnVelocity() {
        //재생속도
        params = mPlayer.getPlaybackParams();

        float speed = 1.0f;
        mPlayer.setPlaybackParams(params.setSpeed(speed));
    }

    private void SlowPlayer() {
        //재생속도
        params = mPlayer.getPlaybackParams();

        float speed = 0.5f;
        mPlayer.setPlaybackParams(params.setSpeed(speed));
    }


    public void deletePlayer() {

        if (mPlayer != null) {

            mPlayer.stop();

            mPlayer.release();

            mPlayer = null;

        }

    }
}
