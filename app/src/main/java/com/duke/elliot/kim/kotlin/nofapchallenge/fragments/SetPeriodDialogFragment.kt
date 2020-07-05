package com.duke.elliot.kim.kotlin.nofapchallenge.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.duke.elliot.kim.kotlin.nofapchallenge.R

class SetPeriodDialogFragment() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_fragment_set_period)

        return dialog
    }
}