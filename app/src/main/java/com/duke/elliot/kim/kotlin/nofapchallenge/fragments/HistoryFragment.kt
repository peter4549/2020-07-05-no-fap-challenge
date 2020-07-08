package com.duke.elliot.kim.kotlin.nofapchallenge.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.duke.elliot.kim.kotlin.nofapchallenge.R
import com.duke.elliot.kim.kotlin.nofapchallenge.adapters.LayoutManagerWrapper
import com.duke.elliot.kim.kotlin.nofapchallenge.adapters.RecyclerViewAdapter
import com.duke.elliot.kim.kotlin.nofapchallenge.model.History
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_history.apply {
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
            layoutManager = LayoutManagerWrapper(context, 1)
        }
    }

    fun setRecyclerViewAdapter(recyclerViewAdapter: RecyclerViewAdapter) {
        this.recyclerViewAdapter = recyclerViewAdapter
    }

    fun insert(history: History) {
        recyclerViewAdapter.insert(history)
    }

    fun update(history: History) {
        recyclerViewAdapter.update(history)
    }

    fun remove(history: History) {
        recyclerViewAdapter.remove(history)
    }
}
