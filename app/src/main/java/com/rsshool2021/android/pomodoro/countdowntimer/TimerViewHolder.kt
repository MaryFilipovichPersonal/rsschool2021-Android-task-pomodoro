package com.rsshool2021.android.pomodoro.countdowntimer

import android.graphics.drawable.AnimationDrawable
import android.view.View
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.rsshool2021.android.pomodoro.R
import com.rsshool2021.android.pomodoro.Utils.displayTime
import com.rsshool2021.android.pomodoro.databinding.ViewHolderTimerBinding

class TimerViewHolder(
    private val binding: ViewHolderTimerBinding,
    private val listener: TimerListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            vhtIbStartPauseTimer.setOnClickListener {
                listener.startStopClick(adapterPosition)
            }
            vhtIbResetTimer.setOnClickListener {
                listener.resetClick(adapterPosition)
            }
            vhtIbDeleteTimer.setOnClickListener {
                listener.deleteClick(adapterPosition)
            }
        }
    }

    fun bind(timer: Timer) {
        binding.vhtTvTimer.text = timer.currentMs.displayTime()
        binding.vhtPpbTimerProgress.apply {
            if (getMax() != timer.period) setMax(timer.period)
            setProgress(timer.currentMs)
        }

        startStopIndicating(timer)
    }

    private fun startStopIndicating(timer: Timer) {
        with(binding) {
            if (timer.isStarted) {
                vhtIbStartPauseTimer.setImageResource(R.drawable.ic_round_pause_24)
                vhtIvPlayIndicator.isInvisible = false
                (vhtIvPlayIndicator.background as? AnimationDrawable)?.start()
            } else {
                vhtIbStartPauseTimer.visibility =
                    if (timer.currentMs <= 0L) View.INVISIBLE else View.VISIBLE
                vhtIbStartPauseTimer.setImageResource(R.drawable.ic_round_play_arrow_24)
                vhtIvPlayIndicator.isInvisible = true
                (vhtIvPlayIndicator.background as? AnimationDrawable)?.stop()
            }
        }
    }

}