package com.duke.elliot.kim.kotlin.nofapchallenge

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.duke.elliot.kim.kotlin.nofapchallenge.adapters.PagerFragmentStateAdapter
import com.duke.elliot.kim.kotlin.nofapchallenge.adapters.RecyclerViewAdapter
import com.duke.elliot.kim.kotlin.nofapchallenge.fragments.HistoryFragment
import com.duke.elliot.kim.kotlin.nofapchallenge.fragments.ProgressFragment
import com.duke.elliot.kim.kotlin.nofapchallenge.model.HistoryViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: HistoryViewModel
    private var init = true
    val progressFragment = ProgressFragment()
    var historyFragment = HistoryFragment()

    private val dateUpdateReceiver = object: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            progressFragment.updateProgress()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]

        viewModel.getAll().observe(this@MainActivity, androidx.lifecycle.Observer { histories ->
            if (init) {
                historyFragment.setRecyclerViewAdapter(RecyclerViewAdapter(histories))
                init = false
            } else {
                when (viewModelOperation) {
                    ViewModelOperation.INSERT -> historyFragment.insert(histories[histories.size - 1])
                    ViewModelOperation.UPDATE -> historyFragment.update(viewModel.targetHistory)
                    ViewModelOperation.DELETE -> historyFragment.remove(viewModel.targetHistory)
                }
            }
        })

        view_pager.adapter =
            PagerFragmentStateAdapter(
                this
            )

        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = tabTexts[position]
            tab.setIcon(tabIcons[position])
            tab.icon!!.setColorFilter(
                ContextCompat.getColor(
                    this@MainActivity, R.color.colorTabIconUnselected
                ), Mode.SRC_IN
            )
        }.attach()

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity, R.color.colorTabIconUnselected
                    ), Mode.SRC_IN
                )
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.icon?.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity, R.color.colorTabIconSelected
                    ), Mode.SRC_IN
                )
            }

        })
    }

    override fun onResume() {
        super.onResume()
        isAppRunning = true
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(dateUpdateReceiver, IntentFilter(ACTION_DATE_UPDATED))
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(dateUpdateReceiver)
        isAppRunning = false
        super.onStop()
    }

    companion object {
        var isAppRunning = false
        var challenging = false
        var viewModelOperation: ViewModelOperation? = null

        const val ACTION_DATE_UPDATED = "action_date_updated"

        private val tabIcons = arrayOf(
            R.drawable.ic_tab_person_24dp,
            R.drawable.ic_tab_person_24dp,
            R.drawable.ic_tab_person_24dp,
            R.drawable.ic_tab_person_24dp
        )

        private val tabTexts = arrayOf("진행상황", "순위", "게시판", "내정보")
    }
}
