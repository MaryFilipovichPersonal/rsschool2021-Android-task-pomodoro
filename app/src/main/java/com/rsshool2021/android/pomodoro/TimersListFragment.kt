package com.rsshool2021.android.pomodoro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsshool2021.android.pomodoro.databinding.FragmentTimersListBinding

class TimersListFragment : Fragment(), TimerListener {

    private var _binding: FragmentTimersListBinding? = null
    private val binding get() = _binding!!

    private val timersListAdapter = TimersListAdapter(this)
    private var timers = mutableListOf<Timer>()
    private var nextId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewAdapter()

        setListeners()

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun start(id: Int) {
        changeTimer(id, null, true)
    }

    override fun stop(id: Int, currentMs: Long) {
        changeTimer(id, currentMs, false)
    }

    override fun reset(id: Int) {
        changeTimer(id, 0L, false)
    }

    override fun delete(id: Int) {
        timers.remove(timers.find { it.id == id })
        submitTimersList()
    }

    private fun setRecyclerViewAdapter() {
        binding.ftlRvTimersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timersListAdapter
        }
    }

    private fun setListeners() {
        binding.ftlBtnAddTimer.setOnClickListener {
            timers.add(Timer(nextId++, 0, true))
            submitTimersList()
        }
    }

    private fun changeTimer(id: Int, currentMs: Long?, isStarted: Boolean) {
        val newList = timersListAdapter.currentList.toMutableList()
        val index = timers.indexOf(timers.find { it.id == id })
        timers.find { it.id == id }?.copy(
            currentMs = currentMs ?: timers[index].currentMs,
            isStarted = isStarted
        )?.let {
            newList[index] = it
        }
        submitTimersList(newList)
        timers = newList
    }

    private fun submitTimersList(newList: MutableList<Timer> = timers) {
        timersListAdapter.submitList(newList.toList())
    }

    companion object {
        @JvmStatic
        fun newInstance() = TimersListFragment()
    }

}