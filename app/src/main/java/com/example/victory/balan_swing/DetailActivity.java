package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
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
    MYView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
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

        mView = new MYView(this);
        mPDRField = (LinearLayout)findViewById(R.id.personalGraphView);
        mPDRField.addView(mView);
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
}

