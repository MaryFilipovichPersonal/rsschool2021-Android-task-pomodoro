package com.rsshool2021.android.pomodoro.countdowntimer

import androidx.recyclerview.widget.RecyclerView
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
            setMax(timer.period)
            setProgress(timer.currentMs-1)
        }

//        if (timer.isStarted) {
//
//        }

        setListeners(timer)
    }

    private fun setListeners(timer: NewTimer) {
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

}