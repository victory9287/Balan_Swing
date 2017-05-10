package com.example.victory.balan_swing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by victory on 2017-05-08.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BalanSwingDB.db";

    // Account
    public static final String TABLE_ACCOUNT = "accounts";
    public static final String ACCOUNT_ID = "m_id";
    public static final String ACCOUNT_NAME = "m_name";
    public static final String ACCOUNT_GENDER = "m_gender";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Account
        String CREATE_ACCOUNT = "create table if not exists "+TABLE_ACCOUNT+"("
                +ACCOUNT_ID+" text primary key,"
                +ACCOUNT_NAME+" text,"
                +ACCOUNT_GENDER+" integer)";
        db.execSQL(CREATE_ACCOUNT);
        Log.d("createTable", "call");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Account
        db.execSQL("drop table if exists"+TABLE_ACCOUNT);
        onCreate(db);
    }

    //Account
    public void addAccount(Account account){
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_ID, account.getM_Id());
        values.put(ACCOUNT_NAME, account.getM_name());
        values.put(ACCOUNT_GENDER, account.getM_gender());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }
    public boolean deleteAccount(String accountID){
        boolean result = false;
        String query = "select * from "+TABLE_ACCOUNT
                +" where "+ACCOUNT_ID+"= \'"+accountID+"\'";
        Account account = new Account();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            account.setM_id(cursor.getString(0));
            db.delete(TABLE_ACCOUNT, ACCOUNT_ID+"=?",
                    new String[]{account.getM_Id()});
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }
    public ArrayList<Account> loadAccount() {
        String query = "select * from " + TABLE_ACCOUNT;

        ArrayList<Account> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Account account = new Account();
            account.setM_id(cursor.getString(0));
            account.setM_name(cursor.getString(1));
            account.setM_gender(Integer.parseInt(cursor.getString(2)));
            arrayList.add(account);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }
    public String returnName(String accountID){
        String query = "select "+ACCOUNT_NAME+" from "+TABLE_ACCOUNT+" where "+ACCOUNT_ID+"= \'"+accountID+"\'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String accountName = "";
        if (!cursor.isAfterLast()){
            accountName = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return accountName;
    }
}
