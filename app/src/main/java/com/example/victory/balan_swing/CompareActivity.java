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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CompareActivity extends AppCompatActivity {

    SharedPreferences pref;
    int lang, sample;

    int[] profileID = {R.drawable.profile_m, R.drawable.profile_w, R.drawable.profile_m};
    String[] select_sample;

    private LinearLayout mPDRField;
    MYView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        init();
        mView = new MYView(this);
        mPDRField = (LinearLayout)findViewById(R.id.personalGraphView);
        mPDRField.addView(mView);
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
        compare_name.setText(select_sample[sample+lang*3]);

        ImageView compare_profile = (ImageView) findViewById(R.id.compare_profile);
        compare_profile.setImageResource(profileID[sample]);
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnCompareBack:
                intent = new Intent(CompareActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnDetail:
                intent = new Intent(CompareActivity.this, DetailActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSetting:
                intent = new Intent(CompareActivity.this, SelectActivity.class);
                startActivity(intent);
                // 설정 바꾸면 finish
                // 아니면 그대로
        }
    }
    class MYView extends View {
        int width;
        int height;
        int w10;
        int h10;

        public MYView(Context context){ super(context); }

        public void onSizeChanged(int w, int h, int oldw, int oldh){
            super.onSizeChanged(w,h,oldw,oldh);
            width = w;
            height = h;
            w10 = width/10;
            h10 = height/10;
        }//size조절을 위한 초기화
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
            //사각틀

            canvas.drawRect(basex*2, basey*3, basex*4, basey*(int)LF, greenPaint);
            canvas.drawText("  왼발", basex*2, basey*2, linePaint);

            canvas.drawRect(basex*6, basey*3, basex*8, basey*(int)RF, greenPaint);
            canvas.drawText(" 오른발", basex*6, basey*2, linePaint);

            canvas.drawLine(basex*3, basey*(int)LF, basex*7, basey*(int)RF, blackPaint);
            canvas.drawCircle(basex*3,basey*(int)LF, basex/5,blackPaint);
            canvas.drawCircle(basex*7,basey*(int)RF, basex/5,blackPaint);
            //막대 연결선.

        }
    }
}

