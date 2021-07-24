package com.rsshool2021.android.pomodoro.countdowntimer

interface TimerListener {
    fun startStopClick(position: Int)
    fun resetClick(position: Int)
    fun deleteClick(position: Int)
}