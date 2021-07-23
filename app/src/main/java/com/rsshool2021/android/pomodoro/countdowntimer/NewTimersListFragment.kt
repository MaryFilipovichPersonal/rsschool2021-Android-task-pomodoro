package com.rsshool2021.android.pomodoro.countdowntimer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsshool2021.android.pomodoro.*
import com.rsshool2021.android.pomodoro.Utils.displayTime
import com.rsshool2021.android.pomodoro.databinding.FragmentNewTimersListBinding
import kotlinx.coroutines.launch

class NewTimersListFragment : Fragment(), TimerListener {

    private var _binding: FragmentNewTimersListBinding? = null
    private val binding get() = _binding!!

    private val listAdapter = NewTimerListAdapter(this)
    private var timers = mutableListOf<NewTimer>()
    private var nextId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewTimersListBinding.inflate(inflater, container, false)
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
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
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
        timers.add(NewTimer(nextId++, period, period, false))
        submitTimerList()
    }

    private fun submitTimerList(newList: MutableList<NewTimer> = timers) {
        listAdapter.submitList(newList.toList())
    }

    private fun showAddTimerFragment() {
        val addTimerFragment = AddTimerFragment.newInstance()
        addTimerFragment.show(
            (requireActivity() as MainActivity).supportFragmentManager,
            ADD_TIMER_FRAGMENT_TAG
        )
    }

    override fun start(id: Int) {

    }

    override fun stop(id: Int, currentMs: Long) {

    }

    override fun reset(id: Int) {

    }

    override fun delete(id: Int) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = NewTimersListFragment()

        private const val INTERVAL = 1000L
    }

}