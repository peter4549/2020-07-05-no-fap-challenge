package com.duke.elliot.kim.kotlin.nofapchallenge.fragments

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import androidx.fragment.app.DialogFragment

import com.duke.elliot.kim.kotlin.nofapchallenge.R

class CongratulatoryMessageDialogFragment : DialogFragment() {

    private var isNewRecord = false
    private var isTitleRenewed = false
    private var period = 0
    private var title = ""


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_congratulatory_message_dialog)

        val textViewTitle = dialog.findViewById<TextView>(R.id.text_view_title)
        val textViewPeriod = dialog.findViewById<TextView>(R.id.text_view_period)
        var titleText = ""
        var periodText = period.toString()

        if (isTitleRenewed)
            titleText = "승급하셨습니다! $title"

        if (isNewRecord)
            periodText = "신기록입니다! $periodText"

        dialog.findViewById<Button>(R.id.button).setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }

    fun setData(isNewRecord: Boolean, isTitleRenewed: Boolean, period: Int, title: String) {
        this.isNewRecord = isNewRecord
        this.isTitleRenewed = isTitleRenewed
        this.period = period
        this.title = title
    }

}
