package com.mayburger.football.mvp.detail.teams

import com.mayburger.football.data.model.Teams


/**
 * Created by Mayburger on 10/01/18.
 */

interface TeamsDetailView {
    fun isFavorite(name: String, isFavorite: Boolean)
    fun onGetTeam(teams: Teams.Team)
}
