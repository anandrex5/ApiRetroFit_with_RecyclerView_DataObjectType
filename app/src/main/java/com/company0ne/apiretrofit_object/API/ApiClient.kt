package com.company0ne.apiretrofit.API

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/* Api - https://jsonplaceholder.typicode.com/posts/1/comments */

object ApiClient {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private var retrofit: Retrofit? = null
    val client: Retrofit
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val client = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    // .addConverterFactory(ScalarsConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit!!
        }
}