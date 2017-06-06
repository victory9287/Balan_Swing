package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.FileInputStream;
import java.io.IOException;

import static com.example.victory.balan_swing.SignupActivity.font;

public class PersonalActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    SurfaceHolder mSh;
    String sdRootPath;
    MediaPlayer mPlayer;

    SurfaceView sv2;
    SurfaceHolder mSh2;
    MediaPlayer mPlayer2;
    String filePath2;

    boolean StartNStop = true;
    boolean mFirst = true;

    PlaybackParams params;

    SharedPreferences pref;
    int lang, club;

    private LinearLayout mPDRField;
    MYView mView;

    Button btn1,btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        btn1 = (Button)findViewById(R.id.buttonSlow);
        btn2 = (Button)findViewById(R.id.buttonReturn);
        btn3 = (Button)findViewById(R.id.buttonFast);
        btn1.setTypeface(font);
        btn2.setTypeface(font);
        btn3.setTypeface(font);

        SurfaceView sv = (SurfaceView) findViewById(R.id.svVideo);
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOverlap();
            }
        });
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
                        mPlayer.pause(); // 시작부분에 멈춤!
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

    public void viewOverlap() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View videoView = inflater.inflate(R.layout.dialog_video, null);
        sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        filePath2 = sdRootPath + "/DCIM/Camera"+"/Mswing.mp4";

        sv2 = (SurfaceView) videoView.findViewById(R.id.bigvideo);
        sv2.setZOrderOnTop(true);
        mSh2 = sv2.getHolder();
        mSh2.setFormat(PixelFormat.TRANSPARENT);
        mSh2.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {

                    //에러 수정 코드
                    FileInputStream fileInputStream;
                    Log.d("check", filePath2);
                    mPlayer2 = new MediaPlayer();
                    mPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {

                        }
                    });
                    fileInputStream = new FileInputStream(filePath2);

                    mPlayer2.setDataSource(fileInputStream.getFD());
                    fileInputStream.close();


                    mPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            params = mediaPlayer.getPlaybackParams();
                            mediaPlayer.start();
                            mediaPlayer.setLooping(true);
                        }
                    });

                    mPlayer2.prepareAsync();

                } catch (IOException e) {
                    return;

                }

                mPlayer2.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(videoView);

        AlertDialog dialog = builder.show();
        dialog.show();
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

    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
