package com.yalematta.weathermvvm.data.response


data class Request(
    val language: String,
    val query: String,
    val type: String,
    val unit: String
)