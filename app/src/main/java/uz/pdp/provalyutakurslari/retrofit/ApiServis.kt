package com.example.myweather.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import uz.pdp.provalyutakurslari.models.Course_model

interface ApiServis {

    @GET("/uz/exchange-rates/json/")
    fun getData(): Call<List<Course_model>>


}