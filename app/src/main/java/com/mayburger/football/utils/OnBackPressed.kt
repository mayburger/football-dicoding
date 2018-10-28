package com.mayburger.football.utils

import android.support.v7.app.AppCompatActivity
import com.mayburger.football.mvp.detail.EventsDetailFragment
import com.mayburger.football.mvp.search.mvp.SearchEventsFragment

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
    fun searchFragmentBack(appCompatActivity: AppCompatActivity) {
        val fragments = appCompatActivity.supportFragmentManager.fragments
        for (f in fragments) {
            if (f != null && f is SearchEventsFragment)
                f.onBackPressed()
        }
    }
}