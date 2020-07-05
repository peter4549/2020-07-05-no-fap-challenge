package com.duke.elliot.kim.kotlin.nofapchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = PagerFragmentStateAdapter(this)

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

        companion object {

            private val tabIcons = arrayOf(
                R.drawable.ic_tab_person_24dp,
                R.drawable.ic_tab_person_24dp,
                R.drawable.ic_tab_person_24dp,
                R.drawable.ic_tab_person_24dp
            )

            private val tabTexts = arrayOf("진행상황", "순위", "게시판", "내정보")
        }
}
