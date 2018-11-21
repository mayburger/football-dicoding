package com.mayburger.football.mvp.detail.teams

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mayburger.football.R
import com.mayburger.football.data.model.Teams
import com.mayburger.football.ui.MvpFragment
import com.mayburger.football.utils.OnBackPressed
import com.mayburger.football.utils.bold
import com.mayburger.football.utils.light
import kotlinx.android.synthetic.main.fragment_teams_detail.*
import org.jetbrains.anko.support.v4.ctx


@SuppressLint("ValidFragment")
class TeamsDetailFragment constructor(private var team_name: String) : MvpFragment<TeamsDetailPresenter>(), OnBackPressed, TeamsDetailView// Required empty public constructor
{
    override fun isFavorite(name: String, isFavorite: Boolean) {
        if (isFavorite) {
            unfavorite.visibility = View.VISIBLE
            favorite.visibility = View.GONE
        } else {
            unfavorite.visibility = View.GONE
            favorite.visibility = View.VISIBLE
        }
        favorite.setOnClickListener {
            mvpPresenter!!.addTeam(name, ctx)
            unfavorite.visibility = View.VISIBLE
            favorite.visibility = View.GONE
        }

        unfavorite.setOnClickListener {
            mvpPresenter!!.removeTeam(name, ctx)
            unfavorite.visibility = View.GONE
            favorite.visibility = View.VISIBLE
        }
    }

    override fun onGetTeam(teams: Teams.Team) {
        Glide.with(ctx).load(teams.strTeamBadge).apply(RequestOptions().centerCrop()).into(image)
        with(team) {
            typeface = bold
            text = teams.strTeam
        }
        with(location) {
            typeface = light
            text = teams.strCountry
        }
        with(title) {
            typeface = bold
        }

        val activity = activity as AppCompatActivity

        tab_layout.addTab(tab_layout.newTab().setText("Overview"))
        tab_layout.addTab(tab_layout.newTab().setText("Players"))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TeamsPagerAdapter(activity.supportFragmentManager, tab_layout.tabCount, teams)
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

    override fun createPresenter(): TeamsDetailPresenter {
        return TeamsDetailPresenter(this@TeamsDetailFragment)
    }

    override fun onBackPressed() {
        fragmentManager!!.beginTransaction().remove(this).commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpPresenter!!.getTeam(repoInstance(), team_name)
        mvpPresenter!!.isFavorite(team_name, ctx)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams_detail, container, false)
    }
}