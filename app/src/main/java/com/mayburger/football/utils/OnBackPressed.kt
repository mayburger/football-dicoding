package com.mayburger.football.utils

import android.support.v7.app.AppCompatActivity
import com.mayburger.football.mvp.detail.events.EventsDetailFragment
import com.mayburger.football.mvp.search.events.mvp.SearchEventsFragment
import com.mayburger.football.mvp.search.teams.mvp.SearchTeamsFragment

interface OnBackPressed {
    fun onBackPressed()
}

class OnBackPressedFun {
    fun detailFragmentBack(appCompatActivity: AppCompatActivity) {
        val fragments = appCompatActivity.supportFragmentManager.fragments
        for (f in fragments) {
            if (f != null && f is EventsDetailFragment)
                f.onBackPressed()
        }
    }

    fun searchEventsFragmentBack(appCompatActivity: AppCompatActivity) {
        val fragments = appCompatActivity.supportFragmentManager.fragments
        for (f in fragments) {
            if (f != null && f is SearchEventsFragment)
                f.onBackPressed()
        }
    }

    fun searchTeamsFragmentBack(appCompatActivity: AppCompatActivity) {
        val fragments = appCompatActivity.supportFragmentManager.fragments
        for (f in fragments) {
            if (f != null && f is SearchTeamsFragment)
                f.onBackPressed()
        }
    }
}