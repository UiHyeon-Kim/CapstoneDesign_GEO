package com.example.capstonedesign_geo.ui.fragment;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaverMapRequest {
    // OkHttpClient 설정
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build();

    public static String BASE_URL = "http://3.35.170.12/"; //데이터가 담긴 서버 주소
    private static final String NAVER_MAP_BASE_URL = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/";

    private static Retrofit dataRetrofit;
    private static Retrofit naverMapRetrofit;


    public static Retrofit getClient(){
        if (dataRetrofit == null) {
            dataRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return dataRetrofit;
    }



        // 네이버 지도 API 전용 클라이언트 생성 메서드
        public static Retrofit getNaverMapClient() {
            if (naverMapRetrofit == null) {
                naverMapRetrofit = new Retrofit.Builder()
                        .baseUrl(NAVER_MAP_BASE_URL) // 슬래시 추가됨
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return naverMapRetrofit;
        }

    }
//    public static Retrofit getClient(){
//        if(retrofit == null){
//            retrofit = new Retrofit.Builder() //retrofit 객체 생성
//                    .baseUrl(BASE_URL) //어떤 서버BASEURL로 네트워크 통신을 할 것인지 설정
//                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create()) //통신 완료 후, 어떤 converter로 데이터를 parsing할 것인지
//                    .build(); //통신하여 데이터를 파싱한 retrofit 객체 생성 완료-!
//        }
//        return retrofit;
//    }

