package com.duke.elliot.kim.kotlin.nofapchallenge.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.duke.elliot.kim.kotlin.nofapchallenge.CHALLENGING
import com.duke.elliot.kim.kotlin.nofapchallenge.FAILURE
import com.duke.elliot.kim.kotlin.nofapchallenge.R
import com.duke.elliot.kim.kotlin.nofapchallenge.SUCCESS
import com.duke.elliot.kim.kotlin.nofapchallenge.model.History
import kotlinx.android.synthetic.main.card_view_history.view.*
import kotlinx.android.synthetic.main.fragment_progress.view.*

class RecyclerViewAdapter(private val histories: MutableList<History>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return histories.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val statusDrawableId = when (histories[position].status) {
            CHALLENGING -> R.drawable.ic_clock_64dp
            FAILURE -> R.drawable.ic_failure_64dp
            SUCCESS -> R.drawable.ic_checked_64dp
            else -> 0
        }

        val targetPeriodText = "도전 기간: ${histories[position].targetPeriod}일"

        Glide
            .with(holder.view.image_view_status.context)
            .load(statusDrawableId)
            .centerCrop()
            .into(holder.view.image_view_status)

        holder.view.text_view_target_period.text = targetPeriodText
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun insert(history: History) {
        histories.add(0, history)
        notifyItemInserted(0)
    }

    fun update(history: History) {
        notifyItemChanged(getPosition(history))
    }

    fun remove(history: History) {
        histories.remove(history)
        notifyItemRemoved(getPosition(history))
    }

    private fun getPosition(history: History): Int = histories.indexOf(history)
}