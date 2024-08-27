package com.example.capstonedesign_geo.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstonedesign_geo.R
import com.example.capstonedesign_geo.databinding.UserRegistrationBinding
import com.example.capstonedesign_geo.utility.setStatusBarColor
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

class UserRegistration : AppCompatActivity() {
    private lateinit var binding: UserRegistrationBinding; // 뷰 바인딩 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserRegistrationBinding.inflate(layoutInflater) // 뷰 바인딩 초기화
        setContentView(binding.root) // 뷰 바인딩 적용
        // 상태바 색상 변경
        setStatusBarColor(ContextCompat.getColor(this, R.color.transparent))
        // 코틀린 이벤트 리스너 - 인텐트
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, UserRegNickname::class.java)
            startActivity(intent)
        }

        val sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE) // 권한 설정 여부 저장
        val isPermission =
            sharedPreferences.getBoolean("permission_granted", false) // 권한 설정 여부 확인. 기본값 false

        // 권한 요청 결과를 처리하는 리스너
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() { // 권한이 허용된 경우
                if (!isPermission) { // 이전에 허용된 적이 없는 경우에만
                    Toast.makeText(this@UserRegistration, "권한 설정이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    // 권한이 허용된 적이 있음을 sharedPreferences에 저장
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("permission_granted", true)
                    editor.apply()
                }
            }
            override fun onPermissionDenied(deniedPermissions: List<String>) { // 권한이 거부된 경우
                Toast.makeText(this@UserRegistration, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // 권한 요청 설정
        TedPermission.create()
            .setPermissionListener(permissionListener) // 위 권한 리스너를 설정
            .setRationaleMessage("앱을 사용하려면, 접근 권한이 필요합니다.") // 권한 요청 이유 메시지
            .setDeniedMessage("권한이 거부되었습니다. 이를 다시 얻으려면, [설정] > [권한]으로 이동하세요.") // 사용자가 권한을 거부했을 때 메시지
            // 요청할 권한 명시
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,  // 정밀 위치 권한(GPS)
                Manifest.permission.ACCESS_COARSE_LOCATION // 대략적인 위치 권한(네트워크)
            )
            .check() // 설정된 내용을 바탕으로 권한을 요청
    }
}