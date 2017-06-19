package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.victory.balan_swing.R.id.percent;


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
    BarChart chart;

    ArrayList<String> BarEntryLabels;
    //ArrayList<String> LineEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;
    //LineDataSet Linedataset;
    //LineData LINEDATA;

    int A = 0;

    int AAAA = 0;

    TextView percentview;

    private FrameLayout mPDRField;
    //MYView mView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        init();

        BarEntryLabels = new ArrayList<String>();
        //LineEntryLabels = new ArrayList<String>();
        AddvaluesToBarEntryLabels();
        AddvaluesToLineEntryLabels();

        BarThread bar_thread = new BarThread();
        bar_thread.start();

    }

    class BarThread extends Thread{
        @Override

        public void run(){
            while(A<199){
                try{
                    handler.sendMessage(handler.obtainMessage());
                    Thread.sleep(35);
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
            AAAA++;
            chart.invalidate();
        }
    };

    private void updateThread1(){
        ArrayList<BarEntry> BARENTRY = new ArrayList<>();
        //ArrayList<Entry> LINEENTRY = new ArrayList<>();
        chart = (BarChart) findViewById(R.id.barchart);

        String[] left = {
                "-50.0", "-50.5", "-51.0", "-51.5", "-52.0", "-52.5", "-53.0", "-53.5", "-54.0", "-54.5",
                "-55.0", "-55.5", "-56.0", "-56.5", "-57.0", "-57.5", "-58.0", "-58.5", "-59.0", "-59.5",
                "-60.0", "-60.5", "-61.0", "-61.5", "-62.0", "-62.5", "-63.0", "-63.5", "-64.0", "-64.5",
                "-65.0", "-65.5", "-66.0", "-66.5", "-67.0", "-67.5", "-68.0", "-68.5", "-69.0", "-69.5",
                "-70.0", "-70.5", "-71.0", "-71.5", "-72.0", "-72.5", "-73.0", "-73.5", "-74.0", "-74.5",
                "-75.0", "-75.5", "-76.0", "-76.5", "-77.0", "-77.5", "-78.0", "-78.5", "-79.0", "-79.5",
                "-80.0", "-80.5", "-81.0", "-81.5", "-82.0", "-82.5", "-83.0", "-83.5", "-84.0", "-84.5",
                "-85.0", "-85.5", "-86.0", "-86.5", "-87.0", "-87.5", "-88.0", "-88.5", "-89.0", "-89.5",
                "-90.0", "-90.5", "-91.0", "-91.5", "-92.0", "-92.5", "-93.0", "-93.5", "-94.0", "-94.5",
                "-95.0", "-95.5", "-96.0", "-96.5", "-97.0", "-97.5", "-98.0", "-98.5", "-99.0", "-99.5",

                "-99.0", "-98.0", "-97.0", "-96.0", "-95.0", "-94.0", "-93.0", "-92.0", "-91.0", "-90.0",
                "-89.0", "-88.0", "-87.0", "-86.0", "-85.0", "-84.0", "-83.0", "-82.0", "-81.0", "-80.0",
                "-79.0", "-78.0", "-77.0", "-76.0", "-75.0", "-74.0", "-73.0", "-72.0", "-71.0", "-70.0",
                "-69.0", "-68.0", "-67.0", "-66.0", "-65.0", "-64.0", "-63.0", "-62.0", "-61.0", "-60.0",
                "-59.0", "-58.0", "-57.0", "-56.0", "-55.0", "-54.0", "-53.0", "-52.0", "-51.0", "-50.0",
                "-49.0", "-48.0", "-47.0", "-46.0", "-45.0", "-44.0", "-43.0", "-42.0", "-41.0", "-40.0",
                "-39.0", "-38.0", "-37.0", "-36.0", "-35.0", "-34.0", "-33.0", "-32.0", "-31.0", "-30.0",
                "-29.0", "-28.0", "-27.0", "-26.0", "-25.0", "-24.0", "-23.0", "-22.0", "-21.0", "-20.0",
                "-19.0", "-18.0", "-17.0", "-16.0", "-15.0", "-14.0", "-13.0", "-12.0", "-11.0", "-10.0",
                "-9.0", "-8.0", "-7.0", "-6.0", "-5.0", "-4.0", "-3.0", "-2.0", "-1.0", "0.0",
        };
        String[] right = {
                "-50.0", "-49.5", "-49.0", "-48.5", "-47.0", "-46.5", "-46.0", "-45.5", "-45.0", "-44.5",
                "-44.0", "-43.5", "-43.0", "-42.5", "-42.0", "-41.5", "-41.0", "-40.5", "-40.0", "-39.5",
                "-30.0", "-39.5", "-39.0", "-38.5", "-37.0", "-36.5", "-36.0", "-35.5", "-35.0", "-34.5",
                "-34.0", "-33.5", "-33.0", "-32.5", "-32.0", "-31.5", "-31.0", "-30.5", "-30.0", "-29.5",
                "-20.0", "-29.5", "-29.0", "-28.5", "-27.0", "-26.5", "-26.0", "-25.5", "-25.0", "-24.5",
                "-24.0", "-23.5", "-23.0", "-22.5", "-22.0", "-21.5", "-21.0", "-20.5", "-20.0", "-19.5",
                "-10.0", "-19.5", "-19.0", "-18.5", "-17.0", "-16.5", "-16.0", "-15.5", "-15.0", "-14.5",
                "-14.0", "-13.5", "-13.0", "-12.5", "-12.0", "-11.5", "-11.0", "-10.5", "-10.0", "-9.5",
                "-9.0", "-8.5", "-8.0", "-7.5", "-7.0", "-7.5", "-7.0", "-6.5", "-6.0", "-5.5",
                "-5.0", "-4.5", "-4.0", "-3.5", "-3.0", "-2.5", "-2.0", "-1.5", "-1.0", "-0.5",

                "-1.0", "-2.0", "-3.0", "-4.0", "-5.0", "-6.0", "-7.0", "-8.0", "-9.0", "-10.0",
                "-11.0", "-12.0", "-13.0", "-14.0", "-15.0", "-16.0", "-17.0", "-18.0", "-19.0", "-20.0",
                "-21.0", "-22.0", "-23.0", "-24.0", "-25.0", "-26.0", "-27.0", "-28.0", "-29.0", "-30.0",
                "-31.0", "-32.0", "-33.0", "-34.0", "-35.0", "-36.0", "-37.0", "-38.0", "-39.0", "-40.0",
                "-41.0", "-42.0", "-43.0", "-44.0", "-45.0", "-46.0", "-47.0", "-48.0", "-49.0", "-50.0",
                "-51.0", "-52.0", "-53.0", "-54.0", "-55.0", "-56.0", "-57.0", "-58.0", "-59.0", "-60.0",
                "-61.0", "-62.0", "-63.0", "-64.0", "-65.0", "-66.0", "-67.0", "-68.0", "-69.0", "-70.0",
                "-71.0", "-72.0", "-73.0", "-74.0", "-75.0", "-76.0", "-77.0", "-78.0", "-79.0", "-80.0",
                "-81.0", "-82.0", "-83.0", "-84.0", "-85.0", "-86.0", "-87.0", "-88.0", "-89.0", "-90.0",
                "-91.0", "-92.0", "-93.0", "-94.0", "-95.0", "-96.0", "-97.0", "-98.0", "-99.0", "100.0"
        };
        String[] percent = {
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",

                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "55%", "69%",
                "99%", "90%", "91%", "88%", "30%", "45%", "47%", "51%", "88%", "69%",

        };
        Bardataset = new BarDataSet(BARENTRY, "FOOT");
        BARDATA = new BarData(BarEntryLabels, Bardataset);

        //Linedataset = new LineDataSet(LINEENTRY,"FOOT");
       // LINEDATA = new LineData(BarEntryLabels, Linedataset);

        //BarchartDesign
        chart.setDoubleTapToZoomEnabled(false);
        chart.setTouchEnabled(false);
        //Bardataset.setColor(Color.rgb(255,204,255));
        Bardataset.setColor(Color.WHITE);
        Bardataset.setBarSpacePercent(80f);

        //chart.animateY(1000);
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

        BARENTRY.add(new BarEntry(Float.parseFloat(right[AAAA]),0));
        //BARENTRY.add(new BarEntry(-(float)(Math.random()*100.0),0));
        BARENTRY.add(new BarEntry(Float.parseFloat(left[AAAA]),1));
        //BARENTRY.add(new BarEntry(-(float)(Math.random()*100.0),1));
        //LINEENTRY.add(new Entry(Float.parseFloat(right[AAAA]),0));
        //LINEENTRY.add(new Entry(Float.parseFloat(left[AAAA]),1));

        //CombinedData data = new CombinedData();
        //data.setData(BARDATA);
        //data.setData(LINEDATA);
        chart.setData(BARDATA);
        //chart.setDescription("MyChart");

        percentview.setText(percent[AAAA]);

        Log.d("check", "aaa");


    }

    public void AddvaluesToBarEntryLabels(){
        BarEntryLabels.add("LEFT");
        BarEntryLabels.add("RIGHT");
    }
    public void AddvaluesToLineEntryLabels(){

    }


    public void init() {

        percentview = (TextView)findViewById(percent);
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


        //mView = new MYView(this);
        mPDRField = (FrameLayout)findViewById(R.id.personalGraphView);
        //mPDRField.addView(mView);
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);
        sample = pref.getInt("sample", -1);

        if (sample == -1) {
            Toast.makeText(this, "no sample", Toast.LENGTH_SHORT).show();
            finish();
        }
        select_sample = getResources().getStringArray(R.array.select_sample);

//        TextView compare_name = (TextView) findViewById(R.id.compare_name);
//        compare_name.setTypeface(font);
//        compare_name.setText(select_sample[sample+lang*3]);
//
//        ImageView compare_profile = (ImageView) findViewById(R.id.compare_profile);
//        compare_profile.setImageResource(profileID[sample]);

        GraphView graphView = (GraphView) findViewById(R.id.graphView);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 50),
                new DataPoint(2, 55),
                new DataPoint(4, 60),
                new DataPoint(6, 79),
                new DataPoint(8, 83),
                new DataPoint(10, 77),
                new DataPoint(12, 60),
                new DataPoint(14, 54),
                new DataPoint(16, 42),
                new DataPoint(18, 20),
                new DataPoint(20, 13),
        });
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 50),
                new DataPoint(2, 62),
                new DataPoint(4, 73),
                new DataPoint(6, 92),
                new DataPoint(8, 70),
                new DataPoint(10, 53),
                new DataPoint(12, 45),
                new DataPoint(14, 20),
                new DataPoint(16, 10),
                new DataPoint(18, 4),
                new DataPoint(20, 0),
        });
        series2.setColor(Color.RED);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","","","","","","","","","",""});
        staticLabelsFormatter.setVerticalLabels(new String[]{"", "20", "40", "60", "80", "100" });
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graphView.addSeries(series);
        graphView.addSeries(series2);

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