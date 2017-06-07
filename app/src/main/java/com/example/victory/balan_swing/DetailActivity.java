package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
    static int detail_Time[]; //= {8000, 27000, 35000, 53000};
    static int detail_Time_pro[] = {2000, 30667, 46000, 65000};

    BarChart chart;

    ArrayList<String> BarEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;
    int A = 0;

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();


        BarEntryLabels = new ArrayList<String>();
        AddvaluesToBarEntryLabels();

        BarThread bar_thread = new BarThread();
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
            case R.id.saveTime:
                if(thread!=null){
                }
                Intent syncIntent = new Intent(this, SyncActivity.class);
                startActivity(syncIntent);
                break;
        }
    }
    public void clickBtn(int num){
        detail_Time[num] = mPlayer[0].getCurrentPosition(); //1000으로 나누어줘야 초단위
        step[num].setAlpha(1.0f);
        step[num].setBackgroundColor(Color.BLACK);
        step[num].setTextColor(Color.WHITE);


    }
    public void loadVideoSource() {

        sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        String filePath[] = {sdRootPath + "/DCIM/Camera"+"/newSwing.mp4",
                sdRootPath + "/DCIM/Camera"+"/newMswing.mp4"};// 넥서스 영상

        try {

            mPlayer = new MediaPlayer[2];

            FileInputStream fileInputStream[] = new FileInputStream[2];

            for(int i=0;i<2;i++){
                mPlayer[i] = new MediaPlayer();
                fileInputStream[i] = new FileInputStream(filePath[i]);

                mPlayer[i].setDataSource(fileInputStream[i].getFD());
                fileInputStream[i].close();



                mPlayer[i].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
            }

            mPlayer[0].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    params = mediaPlayer.getPlaybackParams();

                    mediaPlayer.setPlaybackParams(params.setSpeed(2.5f));
                    mediaPlayer.start();
                }
            });

            mPlayer[1].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    params = mediaPlayer.getPlaybackParams();
                    mediaPlayer.setPlaybackParams(params.setSpeed(1.8f));
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

        if(thread==null){

            holder.setFixedSize(mPlayer[0].getVideoWidth(), mPlayer[0].getVideoHeight());
            mPlayer[0].setDisplay(holder);
            thread = new Thread();
            thread.start();
        }
        else{
            holder.setFixedSize(mPlayer[1].getVideoWidth(), mPlayer[1].getVideoHeight());
            mPlayer[1].setDisplay(holder);
        }

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
