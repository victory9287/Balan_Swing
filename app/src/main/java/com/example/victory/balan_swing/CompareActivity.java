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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

import static com.example.victory.balan_swing.SignupActivity.font;

public class CompareActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    SharedPreferences pref;
    int lang, sample;

    int[] profileID = {R.drawable.profile_m, R.drawable.profile_w, R.drawable.profile_m};
    String[] select_sample;

    SurfaceView sv[];
    SurfaceHolder mSh[];
    String sdRootPath;
    MediaPlayer mPlayer[];

    SurfaceView sv2;
    SurfaceHolder mSh2;
    MediaPlayer mPlayer2;
    String filePath2;

    PlaybackParams params;

    private LinearLayout mPDRField;
    MYView mView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        init();

    }

    public void init() {

        sv = new SurfaceView[2];
        sv[0]= (SurfaceView)findViewById(R.id.partnerVideo);
        sv[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOverlap(0);
            }
        });
        sv[1] = (SurfaceView)findViewById(R.id.myVideo);
        sv[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOverlap(1);
            }
        });

        mSh = new SurfaceHolder[2];

        for(int i=0;i<2;i++){
            mSh[i] = sv[i].getHolder();
            mSh[i].addCallback(this);
        }


        mView = new MYView(this);
        mPDRField = (LinearLayout)findViewById(R.id.personalGraphView);
        mPDRField.addView(mView);
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);
        sample = pref.getInt("sample", -1);

        if (sample == -1) {
            Toast.makeText(this, "no sample", Toast.LENGTH_SHORT).show();
            finish();
        }
        select_sample = getResources().getStringArray(R.array.select_sample);

        TextView compare_name = (TextView) findViewById(R.id.compare_name);
        compare_name.setTypeface(font);
        compare_name.setText(select_sample[sample+lang*3]);

        ImageView compare_profile = (ImageView) findViewById(R.id.compare_profile);
        compare_profile.setImageResource(profileID[sample]);

    }

    public void viewOverlap(int num) {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View videoView = inflater.inflate(R.layout.dialog_video, null);
        sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        switch (num){
            case 0:
                filePath2 = sdRootPath + "/DCIM/Camera"+"/swing.mp4";
                break;
            case 1:
                filePath2 = sdRootPath + "/DCIM/Camera"+"/Mswing.mp4";
                break;
        }

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

                holder.setFixedSize(mPlayer2.getVideoWidth(), mPlayer2.getVideoHeight());
                Log.d("check", "사이즈 : "+mPlayer2.getVideoWidth()+", "+mPlayer2.getVideoHeight());
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

    public void loadVideoSource2() {

        try {

            //error 1, 2147483648 나는 코드
            //mPlayer.setDataSource(filePath);

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
                }
            });

            mPlayer2.prepareAsync();

        } catch (IOException e) {
            return;

        }

    }

    public void onPrepared(MediaPlayer mp) {

    }


    public void surfaceCreated(SurfaceHolder holder) {
        //mSh에 문제가 있어 두번째 holder에 setDisplay가 안된다.
        //holder인자를 사용하면 두가지 다 같은 영상이 적용된다.
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
