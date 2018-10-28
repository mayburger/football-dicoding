package com.mayburger.football.mvp.events

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.mayburger.football.mvp.events.next.mvp.NextEventsFragment
import com.mayburger.football.mvp.events.prev.mvp.PrevEventsFragment

class EventsPagerAdapter(fm: FragmentManager, private var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> {
                PrevEventsFragment()
            }
            1 -> {
                NextEventsFragment()
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}