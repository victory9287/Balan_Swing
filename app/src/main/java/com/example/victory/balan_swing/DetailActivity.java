package com.example.victory.balan_swing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class DetailActivity extends AppCompatActivity {

    SharedPreferences pref;
    int lang, sample;

    String[] detail_step;
    int[] detailID = {
            R.id.btnDetail1, R.id.btnDetail2, R.id.btnDetail3, R.id.btnDetail4
    };
    private LinearLayout mPDRField;

    int drawPercent = 0;
    MYView mView;

    double LF = 60.5;
    double RF = 39.5;

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
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);
        sample = pref.getInt("sample", -1);

        detail_step = getResources().getStringArray(R.array.detail_step);
        Button[] step = new Button[4];
        for (int i = 0; i < 4; i++) {
            step[i] = (Button) findViewById(detailID[i]);
            step[i].setText(detail_step[i+(lang*4)]);
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
                // compareActivity 삭제
                finish();
                break;
            case R.id.btnDetail1:
                break;
            case R.id.btnDetail2:
                break;
            case R.id.btnDetail3:
                break;
            case R.id.btnDetail4:
                break;


        }
    }
    class MYView extends View{
        int width;
        int height;
        int w10;
        int h10;

        public MYView(Context context){
            super(context);
        }

        public void onSizeChanged(int w, int h, int oldw, int oldh){
            super.onSizeChanged(w,h,oldw,oldh);
            width = w;
            height = h;
            w10 = width/10;
            h10 = height/10;
            // 모든 기기에서 같은 비율로 출력하기 위해 화면 크기를 받고 그것을 저장해둔다.
        }
        public void onDraw(Canvas canvas){
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
            canvas.drawLine(basex,basey,basex,height-basey,blackPaint);
            canvas.drawLine(basex,height-basey,width-basex,height-basey,blackPaint);
            canvas.drawLine(width-basex,height-basey,width-basex,basey,blackPaint);
            canvas.drawLine(width-basex,basey,basex,basey,blackPaint);
            canvas.drawLine(basex*2, basey*3, basex*8, basey*3, blackPaint);


            canvas.drawRect(basex*2, basey*3, basex*4, basey*(int)LF, greenPaint);
            canvas.drawText("  왼발", basex*2, basey*2, linePaint);

            canvas.drawRect(basex*6, basey*3, basex*8, basey*(int)RF, greenPaint);
            canvas.drawText(" 오른발", basex*6, basey*2, linePaint);

            canvas.drawLine(basex*3, basey*(int)LF, basex*7, basey*(int)RF, blackPaint);
            canvas.drawCircle(basex*3,basey*(int)LF, basex/5,blackPaint);
            canvas.drawCircle(basex*7,basey*(int)RF, basex/5,blackPaint);


        }

    }

}

