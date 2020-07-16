package com.duke.elliot.kim.kotlin.nofapchallenge.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.duke.elliot.kim.kotlin.nofapchallenge.*
import com.duke.elliot.kim.kotlin.nofapchallenge.model.History
import com.duke.elliot.kim.kotlin.nofapchallenge.services.UpdateJobIntentService

import kotlinx.android.synthetic.main.fragment_progress.*
import kotlin.math.roundToInt

class ProgressFragment : Fragment(), SetPeriodDialogFragment.OnSetPeriodListener {

    private lateinit var setPeriodDialogFragment: SetPeriodDialogFragment
    private var targetPeriod = 0
    private var progressInterval = 0
    private var dateCount = 0
    private var recode = 0
    private var title = Titles.STEP_00

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

        button_test.setOnClickListener {
            serviceTest()
        }
    }

    override fun setPeriod(period: Int) {
        this.targetPeriod = period
        progressInterval = (this.targetPeriod / 100F).roundToInt()
        dateCount = 0
    }

    override fun startChallenge() {
        MainActivity.challenging = true

        setUI()

        val history = History(targetPeriod)
        history.status = CHALLENGING
        (activity as MainActivity).viewModel.insert(history)

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
        dateCount = preferences.getInt(KEY_DATE_COUNT, 0)
        recode = preferences.getInt(KEY_RECODE, 0)
        title = preferences.getString(KEY_TITLE, Titles.STEP_00)!!

        checkProgress()
    }

    private fun checkProgress() {
        if (targetPeriod == dateCount)
            runSuccessEvent()
    }

    private fun runSuccessEvent() {
        val originalTitle = title

        if (targetPeriod > recode) {
            when (targetPeriod) {
                in Periods.STEP_00 until Periods.STEP_01 -> {
                    title = Titles.STEP_00
                }
                in Periods.STEP_01 until Periods.STEP_02 -> {
                    title = Titles.STEP_01
                }
                in Periods.STEP_02 until Periods.STEP_03 -> {
                    title = Titles.STEP_02
                }
                in Periods.STEP_03 until Periods.STEP_04 -> {
                    title = Titles.STEP_03
                }
                in Periods.STEP_04 until Periods.STEP_05 -> {
                    title = Titles.STEP_04
                }
                in Periods.STEP_05 until Periods.STEP_06 -> {
                    title = Titles.STEP_05
                }
                in Periods.STEP_06 until Periods.STEP_07 -> {
                    title = Titles.STEP_06
                }
                in Periods.STEP_07 until Periods.STEP_08 -> {
                    title = Titles.STEP_07
                }
                else -> {
                    title = Titles.STEP_08
                }
            }

            val isTitleRenewed = originalTitle != title
            val isNewRecord =  targetPeriod > recode

            showCongratulatoryMessage(isTitleRenewed, isNewRecord, targetPeriod, title)
            updateRecordTitle()
        }
    }

    private fun showCongratulatoryMessage(isNewRecord: Boolean, isTitleRenewed: Boolean,
                                          period: Int, title: String) {
        val congratulatoryMessageDialogFragment = CongratulatoryMessageDialogFragment()

        congratulatoryMessageDialogFragment.setData(isNewRecord, isTitleRenewed, period, title)
        congratulatoryMessageDialogFragment.show((activity as MainActivity).supportFragmentManager, TAG)
    }

    private fun updateRecordTitle() {
        val preferences =
            requireContext().getSharedPreferences(PROGRESS_PREFERENCES, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putInt(KEY_RECODE, recode)
        editor.putString(KEY_TITLE, title)
        editor.apply()
    }

    private fun setUI() {
        text_view_date_count.text = dateCount.toString()
        text_view_period.text = this.targetPeriod.toString()
        progress_bar.progress = dateCount * progressInterval
    }

    fun updateProgress() {
        loadProgress()
        setUI()
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
        dateCount = 0

        val preferences =
            requireContext().getSharedPreferences(PROGRESS_PREFERENCES, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putBoolean(KEY_CHALLENGING, MainActivity.challenging)
        editor.putInt(KEY_TARGET_PERIOD, 0)
        editor.putInt(KEY_PROGRESS_INTERVAL, 0)
        editor.putInt(KEY_DATE_COUNT, 0)
        editor.apply()

        clearUI()
    }

    private fun serviceTest () {
        val intent = Intent(requireContext(), UpdateJobIntentService::class.java)
        requireContext().startService(intent)
        UpdateJobIntentService().enqueueWork(requireContext(), intent)
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
