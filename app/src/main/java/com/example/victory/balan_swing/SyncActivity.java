package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.victory.balan_swing.DetailActivity.detail_Time;
import static com.example.victory.balan_swing.SignupActivity.font;

public class SyncActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl {
        SharedPreferences pref;
        int lang, sample;

        String[] detail_step;
        int[] detailID2 = {
                R.id.btnSync1, R.id.btnSync2, R.id.btnSync3, R.id.btnSync4
        };

        Button[] step;

        SurfaceView sv;
        SurfaceHolder mSh;
        String sdRootPath;
        MediaPlayer mPlayer;

        VideoControllerView controller;
        ImageView proImg;
        int img[] = {R.drawable.address, R.drawable.topofswing, R.drawable.impact, R.drawable.followthrough};


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sync);
            init();

        }

        public void init() {
            proImg = (ImageView)findViewById(R.id.pro_img);
            proImg.setImageDrawable(getDrawable(img[0]));
            sv = new SurfaceView(this);
//            sv = new SurfaceView[2];
            //sv[0]= (SurfaceView)findViewById(R.id.sync_partnerVideo);
            sv = (SurfaceView)findViewById(R.id.sync_myVideo);

            mSh = sv.getHolder();
            mSh.addCallback(this);


            mPlayer = new MediaPlayer();
            controller = new VideoControllerView(this);
            pref = getSharedPreferences("pref", MODE_PRIVATE);
            lang = pref.getInt("language", 0);
            sample = pref.getInt("sample", -1);

            detail_step = getResources().getStringArray(R.array.detail_step);
            step = new Button[4];
            for (int i = 0; i < 4; i++) {
                step[i] = (Button)findViewById(detailID2[i]);
                step[i].setText(detail_step[i+(lang*4)]);
                step[i].setTypeface(font);
                step[i].setAlpha(0.5f);
                step[i].setBackgroundColor(Color.GRAY);
            }
        }

        public void syncBtnClick(View view) {
            Intent intent;
            switch (view.getId())
            {
                case R.id.btnDetailBack:
                    deletePlayer();
                    finish();
                    break;
                case R.id.btnHome:
                    intent = new Intent(SyncActivity.this, MenuActivity.class);
                    startActivity(intent); // resultcode 확인필요
                    deletePlayer();
                    // compareActivity 삭제
                    finish();
                    break;
                case R.id.btnSync1:
                    clickBtn(0);
                    break;
                case R.id.btnSync2:
                    clickBtn(1);
                    break;
                case R.id.btnSync3:
                    clickBtn(2);
                    break;
                case R.id.btnSync4:
                    clickBtn(3);
                    break;

            }
        }
        public void clickBtn(int num){
            // 재생 부분을 추가해야함.

            step[num].setAlpha(1.0f);
            step[num].setBackgroundColor(Color.BLACK);
            step[num].setTextColor(Color.WHITE);

            proImg.setImageDrawable(getDrawable(img[num]));
            mPlayer.seekTo(detail_Time[num]);
            mPlayer.pause();

            //Toast.makeText(this, detail_Time[num]+"", Toast.LENGTH_SHORT).show();

        }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.show();
        return false;
    }
        public void loadVideoSource() {
            // 싱크 맞추기.
            sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

            String filePath =
                    sdRootPath + "/DCIM/Camera" + "/newMswing.mp4";


            try {

                FileInputStream fileInputStream = new FileInputStream(filePath);

                mPlayer = new MediaPlayer();

                mPlayer.setDataSource(fileInputStream.getFD());
                fileInputStream.close();


                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        //mediaPlayer.start();
                    }
                });
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            mPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    controller.setMediaPlayer(controller.getmPlayer());
                    controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));

                    mediaPlayer.start();
                }
            });

            mPlayer.prepareAsync();
        }
        public void onPrepared(MediaPlayer mp) {

        }


        public void surfaceCreated(SurfaceHolder holder) {

            loadVideoSource();

            holder.setFixedSize(mPlayer.getVideoWidth(), mPlayer.getVideoHeight());
            mPlayer.setDisplay(holder);
        }


        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }


        public void surfaceDestroyed(SurfaceHolder holder) {
        }

        public void deletePlayer() {

            if (mPlayer != null) {

                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;

            }
        }

    // Implement VideoMediaController.MediaPlayerControl
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void pause() {
        mPlayer.pause();
    }

    @Override
    public void seekTo(int i) {
        mPlayer.seekTo(i);
    }

    @Override
    public void start() {
        mPlayer.start();
    }


}
