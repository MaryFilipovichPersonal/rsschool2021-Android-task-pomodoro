package com.rsshool2021.android.pomodoro.service

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.rsshool2021.android.pomodoro.R
import com.rsshool2021.android.pomodoro.TIMER_ACTION
import com.rsshool2021.android.pomodoro.Utils.COMMAND_ID
import com.rsshool2021.android.pomodoro.Utils.COMMAND_START
import com.rsshool2021.android.pomodoro.Utils.COMMAND_STOP
import com.rsshool2021.android.pomodoro.Utils.INVALID
import com.rsshool2021.android.pomodoro.Utils.displayTime
import com.rsshool2021.android.pomodoro.countdowntimer.Timer
import kotlinx.coroutines.*
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext

const val TIMER_STATE = "TimerState"
const val TIMER_CURRENT_MS = "TimerCurrentMs"

class TimerForegroundService : Service(), CoroutineScope {

    private var isServiceStarted = false

    private val notificationHelper by lazy { NotificationHelper(this) }
    private var startTimeMs = 0L

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        intent?.extras?.run {
            when (getString(COMMAND_ID) ?: INVALID) {
                COMMAND_START -> {
                    startTimeMs = getLong(TIMER_STATE, 0L)
                    startTimer()
                }
                COMMAND_STOP -> endTimerService()
                INVALID -> {}
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun startTimer() {
        if (isServiceStarted) {
            return
        }
        startForeground(NotificationHelper.NOTIFICATION_ID, notificationHelper.getNotification())
        broadcastUpdate()
        startCoroutineTimer()
        isServiceStarted = true
    }

    private fun broadcastUpdate() {
        if (startTimeMs > 0L) {
            notificationHelper.updateNotification(startTimeMs.displayTime())
        } else {
            notificationHelper.updateNotification("Timer is over")
        }
        sendBroadcast(
            Intent(TIMER_ACTION)
                .putExtra(TIMER_CURRENT_MS, startTimeMs)
        )
    }

    private fun startCoroutineTimer() {
        launch(coroutineContext) {
            while (startTimeMs > 0) {
                startTimeMs -= INTERVAL
                broadcastUpdate()
                delay(INTERVAL)
            }
            if (startTimeMs <= 0)
                broadcastUpdate()
        }
    }

    private fun endTimerService() {
        if (!isServiceStarted) {
            return
        }
        job.cancel()
        broadcastUpdate()
        isServiceStarted = false
        stopService()
    }

    private fun stopService() {
        stopForeground(true)
        stopSelf()
    }

    companion object {
        private const val INTERVAL = 1000L
    }

}