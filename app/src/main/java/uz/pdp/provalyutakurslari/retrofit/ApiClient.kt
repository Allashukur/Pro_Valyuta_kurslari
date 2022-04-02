package com.example.myweather.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    const val BASE_URL =
        "http://nbu.uz/"

    const
    val BASE_URL_IMAGE =
        "http://nbu.uz/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}