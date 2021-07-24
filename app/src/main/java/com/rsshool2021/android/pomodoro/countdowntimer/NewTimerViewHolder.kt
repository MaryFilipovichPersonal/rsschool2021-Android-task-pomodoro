package com.rsshool2021.android.pomodoro.countdowntimer

import android.graphics.drawable.AnimationDrawable
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.rsshool2021.android.pomodoro.R
import com.rsshool2021.android.pomodoro.Timer
import com.rsshool2021.android.pomodoro.TimerListener
import com.rsshool2021.android.pomodoro.Utils.displayTime
import com.rsshool2021.android.pomodoro.databinding.ViewHolderTimerBinding

class NewTimerViewHolder(
    private val binding: ViewHolderTimerBinding,
    private val listener: TimerListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(timer: NewTimer) {
        binding.vhtTvTimer.text = timer.currentMs.displayTime()
        binding.vhtPpbTimerProgress.apply {
            if (getMax() != timer.period) setMax(timer.period)
            setProgress(timer.currentMs)
        }

        if (timer.isStarted) {
            startTimer()
        } else {
            stopTimer()
        }

        setListeners(timer)
    }

    private fun startTimer() {
        with(binding) {
                vhtIbStartPauseTimer.setImageResource(R.drawable.ic_round_pause_24)
            if (vhtIvPlayIndicator.isInvisible) {
                vhtIvPlayIndicator.isInvisible = false
                (vhtIvPlayIndicator.background as? AnimationDrawable)?.start()
            }
        }
    }

    private fun stopTimer() {
        with(binding) {
            if (!vhtIvPlayIndicator.isInvisible) {
                vhtIbStartPauseTimer.setImageResource(R.drawable.ic_round_play_arrow_24)
                vhtIvPlayIndicator.isInvisible = true
                (vhtIvPlayIndicator.background as? AnimationDrawable)?.stop()
            }
        }
    }

    private fun setListeners(timer: NewTimer) {
        with(binding) {
            vhtIbStartPauseTimer.setOnClickListener {
                if (timer.isStarted) {
                    stopTimer()
                    listener.stop(timer.id, timer.currentMs)
                } else {
                    startTimer()
                    listener.start(timer.id)
                }
            }
            vhtIbResetTimer.setOnClickListener {
                stopTimer()
                listener.reset(timer.id)
            }
            vhtIbDeleteTimer.setOnClickListener {
                listener.delete(timer.id)
            }
        }
    }

}