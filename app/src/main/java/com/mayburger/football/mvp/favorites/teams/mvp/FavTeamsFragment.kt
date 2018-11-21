package com.mayburger.football.mvp.favorites.teams.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mayburger.football.DetailActivity
import com.mayburger.football.R
import com.mayburger.football.data.model.Teams
import com.mayburger.football.db.TeamFavorites
import com.mayburger.football.db.TeamFavorites.Companion.TEAM_NAME
import com.mayburger.football.mvp.favorites.teams.FavTeamsAdapter
import com.mayburger.football.ui.MvpFragment
import kotlinx.android.synthetic.main.fragment_teams_favorites.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


open class FavTeamsFragment : MvpFragment<FavTeamsPresenter>(), FavTeamsView// Required empty public constructor
{
    override fun createPresenter(): FavTeamsPresenter {
        return FavTeamsPresenter(this@FavTeamsFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams_favorites, container, false)
    }


    open lateinit var adapter: FavTeamsAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpPresenter!!.getTeams(ctx)
        swipeRefreshTeams.setColorSchemeColors(ContextCompat.getColor(ctx, R.color.colorAccent))
        swipeRefreshTeams.setOnRefreshListener {
            if (recTeams != null) {
                with(recTeams) {
                    visibility = View.GONE
                }
            }
            mvpPresenter!!.getTeams(ctx)
        }
    }

    override fun onGetTeams(events: List<TeamFavorites>) {
        recTeams.visibility = View.VISIBLE
        swipeRefreshTeams.isRefreshing = false
        if (!events.isEmpty()) {
            recTeams.visibility = View.VISIBLE
            swipeRefreshTeams.isRefreshing = false
            adapter = FavTeamsAdapter(events, ctx, repoInstance()) { it ->
                val intent = Intent(context, DetailActivity::class.java)
                val team = Teams.Team()
                team.strTeam = it.strTeam
                intent.putExtra(TEAM_NAME, team.strTeam)
                startActivity(intent)
            }
            recTeams.layoutManager = GridLayoutManager(ctx, 2)
            recTeams.adapter = adapter
        } else {
            toast("No Data")
        }
    }


    override fun onDataEmpty() {
        swipeRefreshTeams.isRefreshing = false
        toast("No Data")
    }

}
