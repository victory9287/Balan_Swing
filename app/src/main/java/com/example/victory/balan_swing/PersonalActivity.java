package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.example.victory.balan_swing.SignupActivity.font;

public class PersonalActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    BarChart barChart;
    ArrayList<String> dates;
    Random random;
    ArrayList<BarEntry> barEntries;

    SurfaceHolder mSh;
    String sdRootPath;
    MediaPlayer mPlayer;
    String filePath;

    SurfaceView sv2;
    SurfaceHolder mSh2;
    MediaPlayer mPlayer2;
    String filePath2;

    String recordedPath ="";

    boolean StartNStop = true;
    boolean mFirst = true;

    PlaybackParams params;

    SharedPreferences pref;
    int lang, club;

    Button btn1, btn2, btn3;

    static final int REQUEST_VIDEO_CAPTURE = 1;

    BarChart chart;

    ArrayList<String> BarEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;
    int A = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        Log.d("check", "onCreate");

        btn1 = (Button) findViewById(R.id.buttonSlow);
        btn2 = (Button) findViewById(R.id.buttonReturn);
        btn3 = (Button) findViewById(R.id.buttonFast);
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

        BarEntryLabels = new ArrayList<String>();

        AddvaluesToBarEntryLabels();


        BarThread bar_thread = new BarThread();
        //thread.setDaemon(true);
        bar_thread.start();

    }

    class BarThread extends Thread {
        @Override

        public void run() {
            while (A < 200) {
                try {
                    handler.sendMessage(handler.obtainMessage());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                A++;
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateThread1();
            chart.invalidate();
        }
    };


    private void updateThread1(){
        ArrayList<BarEntry> BARENTRY = new ArrayList<>();
        chart = (BarChart) findViewById(R.id.barchart);

        Bardataset = new BarDataSet(BARENTRY, "FOOT");
        BARDATA = new BarData(BarEntryLabels, Bardataset);

        //BarchartDesign
        chart.setDoubleTapToZoomEnabled(false);
        chart.setTouchEnabled(false);

        chart.animateY(1000);
        chart.setMaxVisibleValueCount(100);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setTextSize(10f);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMaxValue(0f);
        yAxis.setAxisMinValue(-100f);

        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);

        BARENTRY.add(new BarEntry(-(float)(Math.random()*100.0),0));
        BARENTRY.add(new BarEntry(-(float)(Math.random()*100.0),1));

        chart.setData(BARDATA);

        Log.d("check", "aaa");
    }
    public void AddvaluesToBarEntryLabels(){
        BarEntryLabels.add("LEFT");
        BarEntryLabels.add("RIGHT");
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnPersonalBack:
                deletePlayer();
                intent = new Intent(PersonalActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnCamera:
                intent = new Intent(this, CameraActivity.class);
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
                break;
        }
    }

    public void loadVideoSource() {

        sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        //자기꺼 주석 풀어서 쓰기!

        //String filePath = sdRootPath+"/DCIM/Camera"+"/20170426_162440.mp4"; //영서 오빠 영상
        //String filePath = sdRootPath+"/DCIM/Camera"+"/20170314_225610.mp4"; // 종현이 영상
        filePath = sdRootPath + "/DCIM/Camera"+"/Mswing.mp4"; // 넥서스 영상
        if (!recordedPath.equals("")) {
            Log.d("check", "filePath:"+filePath);
            filePath = recordedPath;
        }

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

                    mPlayer2 = new MediaPlayer();
                    mPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {

                        }
                    });
                    if (!recordedPath.equals("")) {
                        filePath2 = recordedPath;
                        Log.d("check", "filePath2:"+filePath2);
                    }
                    Log.d("check", filePath2);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            recordedPath = data.getStringExtra("path");
            Log.d("check", recordedPath);
        }
    }
}
