package com.example.victory.balan_swing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static com.example.victory.balan_swing.SignupActivity.font;

public class MenuActivity extends AppCompatActivity {
    ImageButton btnMenu1, btnMenu2, btnMenu3;
    TextView tvTitle, tvCalendar, tvPersonal, tvCompare, tvLogout, tvName;

    SharedPreferences pref;
    int sample, lang;

    String[] title, calendar, personal, compare, logout, clubTitle, clubList, set, cancel;
    String account;
    int club;

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    public void init() {
        font = Typeface.createFromAsset(getAssets(), "font.ttf");
        btnMenu1 = (ImageButton) findViewById(R.id.btnMenu1);
        btnMenu2 = (ImageButton) findViewById(R.id.btnMenu2);
        btnMenu3 = (ImageButton) findViewById(R.id.btnMenu3);
        tvTitle = (TextView) findViewById(R.id.menu_title);
        tvCalendar = (TextView) findViewById(R.id.menu_calendar);
        tvPersonal = (TextView) findViewById(R.id.menu_personal);
        tvCompare = (TextView) findViewById(R.id.menu_compare);
        tvLogout = (TextView) findViewById(R.id.menu_logout);
        tvName = (TextView) findViewById(R.id.accountName);
        tvCalendar.setTypeface(font);
        tvTitle.setTypeface(font);
        tvPersonal.setTypeface(font);
        tvCompare.setTypeface(font);
        tvLogout.setTypeface(font);
        tvName.setTypeface(font);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        account = pref.getString("account", "");
        lang = pref.getInt("language", 0);
        sample = pref.getInt("sample", -1);

        title = getResources().getStringArray(R.array.menu_title);
        calendar = getResources().getStringArray(R.array.menu_calendar);
        personal = getResources().getStringArray(R.array.menu_personal);
        compare = getResources().getStringArray(R.array.menu_compare);
        logout = getResources().getStringArray(R.array.menu_logout);
        clubTitle = getResources().getStringArray(R.array.club_title);
        switch (lang) {
            case 0:
                clubList = getResources().getStringArray(R.array.club_spinner_kor);
                break;
            case 1:
                clubList = getResources().getStringArray(R.array.club_spinner_eng);
                break;
            case 2:
                clubList = getResources().getStringArray(R.array.club_spinner_jpn);
                break;
            case 3:
                clubList = getResources().getStringArray(R.array.club_spinner_zho);
                break;
        }
        set = getResources().getStringArray(R.array.dialog_set);
        cancel = getResources().getStringArray(R.array.dialog_cancel);

        tvTitle.setText(title[lang]);
        tvCalendar.setText(calendar[lang]);
        tvPersonal.setText(personal[lang]);
        tvCompare.setText(compare[lang]);
        tvLogout.setText(logout[lang]);

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
                selectClub(true);
                break;
            case R.id.btnMenu3:
                selectClub(false);
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

    public void selectClub(final boolean flag){
        club = 0;

        final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_club, null);

        RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.langCheck);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.club1: club = 0; break;
                    case R.id.club2: club = 1; break;
                    case R.id.club3: club = 2; break;
                    case R.id.club4: club = 3; break;
                    case R.id.club5: club = 4; break;
                }
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(clubTitle[lang]);
        alert.setView(dialogView);
        alert.setPositiveButton(set[lang], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("club", club);
                editor.commit();
                Intent intent;
                if (flag)
                    intent = new Intent(getApplicationContext(), PersonalActivity.class);
                else {
                    if (sample == -1)
                        intent = new Intent(getApplicationContext(), SelectActivity.class);
                    else
                        intent = new Intent(getApplicationContext(), CompareActivity.class);
                }
                startActivity(intent);
            }
        });
        alert.setNegativeButton(cancel[lang], null);

        AlertDialog dialog = alert.show();
        dialog.show();
    }
}
