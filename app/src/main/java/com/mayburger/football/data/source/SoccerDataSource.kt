package com.mayburger.football.data.source

import com.mayburger.football.data.model.*
import org.jetbrains.annotations.NotNull

/**
 * Created by Mayburger on 1/9/18.
 */

interface SoccerDataSource {

    fun getPrev(callback: GetEventsCallback, @NotNull id: String)
    fun getNext(callback: GetEventsCallback, @NotNull id: String)
    fun getEvent(callback: GetEventsCallback, @NotNull id: String)
    fun getEventByQuery(callback: GetEventsSearchCallback, @NotNull name: String)
    fun getTeamsByName(callback: GetTeamsCallback, @NotNull name: String)
    fun getLeagues(callback: GetLeaguesCallback)
    fun getTeamsByLeague(callback: GetTeamsCallback, @NotNull league: String)
    fun getPlayers(callback: GetPlayersCallback, @NotNull team: String)

    interface GetEventsCallback {
        fun onEventsLoaded(events: List<Events.Event>?)
        fun onDataNotAvailable()
        fun onError(errorMessage: String)
    }

    interface GetPlayersCallback {
        fun onPlayersLoaded(players: List<Players.Player>?)
        fun onDataNotAvailable()
        fun onError(errorMessage: String)
    }

    interface GetEventsSearchCallback {
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
