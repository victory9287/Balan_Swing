package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CompareActivity extends AppCompatActivity {

    SharedPreferences pref;
    int lang, sample;

    int[] profileID = {R.drawable.profile_m, R.drawable.profile_w, R.drawable.profile_m};
    String[] select_sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        init();
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
}

