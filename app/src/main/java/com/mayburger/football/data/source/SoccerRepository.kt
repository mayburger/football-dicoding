package com.mayburger.football.data.source

import com.mayburger.football.data.model.*
import org.jetbrains.annotations.NotNull
import java.net.URL

/**
 * Created by Mayburger on 1/9/18.
 */


class SoccerRepository(private val remoteDataSource: SoccerDataSource) : SoccerDataSource {

    override fun getPlayers(callback: SoccerDataSource.GetPlayersCallback, team: String) {
        remoteDataSource.getPlayers(object : SoccerDataSource.GetPlayersCallback {
            override fun onPlayersLoaded(players: List<Players.Player>?) {
                if (players != null) {
                    callback.onPlayersLoaded(players)
                }
            }

            override fun onDataNotAvailable() {
                /** unused **/
            }

            override fun onError(errorMessage: String) {
                /** unused **/
            }
        }, team)
    }

    override fun getTeamsByLeague(callback: SoccerDataSource.GetTeamsCallback, league: String) {
        remoteDataSource.getTeamsByLeague(object : SoccerDataSource.GetTeamsCallback {
            override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                if (teams != null) {
                    callback.onTeamsLoaded(teams)
                }
            }

            override fun onDataNotAvailable() {
                /** unused **/
            }

            override fun onError(errorMessage: String) {
                /** unused **/
            }
        }, league)
    }

    override fun getLeagues(callback: SoccerDataSource.GetLeaguesCallback) {
        remoteDataSource.getLeagues(object : SoccerDataSource.GetLeaguesCallback {
            override fun onLeaguesLoaded(leagues: List<Leagues.League>?) {
                if (leagues != null) {
                    if (!leagues.isEmpty()) {
                        callback.onLeaguesLoaded(leagues)
                    }
                }
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onError(errorMessage: String) {
                /** unused **/
            }
        })
    }

    override fun getEventByQuery(callback: SoccerDataSource.GetEventsSearchCallback, name: String) {
        remoteDataSource.getEventByQuery(object : SoccerDataSource.GetEventsSearchCallback {
            override fun onEventsLoaded(events: List<Search.Event>?) {
                if (events != null) {
                    if (!events.isEmpty()) {
                        callback.onEventsLoaded(events)
                    }
                }
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onError(errorMessage: String) {
                /** unused **/
            }
        }, name)
    }

    override fun getEvent(callback: SoccerDataSource.GetEventsCallback, id: String) {
        remoteDataSource.getEvent(object : SoccerDataSource.GetEventsCallback {
            override fun onEventsLoaded(events: List<Events.Event>?) {
                if (events != null) {
                    if (!events.isEmpty()) {
                        callback.onEventsLoaded(events)
                    }
                }
            }

            override fun onDataNotAvailable() {
                /** unused **/
            }

            override fun onError(errorMessage: String) {
                /** unused **/
            }
        }, id)
    }

    override fun getTeamsByName(callback: SoccerDataSource.GetTeamsCallback, name: String) {
        remoteDataSource.getTeamsByName(object : SoccerDataSource.GetTeamsCallback {
            override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                if (teams != null) {
                    callback.onTeamsLoaded(teams)
                }
            }

            override fun onDataNotAvailable() {
                /** unused **/
            }

            override fun onError(errorMessage: String) {
                /** unused **/
            }
        }, name)
    }

    override fun getPrev(callback: SoccerDataSource.GetEventsCallback, @NotNull id: String) {
        remoteDataSource.getPrev(object : SoccerDataSource.GetEventsCallback {
            override fun onEventsLoaded(events: List<Events.Event>?) {
                if (events != null) {
                    if (!events.isEmpty()) {
                        callback.onEventsLoaded(events)
                    }
                }
            }

            override fun onDataNotAvailable() {
                /** unused **/
            }

            override fun onError(errorMessage: String) {
                /** unused **/
            }
        }, id)
    }

    override fun getNext(callback: SoccerDataSource.GetEventsCallback, @NotNull id: String) {
        remoteDataSource.getNext(object : SoccerDataSource.GetEventsCallback {
            override fun onEventsLoaded(events: List<Events.Event>?) {
                if (events != null) {
                    if (!events.isEmpty()) {
                        callback.onEventsLoaded(events)
                    }
                }
            }

            override fun onDataNotAvailable() {
                /** unused **/
            }

            override fun onError(errorMessage: String) {
                /** unused **/
            }
        }, id)
    }

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}
