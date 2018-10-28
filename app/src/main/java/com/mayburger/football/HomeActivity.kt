package com.mayburger.football

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.mayburger.football.base.BaseActivity
import com.mayburger.football.mvp.events.EventsFragment
import com.mayburger.football.mvp.events.fav.mvp.FavEventsFragment
import com.mayburger.football.mvp.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.ctx
import java.util.ArrayList


class HomeActivity : BaseActivity() {


    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        replaceBottomNavigationFragments(EventsFragment())
        nav.defaultBackgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)

        setupBottomNavigation(ArrayList(),
                intArrayOf(R.string.app_name, R.string.app_name, R.string.app_name),
                intArrayOf(R.drawable.ic_trophy, R.drawable.ic_soccer, R.drawable.ic_unfavorite),
                intArrayOf(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary)
        )

        nav.setOnTabSelectedListener({ position, _ ->
            when (position) {
                0 ->
                    replaceBottomNavigationFragments(EventsFragment())
                1 ->
                    replaceBottomNavigationFragments(TeamsFragment())
                2 ->
                    replaceBottomNavigationFragments(FavEventsFragment())
            }

            true
        })
    }

    override fun onBackPressed() {
        initBackPressed(this@HomeActivity)
    }

    private fun setupBottomNavigation(itemList: ArrayList<AHBottomNavigationItem>, titleList: IntArray, iconlist: IntArray, colorList: IntArray) {
        nav.accentColor = ContextCompat.getColor(ctx, R.color.colorAccent)
        nav.inactiveColor = ContextCompat.getColor(ctx, R.color.colorGreyIndicator)
        nav.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE

        for (i in titleList.indices) {
            itemList.add(AHBottomNavigationItem(titleList[i], iconlist[i], colorList[i]))
            nav.addItem(itemList[i])
        }
    }

    private fun replaceBottomNavigationFragments(fragment: Fragment) {
        replaceFragment(this, fragment, R.id.frame_home)
    }

}