package com.mayburger.football.data.source

import android.content.Context
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Leagues
import com.mayburger.football.data.model.Search
import com.mayburger.football.data.model.Teams
import org.jetbrains.annotations.NotNull

/**
 * Created by Mayburger on 1/9/18.
 */

interface SoccerDataSource {

    fun getPrev(callback: GetEventsCallback, @NotNull id: String)
    fun getNext(callback: GetEventsCallback, @NotNull id: String)
    fun getEvent(callback: GetEventsCallback, @NotNull id: String)
    fun getEventByQuery(callback: GetSearchCallback, @NotNull name: String)
    fun getTeamsByName(callback: GetTeamsCallback, @NotNull name: String)
    fun getLeagues(callback: GetLeaguesCallback)
    fun getTeamsByLeague(callback: GetTeamsCallback, @NotNull league: String)

    interface GetEventsCallback {
        fun onEventsLoaded(events: List<Events.Event>?)
        fun onDataNotAvailable()
        fun onError(errorMessage: String)
    }

    interface GetSearchCallback {
        fun onEventsLoaded(events: List<Search.Event>?)
        fun onDataNotAvailable()
        fun onError(errorMessage: String)
    }

    interface GetTeamsCallback {
        fun onTeamsLoaded(teams: List<Teams.Team>?)
        fun onDataNotAvailable()
        fun onError(errorMessage: String)
    }

    interface GetLeaguesCallback {
        fun onLeaguesLoaded(leagues: List<Leagues.League>?)
        fun onDataNotAvailable()
        fun onError(errorMessage: String)
    }
}
