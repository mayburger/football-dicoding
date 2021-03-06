package com.mayburger.football.mvp.teams

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mayburger.football.DetailActivity
import com.mayburger.football.R
import com.mayburger.football.data.model.Leagues
import com.mayburger.football.data.model.Teams
import com.mayburger.football.db.TeamFavorites.Companion.TEAM_NAME
import com.mayburger.football.mvp.search.events.mvp.SearchEventsFragment
import com.mayburger.football.mvp.search.teams.mvp.SearchTeamsFragment
import com.mayburger.football.ui.MvpFragment
import com.mayburger.football.utils.Constants
import kotlinx.android.synthetic.main.fragment_teams.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


open class TeamsFragment : MvpFragment<TeamsPresenter>(), TeamsView// Required empty public constructor
{
    override fun onGetTeams(teams: List<Teams.Team>) {
        try {
            swipeRefresh.isRefreshing = false
            recTeams.visibility = View.VISIBLE
            adapter = TeamsAdapter(teams, ctx, repoInstance()) { it ->
                val intent = Intent(context, DetailActivity::class.java)
                val event: Teams.Team = it
                intent.putExtra(TEAM_NAME, event.strTeam)
                startActivity(intent)
            }
            recTeams.layoutManager = GridLayoutManager(ctx, 2)
            recTeams.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun createPresenter(): TeamsPresenter {
        return TeamsPresenter(this@TeamsFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onGetLeagues(leagues: List<Leagues.League>) {
        getEventsData(leagues[0].strLeague.toString())

        val list = ArrayList<String>()
        for (i in 0..leagues.size) {
            if (leagues[i].strSport.equals(Constants.SPORT)) {
                list.add(leagues[i].strLeague.toString())
                val adapter = ArrayAdapter<String>(context, R.layout.spinner, list)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner!!.adapter = adapter

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        getEventsData(leagues.get(p2).strLeague.toString())
                    }
                }
            }
        }
    }

    private lateinit var adapter: TeamsAdapter
    fun getEventsData(id: String) {
        if (isNetworkAvailable()) {
            mvpPresenter!!.getEvents(repoInstance(), id)
            recTeams.visibility = View.GONE
            swipeRefresh.isRefreshing = true
            swipeRefresh.setColorSchemeColors(ContextCompat.getColor(ctx, R.color.colorAccent))
        } else {
            toast("Please check your internet connection!")
        }

        swipeRefresh.setOnRefreshListener {
            if (isNetworkAvailable()) {
                mvpPresenter!!.getEvents(repoInstance(), id)
                recTeams.visibility = View.GONE
                swipeRefresh.isRefreshing = true
            } else {
                toast("Please check your internet connection!")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpPresenter!!.getLeagues(repoInstance())
        val activity = activity as AppCompatActivity
        search.setOnClickListener {
            addFragmentOnTop(activity, SearchTeamsFragment(), R.id.frame_top)
        }
    }
}
