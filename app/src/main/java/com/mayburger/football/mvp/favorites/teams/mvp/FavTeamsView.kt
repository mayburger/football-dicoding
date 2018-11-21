package com.mayburger.football.mvp.favorites.teams.mvp

import com.mayburger.football.db.TeamFavorites


/**
 * Created by Mayburger on 10/01/18.
 */

interface FavTeamsView {
    fun onGetTeams(events: List<TeamFavorites>)
    fun onDataEmpty()
}
