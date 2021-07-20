package com.rsshool2021.android.pomodoro

import androidx.recyclerview.widget.RecyclerView
import com.rsshool2021.android.pomodoro.databinding.ViewHolderTimerBinding

class TimerViewHolder(
    private val binding: ViewHolderTimerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(timer: Timer) {
        binding.vhtTvTimer.text = timer.currentMs.displayTime()
    }

    private fun Long.displayTime(): String {
        if (this <= 0L) {
            return START_TIME
        }
        val h = this / 1000 / 3600
        val m = this / 1000 % 3600 / 60
        val s = this / 1000 % 60
        val ms = this % 1000 / 10
        return "${getTimerSlot(h)}:${getTimerSlot(m)}:${getTimerSlot(s)}:${getTimerSlot(ms)}"
    }

    private fun getTimerSlot(count: Long): String {
        return if (count / 10L > 0) "$count"
        else "0$count"
    }

    companion object {
        private const val START_TIME = "00:00:00:00"
    }

}