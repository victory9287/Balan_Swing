package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.victory.balan_swing.SignupActivity.font;

public class CompareActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    SharedPreferences pref;
    int lang, sample;

    int[] profileID = {R.drawable.profile_m, R.drawable.profile_w, R.drawable.profile_m};
    String[] select_sample;

    SurfaceHolder mSh;
    String sdRootPath;
    MediaPlayer mPlayer;
    MediaPlayer mPlayer2;

    boolean StartNStop = true;
    boolean mFirst = true;

    PlaybackParams params;
    BarChart chart;

    ArrayList<String> BarEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;
    int A = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        init();
        SurfaceView sv[] = new SurfaceView[2];
        sv[0]= (SurfaceView)findViewById(R.id.partnerVideo);
        sv[1] = (SurfaceView)findViewById(R.id.myVideo);

        mSh = sv[0].getHolder();
        mSh.addCallback(this);


        BarEntryLabels = new ArrayList<String>();
        AddvaluesToBarEntryLabels();

        BarThread bar_thread = new BarThread();
        //thread.setDaemon(true);
        bar_thread.start();

    }

    class BarThread extends Thread{
        @Override

            public void run(){
            while(A<200){
                try{
                    handler.sendMessage(handler.obtainMessage());
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                A++;
            }
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
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
        compare_name.setTypeface(font);
        compare_name.setText(select_sample[sample+lang*3]);

        ImageView compare_profile = (ImageView) findViewById(R.id.compare_profile);
        compare_profile.setImageResource(profileID[sample]);
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnCompareBack:
                deletePlayer();
                intent = new Intent(CompareActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnDetail:
                deletePlayer();
                intent = new Intent(CompareActivity.this, DetailActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSetting:
                deletePlayer();
                intent = new Intent(CompareActivity.this, SelectActivity.class);
                startActivity(intent);
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

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mPlayer.start();
                }
            });
            mPlayer.prepareAsync();
//            mPlayer2.prepareAsync();


        } catch (IOException e) {

            return;

        }

    }


    public void onPrepared(MediaPlayer mp) {

    }


    public void surfaceCreated(SurfaceHolder holder) {

        loadVideoSource();

        mSh.setFixedSize(mPlayer.getVideoWidth(), mPlayer.getVideoHeight());
        mPlayer.setDisplay(mSh);

//        b_mSh.setFixedSize(mPlayer2.getVideoWidth(), mPlayer2.getVideoHeight());
//        mPlayer2.setDisplay(b_mSh);
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
}

