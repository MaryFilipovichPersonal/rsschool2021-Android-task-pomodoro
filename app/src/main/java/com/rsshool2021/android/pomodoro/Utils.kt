package com.rsshool2021.android.pomodoro

object Utils {

    private const val START_TIME = "00:00:00"

    fun convertToMilliseconds(h: Int = 0, m: Int = 0, s: Int = 0): Long {
        val msFromH = h.toLong() * 60 * 60 * 1000
        val msFromM = m.toLong() * 60 * 1000
        val msFromS = s.toLong() * 1000
        return (msFromH + msFromM + msFromS)
    }


    fun Long.displayTime(): String {
        if (this <= 0L) {
            return START_TIME
        }
        val h = this / 1000 / 3600
        val m = this / 1000 % 3600 / 60
        val s = this / 1000 % 60
//        val ms = this % 1000 / 10
        return "${getTimerSlot(h)}:${getTimerSlot(m)}:${getTimerSlot(s)}"
    }

    private fun getTimerSlot(count: Long): String {
        return if (count / 10L > 0) "$count"
        else "0$count"
    }
}