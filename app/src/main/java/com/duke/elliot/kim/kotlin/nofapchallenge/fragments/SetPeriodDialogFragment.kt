package com.duke.elliot.kim.kotlin.nofapchallenge.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.duke.elliot.kim.kotlin.nofapchallenge.R
import kotlinx.android.synthetic.main.dialog_fragment_set_period.*

class SetPeriodDialogFragment : DialogFragment() {

    private lateinit var onSetPeriodListener: OnSetPeriodListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_fragment_set_period)

        dialog.button_set_period.setOnClickListener {
            setPeriod()
        }

        return dialog
    }

    interface OnSetPeriodListener {
        fun setPeriod(period: Int)
    }

    fun setListener(onSetPeriodListener: OnSetPeriodListener) {
        this.onSetPeriodListener = onSetPeriodListener
    }

    private fun setPeriod() {
        if (!dialog?.edit_text_period?.text?.isBlank()!!) {
            onSetPeriodListener.setPeriod(dialog?.edit_text_period?.text?.toString()?.toInt()!!)
            dialog?.dismiss()
        }
    }
}