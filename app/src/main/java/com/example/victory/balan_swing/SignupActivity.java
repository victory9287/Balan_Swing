package com.example.victory.balan_swing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {
    EditText etID, etName;
    TextView tvOk;
    RadioGroup genderGroup;
    RadioButton btnMale, btnFemale;
    boolean gender;

    String[] name, ok, male, female;

    SharedPreferences pref;
    int lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    public void init() {
        etID = (EditText) findViewById(R.id.signup_id);
        etName = (EditText) findViewById(R.id.signup_name);
        tvOk = (TextView) findViewById(R.id.signup_ok);
        genderGroup = (RadioGroup) findViewById(R.id.signup_gender);
        btnMale = (RadioButton) findViewById(R.id.signup_male);
        btnFemale = (RadioButton) findViewById(R.id.signup_female);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.signup_male:
                        gender = true;
                        break;
                    case R.id.signup_female:
                        gender = false;
                        break;
                }
            }
        });

        name = getResources().getStringArray(R.array.signup_name);
        ok = getResources().getStringArray(R.array.signup_ok);
        male = getResources().getStringArray(R.array.signup_male);
        female = getResources().getStringArray(R.array.signup_female);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);

        etName.setHint(name[lang]);
        tvOk.setText(ok[lang]);
        btnMale.setText(male[lang]);
        btnFemale.setText(female[lang]);
    }

    public void btnClick(View view) {
        if (view.getId() == R.id.btnOk){
            if (etName.length()>0&&etID.length()>0){
                String id = etID.getText().toString();
                String name = etName.getText().toString();

                // 계정 디비에 저장
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("newAcc", id);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        else if (view.getId() == R.id.btnSignupBack){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
