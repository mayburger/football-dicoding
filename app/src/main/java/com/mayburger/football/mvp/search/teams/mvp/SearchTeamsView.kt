package com.mayburger.football.mvp.search.teams.mvp

import com.mayburger.football.data.model.Search
import com.mayburger.football.data.model.Teams


/**
 * Created by Mayburger on 10/01/18.
 */

interface SearchTeamsView {
    fun onGetTeams(teams: List<Teams.Team>)
    fun onGetTeamsNotAvailable()
}
