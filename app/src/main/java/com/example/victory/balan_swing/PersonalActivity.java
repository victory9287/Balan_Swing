package com.example.victory.balan_swing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
    }

    public void btnClick(View view) {
        switch (view.getId())
        {
            case R.id.btnPersonalBack:
                Intent intent = new Intent(PersonalActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
