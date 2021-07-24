package com.rsshool2021.android.pomodoro

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.rsshool2021.android.pomodoro.Utils.COMMAND_ID
import com.rsshool2021.android.pomodoro.Utils.COMMAND_START
import com.rsshool2021.android.pomodoro.Utils.COMMAND_STOP
import com.rsshool2021.android.pomodoro.countdowntimer.Timer
import com.rsshool2021.android.pomodoro.countdowntimer.TimersListFragment
import com.rsshool2021.android.pomodoro.databinding.ActivityMainBinding
import com.rsshool2021.android.pomodoro.service.TIMER_CURRENT_MS
import com.rsshool2021.android.pomodoro.service.TIMER_STATE
import com.rsshool2021.android.pomodoro.service.TimerForegroundService

const val TIMER_ACTION = "TimerAction"

class MainActivity : AppCompatActivity(), LifecycleObserver, FragmentResponseService {

    private lateinit var binding: ActivityMainBinding

    // Foreground receiver
    private val timerReceiver: TimerReceiver by lazy { TimerReceiver() }

    private var startedTimer: Timer? = null
    private var requestService: ActivityRequestService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTimersFragment()
    }

    private fun setTimersFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.amFcvContainer.id, TimersListFragment.newInstance(), TIMERS_LIST_FRAGMENT_TAG)
            .commit()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        startedTimer?.let { timer ->
            val startIntent = Intent(this, TimerForegroundService::class.java)
            startIntent.putExtra(COMMAND_ID, COMMAND_START)
            startIntent.putExtra(TIMER_STATE, timer.currentMs)
            startService(startIntent)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        val stopIntent = Intent(this, TimerForegroundService::class.java)
        stopIntent.putExtra(COMMAND_ID, COMMAND_STOP)
        startService(stopIntent)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(timerReceiver, IntentFilter(TIMER_ACTION))
    }

    override fun onPause() {
        (supportFragmentManager.findFragmentByTag(TIMERS_LIST_FRAGMENT_TAG) as ActivityRequestService).onStartedTimerRequest()
        unregisterReceiver(timerReceiver)
        super.onPause()
    }

    override fun onStartedTimerResponse(timer: Timer?) {
        Log.d("MainActivity", "response: timer = ${timer.toString()}")
        startedTimer = timer
    }

    companion object {
        private const val TIMERS_LIST_FRAGMENT_TAG = "TimersListFragment"
    }

    inner class TimerReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == TIMER_ACTION) {
                val currentMs = intent.getLongExtra(TIMER_CURRENT_MS, 0)
                (supportFragmentManager.findFragmentByTag(TIMERS_LIST_FRAGMENT_TAG) as ActivityRequestService).onForegroundServiceResult(currentMs)
                startedTimer = null
            }
        }
    }

}