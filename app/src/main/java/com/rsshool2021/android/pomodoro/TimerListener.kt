package com.rsshool2021.android.pomodoro

interface TimerListener {
    fun start(id: Int)
    fun stop(id: Int, currentMs: Long)
    fun reset(id: Int)
    fun delete(id: Int)
}