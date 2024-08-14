package com.example.capstonedesign_geo.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.capstonedesign_geo.data.Model.UserInfo;
import com.example.capstonedesign_geo.data.db.UserDatabaseHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// 데이터베이스와 애플리케이션 간 데이터 작업을 중개
// 사용자 정보를 생성, 읽기, 업데이트, 삭제 작업 수행
// UserDatabaseHelper 클래스를 사용하여 데이터베이스에 접근
public class UserRepository {
    private UserDatabaseHelper dbHelper;

    public UserRepository(Context context){
        dbHelper = new UserDatabaseHelper(context);
    }

    // 사용자 정보를 데이터베이스에 저장
    public void saveUser(UserInfo user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserDatabaseHelper.COLUMN_ANDROIDID, user.getAndroidId());
        values.put(UserDatabaseHelper.COLUMN_NICKNAME, user.getNickname());
        values.put(UserDatabaseHelper.COLUMN_USER_TYPE, user.UserType() ? 1:0);
        values.put(UserDatabaseHelper.COLUMN_AGE, user.getAge());
        values.put(UserDatabaseHelper.COLUMN_LOCATION, user.getLocation());
        values.put(UserDatabaseHelper.COLUMN_FAVORITE_TAGS, TextUtils.join(",", user.getFavoriteTags()));

        db.insert(UserDatabaseHelper.TABLE_USER, null, values);
        db.close();
    }

    // 사용자 정보를 데이터베이스에서 가져오는 메서드
    public UserInfo getUser(String userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(UserDatabaseHelper.TABLE_USER,
                null,
                UserDatabaseHelper.COLUMN_USERID + "=?",
                new String[]{userId},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            // 각 칼럼의 인덱스를 얻음
            int androidIdIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_ANDROIDID);
            int nicknameIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_NICKNAME);
            int userTypeIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_USER_TYPE);
            int ageIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_AGE);
            int locationIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_LOCATION);
            int favoriteTagsIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_FAVORITE_TAGS);

            // 각 칼럼의 값을 가져옴
            String androidId = cursor.getString(androidIdIndex);
            String nicknameValue = cursor.getString(nicknameIndex);
            boolean userType = cursor.getInt(userTypeIndex) > 0;
            int age = cursor.getInt(ageIndex);
            String location = cursor.getString(locationIndex);
            Set<String> favoriteTags = new HashSet<>(Arrays.asList(cursor.getString(favoriteTagsIndex).split(",")));

            UserInfo user = new UserInfo(userId, androidId, nicknameValue, userType, age, location, favoriteTags);
            cursor.close();
            return user;
        }

        if (cursor != null) {
            cursor.close();
        }

        return null;
    }

    // 사용자 정보 업데이트
    public void updateUser(UserInfo user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDatabaseHelper.COLUMN_ANDROIDID, user.getAndroidId());
        values.put(UserDatabaseHelper.COLUMN_NICKNAME, user.getNickname());
        values.put(UserDatabaseHelper.COLUMN_USER_TYPE, user.UserType() ? 1:0);
        values.put(UserDatabaseHelper.COLUMN_AGE, user.getAge());
        values.put(UserDatabaseHelper.COLUMN_LOCATION, user.getLocation());
        values.put(UserDatabaseHelper.COLUMN_FAVORITE_TAGS, TextUtils.join(",", user.getFavoriteTags()));

        String selection = UserDatabaseHelper.COLUMN_NICKNAME + " = ?";
        String[] selectionArgs = {String.valueOf(user.getUserId())};

        db.update(UserDatabaseHelper.TABLE_USER, values, selection, selectionArgs);
        db.close();
    }
}
