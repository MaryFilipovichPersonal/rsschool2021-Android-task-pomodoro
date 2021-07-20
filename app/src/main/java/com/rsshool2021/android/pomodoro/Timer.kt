package com.rsshool2021.android.pomodoro

data class Timer(
    val id: Int,
    var currentMs: Long,
    var isStarted: Boolean
)
