package com.company0ne.apiretrofit.API

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("posts/1")
    fun getData(): Call<JsonObject>
}

