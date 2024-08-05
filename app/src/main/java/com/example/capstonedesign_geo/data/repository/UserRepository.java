package com.example.capstonedesign_geo.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.capstonedesign_geo.data.Model.UserInfo;
import com.example.capstonedesign_geo.data.db.UserDatabaseHelper;

public class UserRepository {
    private UserDatabaseHelper dbHelper;

    public UserRepository(Context context){
        dbHelper = new UserDatabaseHelper(context);
    }

    // 사용자 정보를 데이터베이스에 저장
    public void saveUser(UserInfo user){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        values.put(UserDatabaseHelper.COLUMN_ANDROIDID, user.getAndroidId());
        values.put(UserDatabaseHelper.COLUMN_NICKNAME, user.getNickname());
        values.put(UserDatabaseHelper.COLUMN_USER_TYPE, user.UserType() ? 1:0);
        values.put(UserDatabaseHelper.COLUMN_AGE, user.getAge());
        values.put(UserDatabaseHelper.COLUMN_LOCATION, user.getLocation());
        values.put(UserDatabaseHelper.COLUMN_HAS_PET, user.HasPet() ? 1:0);
        values.put(UserDatabaseHelper.COLUMN_FAVORITE_TAGS, user.getFavoriteTags());
        db.insert(UserDatabaseHelper.TABLE_USER, null, values);
    }

    // 사용자 정보를 데이터베이스에서 가져오는 메서드 (예시)
    /*public UserInfo getUser(String nickname) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(UserDatabaseHelper.TABLE_USER,
                null,
                UserDatabaseHelper.COLUMN_NICKNAME + "=?",
                new String[]{nickname},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            UserInfo user = new UserInfo(
                    cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COLUMN_USERID)),
            );
            cursor.close();
            return user;
        }

        if (cursor != null) {
            cursor.close();
        }

        return null;
    }*/

    // 사용자 정보 업데이트
    public void updateUser(UserInfo user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDatabaseHelper.COLUMN_ANDROIDID, user.getAndroidId());
        values.put(UserDatabaseHelper.COLUMN_NICKNAME, user.getNickname());
        values.put(UserDatabaseHelper.COLUMN_USER_TYPE, user.UserType() ? 1:0);
        values.put(UserDatabaseHelper.COLUMN_AGE, user.getAge());
        values.put(UserDatabaseHelper.COLUMN_LOCATION, user.getLocation());
        values.put(UserDatabaseHelper.COLUMN_HAS_PET, user.HasPet() ? 1:0);
        values.put(UserDatabaseHelper.COLUMN_FAVORITE_TAGS, user.getFavoriteTags());

        String selection = UserDatabaseHelper.COLUMN_NICKNAME + " = ?";
        String[] selectionArgs = {String.valueOf(user.getUserId())};

        db.update(UserDatabaseHelper.TABLE_USER, values, selection, selectionArgs);
    }
}
