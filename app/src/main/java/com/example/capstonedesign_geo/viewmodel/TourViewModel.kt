package com.example.capstonedesign_geo.viewmodel

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
                _tourItems.postValue(response.response.body.items.item)
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
}
