package com.rsshool2021.android.pomodoro

import com.rsshool2021.android.pomodoro.countdowntimer.Timer

interface FragmentResponseService {
    fun onStartedTimerResponse(timer: Timer?)
}