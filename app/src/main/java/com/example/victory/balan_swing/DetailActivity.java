package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

import static com.example.victory.balan_swing.SignupActivity.font;

public class DetailActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    SharedPreferences pref;
    int lang, sample;

    String[] detail_step;
    int[] detailID = {
            R.id.btnDetail1, R.id.btnDetail2, R.id.btnDetail3, R.id.btnDetail4
    };

    Button[] step;

    SurfaceView sv[];
    SurfaceHolder mSh[];
    String sdRootPath;
    MediaPlayer mPlayer[];

    PlaybackParams params;
    static int detail_Time[];

    private LinearLayout mPDRField;
    MYView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();

        mView = new MYView(this);
        mPDRField = (LinearLayout)findViewById(R.id.personalGraphView);
        mPDRField.addView(mView);
    }

    public void init() {

        sv = new SurfaceView[2];
        sv[0]= (SurfaceView)findViewById(R.id.detail_partnerVideo);
        sv[1] = (SurfaceView)findViewById(R.id.detail_myVideo);

        mSh = new SurfaceHolder[2];

        for(int i=0;i<2;i++){
            mSh[i] = sv[i].getHolder();
            mSh[i].addCallback(this);
        }

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);
        sample = pref.getInt("sample", -1);

        detail_step = getResources().getStringArray(R.array.detail_step);
        detail_Time = new int[4];
        step = new Button[4];
        for (int i = 0; i < 4; i++) {
            step[i] = (Button) findViewById(detailID[i]);
            step[i].setText(detail_step[i+(lang*4)]);
            step[i].setTypeface(font);
            step[i].setAlpha(0.5f);
            step[i].setBackgroundColor(Color.GRAY);
        }
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnDetailBack:
                deletePlayer();
                finish();
                break;
            case R.id.btnHome:
                intent = new Intent(DetailActivity.this, MenuActivity.class);
                startActivity(intent); // resultcode 확인필요
                deletePlayer();
                // compareActivity 삭제
                finish();
                break;
            case R.id.btnDetail1:
                clickBtn(0);
                break;
            case R.id.btnDetail2:
                clickBtn(1);
                break;
            case R.id.btnDetail3:
                clickBtn(2);
                break;
            case R.id.btnDetail4:
                clickBtn(3);
                break;


        }
    }
    public void clickBtn(int num){
        detail_Time[num] = mPlayer[1].getCurrentPosition()*2/1000;
        step[num].setAlpha(1.0f);
        step[num].setBackgroundColor(Color.BLACK);
        step[num].setTextColor(Color.WHITE);

        Toast.makeText(this, detail_Time[num]+"초", Toast.LENGTH_SHORT).show();
    }
    public void loadVideoSource() {

        sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        String filePath[] = {sdRootPath + "/DCIM/Camera"+"/swing.mp4",
                sdRootPath + "/DCIM/Camera"+"/Mswing.mp4"};// 넥서스 영상


        try {

            mPlayer = new MediaPlayer[2];

            //error 1, 2147483648 나는 코드
            //mPlayer.setDataSource(filePath);

            //에러 수정 코드
            FileInputStream fileInputStream[] = new FileInputStream[2];

            final float slow = 0.45f;
            for(int i=0;i<2;i++){
                mPlayer[i] = new MediaPlayer();
                fileInputStream[i] = new FileInputStream(filePath[i]);

                mPlayer[i].setDataSource(fileInputStream[i].getFD());
                fileInputStream[i].close();



                mPlayer[i].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        //mediaPlayer.start();
                    }
                });
            }

            mPlayer[0].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    params = mediaPlayer.getPlaybackParams();

                    mediaPlayer.setPlaybackParams(params.setSpeed(slow));
                    mediaPlayer.start();
                }
            });

            mPlayer[1].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    params = mediaPlayer.getPlaybackParams();

                    mediaPlayer.start();
                }
            });

            mPlayer[0].prepareAsync();
            mPlayer[1].prepareAsync();

        } catch (IOException e) {

            return;

        }

    }


    public void onPrepared(MediaPlayer mp) {

    }


    public void surfaceCreated(SurfaceHolder holder) {

        loadVideoSource();

        mSh[0].setFixedSize(mPlayer[0].getVideoWidth(), mPlayer[0].getVideoHeight());
        mPlayer[0].setDisplay(mSh[0]);

        holder.setFixedSize(mPlayer[1].getVideoWidth(), mPlayer[1].getVideoHeight());
        mPlayer[1].setDisplay(holder);

    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void deletePlayer() {

        for (int i = 0; i < 2; i++) {
            if (mPlayer[i] != null) {

                mPlayer[i].stop();
                mPlayer[i].release();
                mPlayer[i] = null;

            }
        }
    }
}
