package com.example.victory.balan_swing;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ClubActivity extends AppCompatActivity {

    TextView tvClubTitle;
    Spinner clubSpinner;
    String selectedClub;
    TextView tvStart;

    String[] title, start, club;
    ArrayList<String> clubList;

    SharedPreferences pref;
    int lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        init();
    }

    public void init() {

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);
        switch (lang){
            case 0:
                club = getResources().getStringArray(R.array.club_spinner_kor);
                break;
            case 1:
                club = getResources().getStringArray(R.array.club_spinner_eng);
                break;
            case 2:
                club = getResources().getStringArray(R.array.club_spinner_jpn);
                break;
            case 3:
                club = getResources().getStringArray(R.array.club_spinner_zho);
                break;
        }
        clubList = new ArrayList<>();
        for (int i = 0; i < club.length; i++){
            clubList.add(club[i]);
        }

        title = getResources().getStringArray(R.array.club_title);
        start = getResources().getStringArray(R.array.select_start);

        tvClubTitle = (TextView) findViewById(R.id.club_title);
        tvClubTitle.setText(title[lang]);
        clubSpinner = (Spinner) findViewById(R.id.clubSpinner);
        tvStart = (TextView) findViewById(R.id.club_start);
        tvStart.setText(start[lang]);
        clubSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clubList));
        clubSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClub = parent.getItemAtPosition(position).toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("club", position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedClub = club[0];
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("club", 0);
                editor.commit();
            }
        });
    }

    public void btnClick(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
