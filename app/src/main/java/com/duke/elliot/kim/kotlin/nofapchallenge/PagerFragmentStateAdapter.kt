package com.duke.elliot.kim.kotlin.nofapchallenge

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.duke.elliot.kim.kotlin.nofapchallenge.fragments.BoardFragment
import com.duke.elliot.kim.kotlin.nofapchallenge.fragments.HistoryFragment
import com.duke.elliot.kim.kotlin.nofapchallenge.fragments.RankingFragment

class PagerFragmentStateAdapter(private val fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return PAGE_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> (fragmentActivity as MainActivity).progressFragment
            1 -> RankingFragment()
            2 -> BoardFragment()
            3 -> HistoryFragment()
            else -> throw Exception("Invalid fragment")
        }
    }

    companion object {
        const val PAGE_COUNT = 4
    }
}