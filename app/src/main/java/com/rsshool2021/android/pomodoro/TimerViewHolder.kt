package com.rsshool2021.android.pomodoro

import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.rsshool2021.android.pomodoro.Utils.displayTime
import com.rsshool2021.android.pomodoro.databinding.ViewHolderTimerBinding

class TimerViewHolder(
    private val binding: ViewHolderTimerBinding,
    private val listener: TimerListener,
    private val resources: Resources
) : RecyclerView.ViewHolder(binding.root) {

    private var countDownTimer: CountDownTimer? = null

    fun bind(timer: Timer) {
        binding.vhtTvTimer.text = timer.currentMs.displayTime()

        if (timer.isStarted) {
            startTimer(timer)
        } else {
            stopTimer(timer)
        }

        setListeners(timer)
    }

    private fun setListeners(timer: Timer) {
        with(binding) {
            vhtIbStartPauseTimer.setOnClickListener {
                if (timer.isStarted) {
                    listener.stop(timer.id, timer.currentMs)
                } else {
                    listener.start(timer.id)
                }
            }
            vhtIbResetTimer.setOnClickListener {
                listener.reset(timer.id)
            }
            vhtIbDeleteTimer.setOnClickListener {
                listener.delete(timer.id)
            }
        }
    }

    private fun startTimer(timer: Timer) {
        with(binding) {
            vhtIbStartPauseTimer.setImageResource(R.drawable.ic_round_pause_24)

            countDownTimer?.cancel()
            countDownTimer = getCountDownTimer(timer)
            countDownTimer?.start()

            vhtIvPlayIndicator.isInvisible = false
            (vhtIvPlayIndicator.background as? AnimationDrawable)?.start()
        }
    }

    private fun stopTimer(timer: Timer) {
        with(binding) {
            vhtIbStartPauseTimer.setImageResource(R.drawable.ic_round_play_arrow_24)

            countDownTimer?.cancel()

            vhtIvPlayIndicator.isInvisible = true
            (vhtIvPlayIndicator.background as? AnimationDrawable)?.stop()
        }
    }

    private fun getCountDownTimer(timer: Timer): CountDownTimer {
        return object : CountDownTimer(PERIOD, UNIT_TEN_MS) {
            val interval = UNIT_TEN_MS

            override fun onTick(millisUntilFinished: Long) {
                timer.currentMs += interval
                binding.vhtTvTimer.text = timer.currentMs.displayTime()
            }

            override fun onFinish() {
                binding.vhtTvTimer.text = timer.currentMs.displayTime()
            }

        }
    }

    companion object {
        private const val UNIT_TEN_MS = 1000L
        private const val PERIOD = 1000L * 60L * 60L * 24L // Day
    }

}