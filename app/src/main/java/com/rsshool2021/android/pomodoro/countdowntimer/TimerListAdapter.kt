package com.rsshool2021.android.pomodoro.countdowntimer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.rsshool2021.android.pomodoro.TimerListener
import com.rsshool2021.android.pomodoro.databinding.ViewHolderTimerBinding

class TimerListAdapter (private val listener: TimerListener) :
    ListAdapter<Timer, TimerViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderTimerBinding.inflate(layoutInflater, parent, false)
        return TimerViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Timer>() {
            override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: Timer, newItem: Timer) = Any()

        }

    }

}