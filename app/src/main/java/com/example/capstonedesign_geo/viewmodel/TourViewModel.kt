package com.example.capstonedesign_geo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstonedesign_geo.data.model.Item
import com.example.capstonedesign_geo.data.network.RetrofitClient
import kotlinx.coroutines.launch

class TourViewModel : ViewModel() {
    private val _tourItems = MutableLiveData<List<Item>>()
    val tourItems: LiveData<List<Item>> get() = _tourItems

    fun fetchTourInfo() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getTourList(
                    MobileApp = "AppTest",
                    MobileOS = "ETC",
                    listYN = "Y",
                    arrange = "C",
                    areaCode = "32",
                    _type = "json",
                    numOfRows = 10,
                    pageNo = 1,
                    contentTypeId = "",
                    sigunguCode = "25",
                    cat1 = "",
                    cat2 = "",
                    cat3 = ""
                )
                if (response.isSuccessful && response.body() != null) {
                    // 여기에 넣어서 실제 응답을 로그로 확인
                    Log.d("Response JSON", response.body().toString())

                    // 데이터를 LiveData에 업데이트
                    _tourItems.postValue(response.body()!!.response.body.items.item)
                } else {
                    // 오류 로그 확인
                    Log.e("TourViewModel", "응답 실패 또는 데이터 없음")
                }

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        Log.d("Response JSON", response.body().toString())
                    } else {
                        Log.e("Error", "Response body is null")
                    }
                } else {
                    Log.e("Error", "Response failed with code: ${response.code()}")
                }
                Log.d("Response JSON", response.body().toString())
            } catch (e: Exception) {
                // 에러 처리
                Log.e("TourViewModel", "에러 발생: ${e.message}")
            }
        }
    }
}
