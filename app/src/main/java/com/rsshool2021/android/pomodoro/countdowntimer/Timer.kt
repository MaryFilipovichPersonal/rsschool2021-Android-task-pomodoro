package com.rsshool2021.android.pomodoro.countdowntimer

data class Timer(
    val id: Int,
    val period: Long,
    var currentMs: Long,
    var isStarted: Boolean
)