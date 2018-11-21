package com.mayburger.football.mvp.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mayburger.football.R
import com.mayburger.football.base.BaseFragment
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_favorites_main.*


@SuppressLint("ValidFragment")
/**
 * A simple [Fragment] subclass.
 */
class FavoritesFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = activity as AppCompatActivity

        tab_layout.addTab(tab_layout.newTab().setText("Events"))
        tab_layout.addTab(tab_layout.newTab().setText("Teams"))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = FavoritesFragmentAdapter(activity.supportFragmentManager, tab_layout.tabCount)
        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.setTabTextColors(resources.getColor(R.color.colorGreyIndicator), resources.getColor(R.color.colorPrimary))
        tab_layout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }
}
// Required empty public constructor
