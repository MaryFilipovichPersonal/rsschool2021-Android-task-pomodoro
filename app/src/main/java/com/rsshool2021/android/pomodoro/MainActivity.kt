package com.rsshool2021.android.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsshool2021.android.pomodoro.countdowntimer.TimersListFragment
import com.rsshool2021.android.pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTimersFragment()
    }

    private fun setTimersFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.amFcvContainer.id, TimersListFragment.newInstance(), TIMERS_LIST_FRAGMENT_TAG)
            .commit()
    }

    companion object {
        private const val TIMERS_LIST_FRAGMENT_TAG = "TimersListFragment"
    }
}