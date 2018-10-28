package com.mayburger.football.mvp.teams

import com.mayburger.football.data.model.Leagues
import com.mayburger.football.data.model.Teams


/**
 * Created by Mayburger on 10/01/18.
 */

interface TeamsView {
    fun onGetTeams(teams: List<Teams.Team>)
    fun onGetLeagues(leagues: List<Leagues.League>)
}
