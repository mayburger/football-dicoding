package com.mayburger.football.db

import java.io.Serializable

/**
 * Created by Mayburger on 9/24/18.
 */

data class TeamFavorites(
        val team_id: String?

) : Serializable {

    companion object {
        const val TABLE_TEAMS: String = "TABLE_TEAMS"
        const val TEAM_ID: String = "TEAM_ID"
    }
}