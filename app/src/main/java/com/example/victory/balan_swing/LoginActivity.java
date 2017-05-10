package com.example.victory.balan_swing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.victory.balan_swing.R.id.btnSignup;

public class LoginActivity extends AppCompatActivity {
    Spinner accountSpinner;
    ArrayList<Account> accountList;
    String account;
    Button btnLogin;
    TextView tvSignup;
    ImageButton btnLanguage;
    ArrayAdapter<String> adapter;

    String[] select, set, cancel, login, signup;

    int[] langImg = {R.drawable.kor, R.drawable.eng, R.drawable.jpn, R.drawable.zho};
    int[] langID = {R.id.kor, R.id.eng, R.id.jpn, R.id.zho};

    SharedPreferences pref;
    int lang;

    final int REQUEST_SIGNUP = 1;

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("onCreate", "call");

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        lang = pref.getInt("language", 0);
        String check = pref.getString("account", "");
        if (check.length() > 0){
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }

        select = getResources().getStringArray(R.array.dialog_select);
        set = getResources().getStringArray(R.array.dialog_set);
        cancel = getResources().getStringArray(R.array.dialog_cancel);
        login = getResources().getStringArray(R.array.login_login);
        signup = getResources().getStringArray(R.array.login_signup);

        //디비에서 계정 리스트 받아오기
        accountList = new ArrayList();
        dbHandler = new MyDBHandler(this, null, null, 1);
        accountList = dbHandler.loadAccount();
        if (accountList.isEmpty()){
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
            finish();
        }

        btnLanguage = (ImageButton) findViewById(R.id.btnLanguage);
        tvSignup = (TextView) findViewById(R.id.login_signup);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setEnabled(false);

        accountSpinner = (Spinner) findViewById(R.id.accountSpinner);
        ArrayList<String> idList = new ArrayList<>();
        for (int i = 0; i < accountList.size(); i++){
            idList.add(accountList.get(i).getM_Id());
        }
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_entry, idList);
        accountSpinner.setAdapter(adapter);
        accountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                account = parent.getItemAtPosition(position).toString();
                if (account.length()>0){
                    btnLogin.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                account = "";
            }
        });

        if (accountList.isEmpty()){
            accountSpinner.setEnabled(false);
        }

        setLangauge();
    }

    public void setLangauge() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("language", lang);
        editor.commit();

        btnLogin.setText(login[lang]);
        tvSignup.setText(signup[lang]);

        btnLanguage.setImageResource(langImg[lang]);
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId()){
            case btnSignup:
                intent = new Intent(this, SignupActivity.class);
                //startActivityForResult(intent, REQUEST_SIGNUP);
                startActivity(intent);
                finish();
                break;
            case R.id.btnLogin:
                intent = new Intent(this, MenuActivity.class);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("account", account);
                editor.commit();
                startActivity(intent);
                finish();
                break;
            case R.id.btnLanguage:
                final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dialog_language, null);

                RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.langCheck);
                radioGroup.check(langID[lang]);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId){
                            case R.id.kor: lang=0; break;
                            case R.id.eng: lang=1; break;
                            case R.id.jpn: lang=2; break;
                            case R.id.zho: lang=3; break;
                            default: lang=0; break;
                        }
                    }
                });

                AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
                alert.setTitle(select[lang]);
                alert.setView(dialogView);
                alert.setPositiveButton(set[lang], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setLangauge();
                    }
                });
                alert.setNegativeButton(cancel[lang], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lang = pref.getInt("language", 0);
                    }
                });

                AlertDialog dialog = alert.show();
                dialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP){
            if (resultCode == RESULT_OK){
                Log.d("check", "ok");
                //String newAcc = data.getStringExtra("newAcc");
                //accountList.add(newAcc);
                adapter.notifyDataSetChanged();
                accountSpinner.setEnabled(true);
            }
        }
    }
}

