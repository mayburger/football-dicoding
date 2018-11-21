package com.mayburger.football.mvp.detail.teams.content

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mayburger.football.PlayersDetailActivity
import com.mayburger.football.R
import com.mayburger.football.base.BaseFragment
import com.mayburger.football.data.model.Players
import com.mayburger.football.data.model.Teams
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.espresso.EspressoIdlingResource
import com.mayburger.football.utils.Constants.PLAYERS
import kotlinx.android.synthetic.main.fragment_players.*
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by Mayburger on 10/31/18.
 */


@SuppressLint("ValidFragment")
open class PlayersFragment @SuppressLint("ValidFragment") constructor
(var teams: Teams.Team) : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_players, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getPlayers(teams.strTeam.toString())
    }

    fun getPlayers(team: String) {
        EspressoIdlingResource.increment()
        repoInstance().getPlayers(object : SoccerDataSource.GetPlayersCallback {
            override fun onPlayersLoaded(players: List<Players.Player>?) {
                if (players != null) {
                    if (recycler != null) {
                        recycler.visibility = View.VISIBLE
                    }
                    val adapter = PlayersAdapter(players, ctx, repoInstance()) { it ->
                        val intent = Intent(context, PlayersDetailActivity::class.java)
                        val event: Players.Player = it
                        intent.putExtra(PLAYERS, event)
                        startActivity(intent)
                    }
                    recycler.layoutManager = LinearLayoutManager(ctx)
                    recycler.adapter = adapter
                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement() // Set app as idle.
                    }
                }
            }

            override fun onDataNotAvailable() {}
            override fun onError(errorMessage: String) {}

        }, team)
    }

}

