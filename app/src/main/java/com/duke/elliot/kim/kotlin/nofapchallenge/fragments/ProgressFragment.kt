package com.duke.elliot.kim.kotlin.nofapchallenge.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.duke.elliot.kim.kotlin.nofapchallenge.*

import kotlinx.android.synthetic.main.fragment_progress.*
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 */
class ProgressFragment : Fragment(), SetPeriodDialogFragment.OnSetPeriodListener {

    private lateinit var setPeriodDialogFragment: SetPeriodDialogFragment
    private var targetPeriod = 0
    private var progressInterval = 0
    private var dateCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setPeriodDialogFragment = SetPeriodDialogFragment()
        setPeriodDialogFragment.setListener(this)

        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadProgress()
        setUI()

        image_view_start.setOnClickListener {
            setPeriodDialogFragment.show((activity as MainActivity).supportFragmentManager, TAG)
        }

        image_view_stop.setOnClickListener {
            if (MainActivity.challenging)
                showConfirmationDialog()
        }
    }

    override fun setPeriod(period: Int) {
        this.targetPeriod = period
        progressInterval = (this.targetPeriod / 100F).roundToInt()
        dateCount = 1
    }

    override fun startChallenge() {
        MainActivity.challenging = true

        setUI()

        val preferences =
            requireContext().getSharedPreferences(PROGRESS_PREFERENCES, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putBoolean(KEY_CHALLENGING, MainActivity.challenging)
        editor.putInt(KEY_TARGET_PERIOD, targetPeriod)
        editor.putInt(KEY_PROGRESS_INTERVAL, progressInterval)
        editor.putInt(KEY_DATE_COUNT, dateCount)
        editor.apply()
    }

    private fun loadProgress() {
        val preferences =
            requireContext().getSharedPreferences(PROGRESS_PREFERENCES, Context.MODE_PRIVATE)

        MainActivity.challenging = preferences.getBoolean(KEY_CHALLENGING, false)
        targetPeriod = preferences.getInt(KEY_TARGET_PERIOD, 0)
        progressInterval = preferences.getInt(KEY_PROGRESS_INTERVAL, 0)
        dateCount = preferences.getInt(KEY_DATE_COUNT, 1)
    }

    private fun setUI() {
        text_view_period.text = this.targetPeriod.toString()
        progress_bar.progress = dateCount * progressInterval
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("설마, 해버린 겁니까?")
        builder.setPositiveButton("네") { _, _ ->
            stopChallenge()
        }.setNegativeButton("아니요") { _, _ -> }
            .show()
    }

    private fun stopChallenge() {
        MainActivity.challenging = false
        targetPeriod = 0
        progressInterval = 0
        dateCount = 1

        val preferences =
            requireContext().getSharedPreferences(PROGRESS_PREFERENCES, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putBoolean(KEY_CHALLENGING, MainActivity.challenging)
        editor.putInt(KEY_TARGET_PERIOD, 0)
        editor.putInt(KEY_PROGRESS_INTERVAL, 0)
        editor.putInt(KEY_DATE_COUNT, 1)
        editor.apply()

        clearUI()
    }

    private fun clearUI() {
        progress_bar.progress = 0
        text_view_period.text = "설정된 목표 기간이 없습니다."
    }

    companion object {
        const val TAG = "ProgressFragment"


        // const val testTimeUnit = 10
    }
}
