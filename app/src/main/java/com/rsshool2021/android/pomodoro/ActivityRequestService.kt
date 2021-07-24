package com.rsshool2021.android.pomodoro

import com.rsshool2021.android.pomodoro.countdowntimer.Timer

interface ActivityRequestService {
    fun onStartedTimerRequest()
    fun onForegroundServiceResult(currentMs: Long)
}