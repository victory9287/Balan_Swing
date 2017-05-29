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

    // TrainingHeader
    public static final String TABLE_T_HEADER = "trainingHeaders";
    public static final String T_HEADER_SEQ = "trainingSEQ";
    public static final String T_HEADER_FLG = "trainingFLG";
    public static final String T_HEADER_YMD = "trainingYMD";
    public static final String T_HEADER_TIME = "trainingTime";
    public static final String T_HEADER_CLUB_NUM = "clubNumber";

    // TrainingDetail
    public static final String TABLE_T_DETAIL = "trainingDetails";
    // public static final String T_HEADER_SEQ = "trainingSEQ";
    public static final String T_DETAIL_SEQ = "detailSEQ";
    public static final String T_DETAIL_REGTIME = "REGTime";
    public static final String T_DETAIL_R_WEIGHT = "rightWeight";
    public static final String T_DETAIL_L_WEIGHT = "leftWeight";

    // TrainingVideoHist
    public static final String TABLE_T_VIDEOHIST = "trainingVideoHists";
    // public static final String T_HEADER_SEQ = "trainingSEQ";
    public static final String T_VIDEOHIST_FILE_PATH = "filePath";
    public static final String T_VIDEOHIST_FILE_NAME = "fileName";

    // ProDataHeader
    public static final String TABLE_P_HEADER = "proDataHeaders";
    public static final String P_HEADER_ID = "proID";
    public static final String P_HEADER_NAME = "proName";
    public static final String P_HEADER_CLUB_NUM = "clubNumber";

    // ProDataDetail
    public static final String TABLE_P_DETAIL = "proDataDetails";
    // public static final String P_HEADER_ID = "proID";
    public static final String P_DETAIL_SEQ = "detailSEQ";
    public static final String P_DETAIL_R_WEIGHT = "rightWeight";
    public static final String P_DETAIL_L_WEIGHT = "leftWeight";

    // ProVideoHist
    public static final String TABLE_P_VIDEOHIST = "trainingVideoHists";
    // public static final String P_HEADER_ID = "proID";
    public static final String P_VIDEOHIST_FILE_PATH = "filePath";
    public static final String P_VIDEOHIST_FILE_NAME = "fileName";

    //CommonCode
    public static final String TABLE_COMMON_CODE = "commonCodes";
    public static final String COMMON_DIV_CODE = "divCode";
    public static final String COMMON_TASK_CODE = "taskCode";

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

        //TrainingHeader
        String CREATE_T_HEADER = "create table if not exists "+TABLE_T_HEADER+"("
                +T_HEADER_SEQ+" text primary key,"
                +T_HEADER_FLG+" int,"
                +T_HEADER_YMD+" text,"
                +T_HEADER_TIME+" text,"
                +T_HEADER_CLUB_NUM+" integer"
                + ")";
        db.execSQL(CREATE_T_HEADER);

        //TrainingDetail
        String CREATE_T_DETAIL = "create table if not exists "+TABLE_T_DETAIL+"("
                +T_HEADER_SEQ+" text primary key,"
                +T_DETAIL_SEQ+" int primary key,"
                +T_DETAIL_REGTIME+" text,"
                +T_DETAIL_R_WEIGHT+" int,"
                +T_DETAIL_L_WEIGHT+" int"
                +")";
        db.execSQL(CREATE_T_DETAIL);

        //TrainingVideoHist
        String CREATE_T_VIDEOHIST = "create table if not exists "+TABLE_T_VIDEOHIST+"("
                +T_HEADER_SEQ+" text primary key,"
                +T_VIDEOHIST_FILE_PATH+" text,"
                +T_VIDEOHIST_FILE_NAME+" text"
                +")";
        db.execSQL(CREATE_T_VIDEOHIST);

        //ProDataHeader
        String CREATE_P_HEADER = "create table if not exists "+TABLE_P_HEADER+"("
                +P_HEADER_ID+" text primary key,"
                +P_HEADER_NAME+" text,"
                +P_HEADER_CLUB_NUM+" integer"
                +")";
        db.execSQL(CREATE_P_HEADER);

        //ProDataDetail
        String CREATE_P_DETAIL = "create table if not exists "+TABLE_P_DETAIL+"("
                +P_HEADER_ID+" text primary key,"
                +P_DETAIL_SEQ+" text primary key,"
                +P_DETAIL_R_WEIGHT+" integer,"
                +P_DETAIL_L_WEIGHT+" integer"
                +")";
        db.execSQL(CREATE_P_DETAIL);

        //ProVideoHist
        String CREATE_P_VIDEOHIST = "create table if not exists "+TABLE_P_VIDEOHIST+"("
                +P_HEADER_ID+" text primary key,"
                +P_VIDEOHIST_FILE_PATH+" text,"
                +P_VIDEOHIST_FILE_NAME+" text"
                +")";
        db.execSQL(CREATE_P_VIDEOHIST);

        //CommonCode
        String CREATE_COMMON_CODE = "create table if not exists "+TABLE_COMMON_CODE+"("
                +COMMON_DIV_CODE+" integer primary key,"
                +COMMON_TASK_CODE+" integer primary key"
                +")";
        db.execSQL(CREATE_COMMON_CODE);

        Log.d("createTable", "call");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Account
        db.execSQL("drop table if exists"+TABLE_ACCOUNT);
        db.execSQL("drop table if exists"+TABLE_T_HEADER);
        db.execSQL("drop table if exists"+TABLE_T_DETAIL);
        db.execSQL("drop table if exists"+TABLE_T_VIDEOHIST);
        db.execSQL("drop table if exists"+TABLE_P_HEADER);
        db.execSQL("drop table if exists"+TABLE_P_DETAIL);
        db.execSQL("drop table if exists"+TABLE_P_VIDEOHIST);
        db.execSQL("drop table if exists"+TABLE_COMMON_CODE);
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

    /******************/
    /* TrainingHeader */
    /******************/
    public void addTrainingHeader(TrainingHeader header){
        ContentValues values = new ContentValues();
        values.put(T_HEADER_SEQ, header.getTrainingSEQ());
        values.put(T_HEADER_FLG, header.getTrainingFLG());
        values.put(T_HEADER_YMD, header.getTrainingYMD());
        values.put(T_HEADER_TIME, header.getTrainingTime());
        values.put(T_HEADER_CLUB_NUM, header.getClubNumber());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_T_HEADER, null, values);
        db.close();
    }
    public ArrayList<TrainingHeader> loadTrainingHeader() {
        String query = "select * from " + TABLE_T_HEADER;

        ArrayList<TrainingHeader> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TrainingHeader header = new TrainingHeader();
            header.setTrainingSEQ(cursor.getString(0));
            header.setTrainingFLG(Integer.parseInt(cursor.getString(1)));
            header.setTrainingYMD(cursor.getString(2));
            header.setTrainingTime(cursor.getString(3));
            header.setClubNumber(Integer.parseInt(cursor.getString(4)));
            arrayList.add(header);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }



    /******************/
    /* TrainingDetail */
    /******************/
    public void addTrainingDetail(TrainingDetail detail){
        ContentValues values = new ContentValues();
        values.put(T_HEADER_SEQ, detail.getTrainingSEQ());
        values.put(T_DETAIL_SEQ, detail.getDetailSEQ());
        values.put(T_DETAIL_REGTIME, detail.getREGTime());
        values.put(T_DETAIL_R_WEIGHT, detail.getRightWeight());
        values.put(T_DETAIL_L_WEIGHT, detail.getLeftWeight());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_T_DETAIL, null, values);
        db.close();
    }
    public ArrayList<TrainingDetail> loadTrainingDetail() {
        String query = "select * from " + TABLE_T_DETAIL;

        ArrayList<TrainingDetail> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TrainingDetail detail = new TrainingDetail();
            detail.setTrainingSEQ(cursor.getString(0));
            detail.setDetailSEQ(Integer.parseInt(cursor.getString(1)));
            detail.setREGTime(cursor.getString(2));
            detail.setRightWeight(Integer.parseInt(cursor.getString(3)));
            detail.setLeftWeight(Integer.parseInt(cursor.getString(4)));
            arrayList.add(detail);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    /*********************/
    /* TrainingVideoHist */
    /*********************/
    public void addTrainingVideoHist(TrainingVideoHist videoHist){
        ContentValues values = new ContentValues();
        values.put(T_HEADER_SEQ, videoHist.getTrainingSEQ());
        values.put(T_VIDEOHIST_FILE_PATH, videoHist.getFilePath());
        values.put(T_VIDEOHIST_FILE_NAME, videoHist.getFileName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_T_VIDEOHIST, null, values);
        db.close();
    }
    public ArrayList<TrainingVideoHist> loadTrainingVideoHist() {
        String query = "select * from " + TABLE_T_VIDEOHIST;

        ArrayList<TrainingVideoHist> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TrainingVideoHist videoHist = new TrainingVideoHist();
            videoHist.setTrainingSEQ(cursor.getString(0));
            videoHist.setFilePath(cursor.getString(1));
            videoHist.setFileName(cursor.getString(2));
            arrayList.add(videoHist);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    /******************/
    /* ProDataHeader */
    /******************/
    public void addProDataHeader(ProDataHeader header){
        ContentValues values = new ContentValues();
        values.put(P_HEADER_ID, header.getProID());
        values.put(P_HEADER_NAME, header.getProName());
        values.put(P_HEADER_CLUB_NUM, header.getClubNumber());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_P_HEADER, null, values);
        db.close();
    }
    public ArrayList<ProDataHeader> loadProDataHeader() {
        String query = "select * from " + TABLE_P_HEADER;

        ArrayList<ProDataHeader> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProDataHeader header = new ProDataHeader();
            header.setProID(cursor.getString(0));
            header.setProName(cursor.getString(1));
            header.setClubNumber(Integer.parseInt(cursor.getString(2)));
            arrayList.add(header);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    /******************/
    /* ProDataDetail */
    /******************/
    public void addProDataDetail(ProDataDetail detail){
        ContentValues values = new ContentValues();
        values.put(P_HEADER_ID, detail.getProID());
        values.put(P_DETAIL_SEQ, detail.getDetailSEQ());
        values.put(P_DETAIL_R_WEIGHT, detail.getRightWeight());
        values.put(P_DETAIL_L_WEIGHT, detail.getLeftWeight());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_P_DETAIL, null, values);
        db.close();
    }
    public ArrayList<ProDataDetail> loadProDataDetail() {
        String query = "select * from " + TABLE_P_DETAIL;

        ArrayList<ProDataDetail> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProDataDetail detail = new ProDataDetail();
            detail.setProID(cursor.getString(0));
            detail.setDetailSEQ(Integer.parseInt(cursor.getString(1)));
            detail.setRightWeight(Integer.parseInt(cursor.getString(2)));
            detail.setLeftWeight(Integer.parseInt(cursor.getString(3)));
            arrayList.add(detail);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    /********************/
    /* ProDataVideoHist */
    /********************/
    public void addProVideoHist(ProVideoHist videoHist){
        ContentValues values = new ContentValues();
        values.put(P_HEADER_ID, videoHist.getProID());
        values.put(T_VIDEOHIST_FILE_PATH, videoHist.getFilePath());
        values.put(T_VIDEOHIST_FILE_NAME, videoHist.getFileName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_P_VIDEOHIST, null, values);
        db.close();
    }
    public ArrayList<ProVideoHist> loadProVideoHist() {
        String query = "select * from " + TABLE_P_VIDEOHIST;

        ArrayList<ProVideoHist> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProVideoHist videoHist = new ProVideoHist();
            videoHist.setProID(cursor.getString(0));
            videoHist.setFilePath(cursor.getString(1));
            videoHist.setFileName(cursor.getString(2));
            arrayList.add(videoHist);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    /**************/
    /* CommonCode */
    /**************/
    public void addCommonCode(CommonCode code){
        ContentValues values = new ContentValues();
        values.put(COMMON_DIV_CODE, code.getDivCode());
        values.put(COMMON_TASK_CODE, code.getTaskCode());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_COMMON_CODE, null, values);
        db.close();
    }
    public ArrayList<CommonCode> loadCommonCode() {
        String query = "select * from " + TABLE_COMMON_CODE;

        ArrayList<CommonCode> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CommonCode code = new CommonCode();
            code.setDivCode(Integer.parseInt(cursor.getString(0)));
            code.setTaskCode(Integer.parseInt(cursor.getString(1)));
            arrayList.add(code);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }
}
