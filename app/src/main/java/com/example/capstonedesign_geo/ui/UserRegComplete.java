package com.example.capstonedesign_geo.ui;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.dao.UserDao;
import com.example.capstonedesign_geo.data.db.GeoDatabase;
import com.example.capstonedesign_geo.data.model.User;
import com.example.capstonedesign_geo.data.repository.UserRepository;
import com.example.capstonedesign_geo.data.repository.UserRepositoryImpl;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class UserRegComplete extends AppCompatActivity {

    private Button btnComplete;
    private TextView greeting;
    private String nickname;
    private UserRepository userRepository;
    private GeoDatabase db = Room.databaseBuilder(context.getApplicationContext(), GeoDatabase.class, "GeoDatabase").build();
    private final UserDao userDao = db.userDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_complete);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        btnComplete = findViewById(R.id.btnComplete);
        greeting = findViewById(R.id.tvGreeting);

        // User user = userRepository.getUserById("userId");
        db = GeoDatabase.getInstance(this);
        userRepository = new UserRepositoryImpl(db.userDao());

        new Thread(() -> {
            User user = userRepository.getUserById("userId");
            if (user != null) {
                Log.d("UserRegComplete", "User found in the database: " + user);
            } else {
                Log.e("UserRegComplete", "User not found in the database.");
            }
        }).start();

        // 설문조사 완료 환영 문구 출력
        getNickname();
        greeting.setText(nickname + getString(R.string.RegisterComplete1));

        // 모든 사용자 정보 한 번에 저장
        saveUserInfo();

        btnComplete.setOnClickListener(view -> {
            Intent intent = new Intent(UserRegComplete.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void saveUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        // SharedPreferences로 저장한 UserPreferences의 사용자 정보 가져오기
        String userId = sharedPreferences.getString("userId", null);
        String androidId = sharedPreferences.getString("androidId", null);
        String nickname = sharedPreferences.getString("nickname", null);
        boolean userType = sharedPreferences.getBoolean("userType", false);
        int age = sharedPreferences.getInt("age", 0);
        String location = sharedPreferences.getString("location", null);
        // Set<String> favoriteTags = sharedPreferences.getStringSet("favoriteTags", new HashSet<>());

        if (userId == null) {
            Log.e("saveUserInfo", "UserId is null. Cannot create User instance.");
            return;
        }

        User user = new User(userId, androidId, nickname, userType, age, location);
        userRepository.insertUser(user);

        new Thread(() -> {
            userDao.insertUser(user);
            Log.d("saveUserInfo", "User saved to database: " + user);
        }).start();

        User savedUser = userRepository.getUserById(userId);
        if (savedUser != null) {
            Log.d("saveUserInfo", "User saved: " + savedUser);
        } else {
            Log.e("saveUserInfo", "User not saved in the database.");
        }
    }

    private void getNickname() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        nickname = sharedPreferences.getString("nickname", null);
    }
}
