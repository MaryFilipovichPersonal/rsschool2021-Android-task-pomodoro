package com.rsshool2021.android.pomodoro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsshool2021.android.pomodoro.databinding.FragmentTimersListBinding

class TimersListFragment : Fragment() {

    private var _binding: FragmentTimersListBinding? = null
    private val binding get() = _binding!!

    private val timersListAdapter = TimersListAdapter()
    private val timers = mutableListOf<Timer>()
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

    private fun setRecyclerViewAdapter() {
        binding.ftlRvTimersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timersListAdapter
        }
    }

    private fun setListeners() {
        binding.ftlBtnAddTimer.setOnClickListener {
            timers.add(Timer(nextId++, 0, true))
            timersListAdapter.submitList(timers.toList())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TimersListFragment()
    }
}