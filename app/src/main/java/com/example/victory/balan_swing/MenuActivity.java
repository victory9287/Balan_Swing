package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    ImageButton btnMenu1, btnMenu2, btnMenu3;
    TextView tvTitle, tvCalendar, tvPersonal, tvCompare, tvLogout, tvName;

    SharedPreferences pref;
    int sample, lang;

    String[] title, calendar, personal, compare, logout;
    String account;

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    public void init() {

        btnMenu1 = (ImageButton) findViewById(R.id.btnMenu1);
        btnMenu2 = (ImageButton) findViewById(R.id.btnMenu2);
        btnMenu3 = (ImageButton) findViewById(R.id.btnMenu3);
        tvTitle = (TextView) findViewById(R.id.menu_title);
        tvCalendar = (TextView) findViewById(R.id.menu_calendar);
        tvPersonal = (TextView) findViewById(R.id.menu_personal);
        tvCompare = (TextView) findViewById(R.id.menu_compare);
        tvLogout = (TextView) findViewById(R.id.menu_logout);
        tvName = (TextView) findViewById(R.id.accountName);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        account = pref.getString("account", "");
        lang = pref.getInt("language", 0);
        sample = pref.getInt("sample", -1);

        title = getResources().getStringArray(R.array.menu_title);
        calendar = getResources().getStringArray(R.array.menu_calendar);
        personal = getResources().getStringArray(R.array.menu_personal);
        compare = getResources().getStringArray(R.array.menu_compare);
        logout = getResources().getStringArray(R.array.menu_logout);

        tvTitle.setText(title[lang]);
        tvCalendar.setText(calendar[lang]);
        tvPersonal.setText(personal[lang]);
        tvCompare.setText(compare[lang]);
        tvLogout.setText(logout[lang]);

        /*Intent intent = getIntent();
        String account = intent.getStringExtra("account");
        Log.d("check", account);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userID", account);
        if (account.equals("victory9287")) {
            editor.putString("userName", "허승리");
            userName = "허승리";
        } else if (account.equals("im_j_in")) {
            editor.putString("userName", "임재인");
            userName = "임재인";
        } else if (account.equals("kangj")) {
            editor.putString("userName", "강종현");
            userName = "강종현";
        }
        editor.commit();
        tvName.setText(userName);*/
        dbHandler = new MyDBHandler(this, null, null, 1);
        if (account.length()>0){
            tvName.setText(dbHandler.returnName(account));
        }
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnMenu1:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnMenu2:
                intent = new Intent(this, PersonalActivity.class);
                startActivity(intent);
                break;
            case R.id.btnMenu3:
                if (sample == -1) {
                    intent = new Intent(this, SelectActivity.class);
                } else {
                    intent = new Intent(this, CompareActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.menu_logout:
                // 로그아웃
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("account", "");
                editor.commit();
                // (다이얼로그 띄우기)
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
