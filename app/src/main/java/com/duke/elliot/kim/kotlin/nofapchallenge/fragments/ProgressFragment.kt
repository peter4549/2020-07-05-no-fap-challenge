package com.duke.elliot.kim.kotlin.nofapchallenge.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        image_view_set_period.setOnClickListener {
            setPeriodDialogFragment.show((activity as MainActivity).supportFragmentManager, TAG)
        }

        image_view_start.setOnClickListener {
            startChallenge()
        }
    }

    override fun setPeriod(period: Int) {
        this.targetPeriod = period
        progressInterval = (this.targetPeriod / 100F).roundToInt()
        dateCount = 1
        text_view_period.text = this.targetPeriod.toString()
    }

    private fun loadProgress() {
        val preferences =
            requireContext().getSharedPreferences(PROGRESS_PREFERENCES, Context.MODE_PRIVATE)

        targetPeriod = preferences.getInt(KEY_TARGET_PERIOD, 0)
        progressInterval = preferences.getInt(KEY_PROGRESS_INTERVAL, 0)
        dateCount = preferences.getInt(KEY_DATE_COUNT, 1)
    }

    private fun startChallenge() {
        val progress = dateCount * progressInterval
        progress_bar.progress = progress
    }

    companion object {
        const val TAG = "ProgressFragment"


        const val testTimeUnit = 10
    }
}
