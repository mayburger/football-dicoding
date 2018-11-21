package com.mayburger.football.mvp.search.teams.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.mayburger.football.DetailActivity
import com.mayburger.football.R
import com.mayburger.football.data.model.Search
import com.mayburger.football.data.model.Teams
import com.mayburger.football.db.EventFavorites.Companion.EVENT_ID
import com.mayburger.football.db.TeamFavorites.Companion.TEAM_NAME
import com.mayburger.football.mvp.search.events.SearchEventsAdapter
import com.mayburger.football.mvp.teams.TeamsAdapter
import com.mayburger.football.ui.MvpFragment
import com.mayburger.football.utils.OnBackPressed
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


open class SearchTeamsFragment : MvpFragment<SearchTeamsPresenter>(), SearchTeamsView, OnBackPressed// Required empty public constructor
{

    override fun onBackPressed() {
        fragmentManager!!.beginTransaction().remove(this).commit()
    }

    override fun createPresenter(): SearchTeamsPresenter {
        return SearchTeamsPresenter(this@SearchTeamsFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private lateinit var adapter: TeamsAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (isNetworkAvailable()) {
            mvpPresenter!!.showInput(act)
            search.setOnClickListener {
                mvpPresenter!!.showInput(act)
            }
            search.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mvpPresenter!!.getEvents(repoInstance(), search.text.toString())
                    swipeRefresh.isRefreshing = true
                    mvpPresenter!!.hideInput(act)
                    true
                } else {
                    false
                }
            }

            recSearch.visibility = View.GONE
            swipeRefresh.setColorSchemeColors(ContextCompat.getColor(ctx, R.color.colorAccent))
            swipeRefresh.isEnabled = false
        } else {
            toast("Please check your internet connection!")
        }
    }

    override fun onGetTeams(teams: List<Teams.Team>) {
        try {
            swipeRefresh.isRefreshing = false
            recSearch.visibility = View.VISIBLE
            adapter = TeamsAdapter(teams, ctx, repoInstance()) { it ->
                val intent = Intent(context, DetailActivity::class.java)
                val event: Teams.Team = it
                intent.putExtra(TEAM_NAME, event.strTeam)
                startActivity(intent)
            }
            recSearch.layoutManager = GridLayoutManager(ctx, 2)
            recSearch.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onGetTeamsNotAvailable() {
        swipeRefresh.isRefreshing = false
        toast("No data response")
    }

}
