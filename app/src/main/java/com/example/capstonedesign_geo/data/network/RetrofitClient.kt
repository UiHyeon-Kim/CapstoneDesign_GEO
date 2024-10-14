package com.example.capstonedesign_geo.data.network

import com.example.capstonedesign_geo.data.model.Item
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://apis.data.go.kr/B551011/KorService1/"

    /*private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getTourApiService(): TourApiService {
        return getRetrofit().create(TourApiService::class.java)
    }*/

    val gson = GsonBuilder()
        .registerTypeAdapter(object : TypeToken<List<Item>>() {}.type, ItemListDeserializer())
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: TourApiService by lazy {
        retrofit.create(TourApiService::class.java)
    }
}