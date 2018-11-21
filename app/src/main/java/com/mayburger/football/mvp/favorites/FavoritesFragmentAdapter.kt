package com.mayburger.football.mvp.favorites

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.mayburger.football.db.EventFavorites
import com.mayburger.football.mvp.events.next.mvp.NextEventsFragment
import com.mayburger.football.mvp.events.prev.mvp.PrevEventsFragment
import com.mayburger.football.mvp.favorites.events.mvp.FavEventsFragment
import com.mayburger.football.mvp.favorites.teams.mvp.FavTeamsFragment

class FavoritesFragmentAdapter(fm: FragmentManager, private var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> {
                FavEventsFragment()
            }
            1 -> {
                FavTeamsFragment()
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}