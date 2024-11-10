package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.PreferenceUtils;

public class SettingActivity extends AppCompatActivity implements ConfirmDialogInterface {

    private AudioManager audioManager;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // AudioManager 초기화
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Vibrator 초기화
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            vibrator = ((android.os.VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE)).getDefaultVibrator();
        } else {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }

        // 소리 조절 SeekBar 설정
        SeekBar volumeSeekBar = findViewById(R.id.seekBar_volume);
        setupVolumeSeekBar(volumeSeekBar);

        // 진동 세기 조절 SeekBar 설정
        SeekBar vibrationSeekBar = findViewById(R.id.seekBar_vibration);
        setupVibrationSeekBar(vibrationSeekBar);

        // 초기화 버튼 클릭 이벤트 처리
        TextView btnResetPreferences = findViewById(R.id.btnResetPreferences);
        btnResetPreferences.setOnClickListener(v -> showResetConfirmationDialog());
    }

    // 커스텀 다이얼로그 호출
    private void showResetConfirmationDialog() {
        CustomDialog dialog = CustomDialog.newInstance(
                "설정을 초기화 하시겠습니까?",
                "이 작업은 되돌릴 수 없습니다."
        );
        dialog.setConfirmDialogInterface(this);
        dialog.show(getFragmentManager(), "CustomDialog");
    }

    // 다이얼로그에서 확인 버튼 클릭 시 호출
    @Override
    public void onConfirmClick(String item) {
        resetPreferences();
    }

    private void resetPreferences() {
        // SharedPreferences 초기화
        PreferenceUtils.clearPreferences(this);
        Toast.makeText(this, "설정이 초기화되었습니다.", Toast.LENGTH_SHORT).show();

        // UserRegistration 액티비티로 이동
        Intent intent = new Intent(SettingActivity.this, UserRegistration.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    // 소리 조절 SeekBar 설정 함수
    private void setupVolumeSeekBar(SeekBar volumeSeekBar) {
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    // 진동 세기 조절 SeekBar 설정 함수
    private void setupVibrationSeekBar(SeekBar vibrationSeekBar) {
        vibrationSeekBar.setMax(255);
        vibrationSeekBar.setProgress(128);

        vibrationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setVibrationStrength(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    // 진동 세기 설정 함수
    private void setVibrationStrength(int strength) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(100, strength);
            vibrator.vibrate(vibrationEffect);
        } else {
            vibrator.vibrate(100);
        }
    }
}
