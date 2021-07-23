package com.rsshool2021.android.pomodoro.countdowntimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rsshool2021.android.pomodoro.*
import com.rsshool2021.android.pomodoro.databinding.FragmentAddTimerBinding

class AddTimerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTimerBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNumberPickers()

        setListeners()
    }

    private fun initNumberPickers() {
        with(binding) {
            fatNpHoursPicker.apply {
                minValue = 0
                maxValue = 23
                value = 0
            }
            fatNpMinutesPicker.apply {
                maxValue = 59
                minValue = 0
                value = 0
            }
            fatNpSecondsPicker.apply {
                maxValue = 59
                minValue = 0
                value = 0
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            fatBtnAddTimer.setOnClickListener {
                val h = fatNpHoursPicker.value
                val m = fatNpMinutesPicker.value
                val s = fatNpSecondsPicker.value
                val periodInMs = Utils.convertToMilliseconds(h, m, s)
                (requireActivity() as MainActivity).supportFragmentManager.setFragmentResult(
                    ADD_TIMER_REQUEST_KEY,
                    bundleOf(
                        TIMER_BUNDLE_PERIOD_KEY to periodInMs
                    )
                )
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddTimerFragment()
    }
}