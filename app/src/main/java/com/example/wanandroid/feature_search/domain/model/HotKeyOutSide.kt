package com.example.wanandroid.feature_search.domain.model

data class HotKeyOutSide(
    val data: List<HotKey>,
    val errorCode: Int,
    val errorMsg: String
)