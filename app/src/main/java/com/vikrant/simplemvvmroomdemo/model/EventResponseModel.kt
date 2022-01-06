package com.vikrant.simplemvvmroomdemo.model

import com.google.gson.annotations.SerializedName


data class EventResponseModel(
    @SerializedName("Data")
    val Data: List<Data>,
    @SerializedName("totalCount")
    val totalCount: Int
)

data class Data(
    @SerializedName("Festival")
    val Festival: String,
    @SerializedName("Date")
    val Date: String,
    @SerializedName("Day")
    val Day: String,
    @SerializedName("Id")
    val Id: Int,
)
