package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.victory.balan_swing.R.id.check1;
import static com.example.victory.balan_swing.R.id.check2;
import static com.example.victory.balan_swing.R.id.check3;

public class SelectActivity extends AppCompatActivity {
    ImageView[] check;
    ImageButton btnStart;

    SharedPreferences pref;
    int lang;
    int sample;

    int[] checkID = {
            check1, check2, check3
    };
    int[] sampleID = {
            R.id.sample1, R.id.sample2, R.id.sample3
    };
    String[] select_start, select_sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        init();
    }

    public void init() {
        check = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            check[i] = (ImageView) findViewById(checkID[i]);
        }
        btnStart = (ImageButton) findViewById(R.id.btnStart);
        btnStart.setClickable(false);

        select_start = getResources().getStringArray(R.array.select_start);
        select_sample = getResources().getStringArray(R.array.select_sample);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);
        sample = pref.getInt("sample", -1);

        TextView tvStart = (TextView) findViewById(R.id.select_start);
        tvStart.setText(select_start[lang]);

        TextView tvSample[] = new TextView[3];
        for (int i = 0; i < 3; i++) {
            tvSample[i] = (TextView) findViewById(sampleID[i]);
            tvSample[i].setText(select_sample[i+(lang*3)]);
        }
        if (sample != -1) {
            check[sample].setVisibility(View.VISIBLE);
            btnStart.setClickable(true);
        }
    }

    public void btnClick(View view) { // 아무도 안선택한 경우 못 시작하게 해야함
        switch (view.getId()) {
            case R.id.profile1:
                // 샘플데이터 디비저장
                check[0].setVisibility(View.VISIBLE);
                check[1].setVisibility(View.INVISIBLE);
                check[2].setVisibility(View.INVISIBLE);
                btnStart.setClickable(true);
                sample = 0;
                break;
            case R.id.profile2:
                // 샘플데이터 디비저장
                check[0].setVisibility(View.INVISIBLE);
                check[1].setVisibility(View.VISIBLE);
                check[2].setVisibility(View.INVISIBLE);
                btnStart.setClickable(true);
                sample = 1;
                break;
            case R.id.profile3:
                // 샘플데이터 디비저장
                check[0].setVisibility(View.INVISIBLE);
                check[1].setVisibility(View.INVISIBLE);
                check[2].setVisibility(View.VISIBLE);
                btnStart.setClickable(true);
                sample = 2;
                break;
            case R.id.btnStart:
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("sample", sample);
                editor.commit();

                Intent intent = new Intent(SelectActivity.this, CompareActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnSelectBack:
                finish();
                break;
        }
    }
}