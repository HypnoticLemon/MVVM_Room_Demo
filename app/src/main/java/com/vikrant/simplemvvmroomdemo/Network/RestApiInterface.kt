package com.vikrant.simplemvvmroomdemo.Network

import com.vikrant.simplemvvmroomdemo.model.EventResponseModel
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RestApiInterface {

    @Streaming
    @GET
    suspend fun getFile(@Url url: String): EventResponseModel
}