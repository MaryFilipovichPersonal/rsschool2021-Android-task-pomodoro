package com.rsshool2021.android.pomodoro.countdowntimer

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rsshool2021.android.pomodoro.*
import com.rsshool2021.android.pomodoro.databinding.FragmentTimersListBinding

class TimersListFragment : Fragment(), TimerListener {

    private var _binding: FragmentTimersListBinding? = null
    private val binding get() = _binding!!

    private val listAdapter = TimerListAdapter(this)
    private var timers = mutableListOf<Timer>()
    private var nextId = 0
    private lateinit var startedTimer: Timer

    private var countDownTimer: CountDownTimer? = null

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

    private fun setRecyclerViewAdapter() {
        binding.fntlRvTimersList.apply {
            itemAnimator = object : DefaultItemAnimator() {
                override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean =
                    true
            }
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        listAdapter.submitList(timers)
    }

    private fun setListeners() {
        binding.fntlBtnAddTimer.setOnClickListener {
            showAddTimerFragment()
        }
        (requireActivity() as MainActivity).supportFragmentManager.setFragmentResultListener(
            ADD_TIMER_REQUEST_KEY, viewLifecycleOwner
        ) { key, bundle ->
            val period: Long = bundle.getLong(TIMER_BUNDLE_PERIOD_KEY)
            addNewTimer(period)
        }
    }

    private fun addNewTimer(period: Long) {
        timers.add(Timer(nextId++, period, period, false))
        listAdapter.notifyItemInserted(timers.lastIndex)
    }

    private fun showAddTimerFragment() {
        val addTimerFragment = AddTimerFragment.newInstance()
        addTimerFragment.show(
            (requireActivity() as MainActivity).supportFragmentManager,
            ADD_TIMER_FRAGMENT_TAG
        )
    }

    override fun startStopClick(position: Int) {
        if (timers[position].isStarted) stopTimer(position)
        else startTimer(position)
    }

    private fun startTimer(position: Int) {
        if (this::startedTimer.isInitialized && timers.contains(startedTimer))
            stopStartedTimer()
        startedTimer = timers[position]
        startedTimer.isStarted = true
        countDownTimer = getCountDownTimer(startedTimer)
        countDownTimer?.start()
        listAdapter.notifyItemChanged(position)
    }

    private fun stopStartedTimer() {
        stopTimer(timers.indexOf(startedTimer))
    }

    private fun stopTimer(position: Int) {
        countDownTimer?.cancel()
        timers[position].isStarted = false
        listAdapter.notifyItemChanged(position)
    }

    override fun resetClick(position: Int) {
        val timer = timers[position]
        if (timer.isStarted) {
            countDownTimer?.cancel()
        }
        timer.apply {
            isStarted = false
            currentMs = period
        }
        listAdapter.notifyItemChanged(position)
    }

    override fun deleteClick(position: Int) {
        if (timers[position].isStarted) countDownTimer?.cancel()
        timers.removeAt(position)
        listAdapter.notifyItemRemoved(position)
    }

    private fun getCountDownTimer(timer: Timer): CountDownTimer {
        return object : CountDownTimer(timer.period, INTERVAL) {

            override fun onTick(millisUntilFinished: Long) {
                val position = timers.indexOf(timer)
                timer.currentMs -= INTERVAL
                listAdapter.notifyItemChanged(position)
            }

            override fun onFinish() {
                val position = timers.indexOf(timer)
                timer.currentMs -= INTERVAL
                timer.isStarted = false
                listAdapter.notifyItemChanged(position)
                Toast.makeText(
                    context,
                    if (timer.currentMs <= 0L) "Timer is over" else "Timer was stopped",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TimersListFragment()

        private const val INTERVAL = 1000L
    }

}