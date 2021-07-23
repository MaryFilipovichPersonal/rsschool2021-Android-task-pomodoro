package com.rsshool2021.android.pomodoro.countdowntimer

data class NewTimer(
    val id: Int,
    val period: Long,
    var currentMs: Long,
    var isStarted: Boolean
)