package com.zen.sentimentanalysis.retrofit

import com.zen.sentimentanalysis.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://aylien-text.p.rapidapi.com/"

    private val retrofit by lazy {

        val loggingInterceptor: HttpLoggingInterceptor =
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else
                HttpLoggingInterceptor()

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor {
                val original = it.request()
                val requestBuilder = original.newBuilder()
                    .addHeader(
                        "x-rapidapi-key",
                        BuildConfig.API_KEY
                    )
                    .addHeader("x-rapidapi-host", "aylien-text.p.rapidapi.com")
                    .method(original.method, original.body)
                it.proceed(requestBuilder.build())
            }.build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}