package com.vikrant.simplemvvmroomdemo.Network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Rest client (Retrofit) for network(API) calling & GSON parsing
 */
object RestApiClient {

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://drive.google.com/u/0/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(
            OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                ).build()
        ).build()

}