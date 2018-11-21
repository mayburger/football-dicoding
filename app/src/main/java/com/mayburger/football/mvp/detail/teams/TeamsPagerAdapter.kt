package com.mayburger.football.mvp.detail.teams

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.mayburger.football.data.model.Teams
import com.mayburger.football.mvp.detail.teams.content.OverviewFragment
import com.mayburger.football.mvp.detail.teams.content.PlayersFragment
import com.mayburger.football.mvp.events.next.mvp.NextEventsFragment
import com.mayburger.football.mvp.events.prev.mvp.PrevEventsFragment

class TeamsPagerAdapter(fm: FragmentManager, private var mNumOfTabs: Int, var teams:Teams.Team) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> {
                OverviewFragment(teams)
            }
            1 -> {
                PlayersFragment(teams)
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}