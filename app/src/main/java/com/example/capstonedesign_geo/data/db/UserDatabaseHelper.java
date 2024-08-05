package com.example.capstonedesign_geo.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserInfo.db";
    private static final int DATABASE_VERSION = 1;

    // 사용자 정보를 저장할 테이블 및 칼럼 정의
    public static final String TABLE_USER = "UserInfo";
    public static final String COLUMN_USERID = "user_id";
    public static final String COLUMN_ANDROIDID = "android_id";
    public static final String COLUMN_NICKNAME = "nickname";
    public static final String COLUMN_USER_TYPE = "user_type";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_HAS_PET = "has_pet";
    public static final String COLUMN_FAVORITE_TAGS = "favorite_tags";

    // 테이블 생성
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_USERID + " TEXT," +
            COLUMN_ANDROIDID + "TEXT," +
            COLUMN_NICKNAME + " TEXT, " +
            COLUMN_USER_TYPE + " INTEGER, " +
            COLUMN_AGE + " INTEGER, " +
            COLUMN_LOCATION + " TEXT, " +
            COLUMN_HAS_PET + " INTEGER, " +
            COLUMN_FAVORITE_TAGS + " TEXT);";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);   // 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);   // 기존 테이블 삭제
        onCreate(db);   // 새 테이블 생성
    }
}


