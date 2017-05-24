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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class DetailActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    SharedPreferences pref;
    int lang, sample;

    String[] detail_step;
    int[] detailID = {
            R.id.btnDetail1, R.id.btnDetail2, R.id.btnDetail3, R.id.btnDetail4
    };

    Button[] step;
    SurfaceHolder holder;
    String sdRootPath;
    MediaPlayer mPlayer;

    boolean StartNStop = true;
    boolean mFirst = true;

    PlaybackParams params;
    static int detail_Time[];

    private LinearLayout mPDRField;
    MYView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();

        SurfaceView sv2 = (SurfaceView) findViewById(R.id.detailVideo);
        holder = sv2.getHolder();
        holder.addCallback(this);

        mView = new MYView(this);
        mPDRField = (LinearLayout)findViewById(R.id.personalGraphView);
        mPDRField.addView(mView);
    }

    public void init() {
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);
        sample = pref.getInt("sample", -1);

        detail_step = getResources().getStringArray(R.array.detail_step);
        detail_Time = new int[4];
        step = new Button[4];
        for (int i = 0; i < 4; i++) {
            step[i] = (Button) findViewById(detailID[i]);
            step[i].setText(detail_step[i+(lang*4)]);
            step[i].setAlpha(0.5f);
            step[i].setBackgroundColor(Color.GRAY);
        }
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnDetailBack:
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
        detail_Time[num] = mPlayer.getCurrentPosition()*2/1000;
        step[num].setAlpha(1.0f);
        step[num].setBackgroundColor(Color.BLACK);
        step[num].setTextColor(Color.WHITE);

        Toast.makeText(this, detail_Time[num]+"초", Toast.LENGTH_SHORT).show();
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

            mPlayer.setDisplay(holder);

            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (StartNStop) {
                        params = mPlayer.getPlaybackParams();

                        float slow = 0.3f;
                        mPlayer.setPlaybackParams(params.setSpeed(slow));
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




        holder.setFixedSize(mPlayer.getVideoWidth(), mPlayer.getVideoHeight());

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
    class MYView extends View {
        int width;
        int height;
        int w10;
        int h10;

        public MYView(Context context) {
            super(context);
        }

        public void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            width = w;
            height = h;
            w10 = width / 10;
            h10 = height / 10;
        }

        public void onDraw(Canvas canvas) {
            Paint greenPaint = new Paint();
            greenPaint.setColor(0xff00964c);
            greenPaint.setStrokeWidth(11);

            Paint blackPaint = new Paint();
            blackPaint.setColor(Color.BLACK);
            blackPaint.setStrokeWidth(11);

            Paint linePaint = new Paint();
            linePaint.setColor(0xff404040);
            linePaint.setTextSize(50);

            int basex;
            int basey;

            double LF, RF = 0.0;
            LF = 5.0;
            RF = 8.0;


            basex = w10;
            basey = h10;
            canvas.drawLine(basex, basey, basex, height - basey, blackPaint);
            canvas.drawLine(basex, height - basey, width - basex, height - basey, blackPaint);
            canvas.drawLine(width - basex, height - basey, width - basex, basey, blackPaint);
            canvas.drawLine(width - basex, basey, basex, basey, blackPaint);
            canvas.drawLine(basex * 2, basey * 3, basex * 8, basey * 3, blackPaint);


            canvas.drawRect(basex * 2, basey * 3, basex * 4, basey * (int) LF, greenPaint);
            canvas.drawText("  왼발", basex * 2, basey * 2, linePaint);

            canvas.drawRect(basex * 6, basey * 3, basex * 8, basey * (int) RF, greenPaint);
            canvas.drawText(" 오른발", basex * 6, basey * 2, linePaint);

            canvas.drawLine(basex * 3, basey * (int) LF, basex * 7, basey * (int) RF, blackPaint);
            canvas.drawCircle(basex * 3, basey * (int) LF, basex / 5, blackPaint);
            canvas.drawCircle(basex * 7, basey * (int) RF, basex / 5, blackPaint);


        }
    }


}

