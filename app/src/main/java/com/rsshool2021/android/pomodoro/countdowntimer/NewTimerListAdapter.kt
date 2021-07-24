package com.rsshool2021.android.pomodoro.countdowntimer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.rsshool2021.android.pomodoro.TimerListener
import com.rsshool2021.android.pomodoro.databinding.ViewHolderTimerBinding

class NewTimerListAdapter (private val listener: TimerListener) :
    ListAdapter<NewTimer, NewTimerViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewTimerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderTimerBinding.inflate(layoutInflater, parent, false)
        return NewTimerViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NewTimerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<NewTimer>() {
            override fun areItemsTheSame(oldItem: NewTimer, newItem: NewTimer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewTimer, newItem: NewTimer): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: NewTimer, newItem: NewTimer) = Any()

        }

    }

}