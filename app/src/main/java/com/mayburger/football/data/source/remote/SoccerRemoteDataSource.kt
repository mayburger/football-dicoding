package com.mayburger.football.data.source.remote

import android.content.Context
import com.mayburger.football.data.source.SoccerDataSource

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import org.jetbrains.annotations.NotNull
import kotlin.coroutines.experimental.coroutineContext


/**
 * Created by Mayburger on 1/9/18.
 */

class SoccerRemoteDataSource : SoccerDataSource {
    override fun getTeamsByLeague(callback: SoccerDataSource.GetTeamsCallback, league: String) {
        apiService.getTeamsByLeague(
                league)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { results ->
                    if (results != null) {
                        callback.onTeamsLoaded(results.teams!!)
                    } else {
                        callback.onDataNotAvailable()
                    }
                }
    }

    override fun getLeagues(callback: SoccerDataSource.GetLeaguesCallback) {
        apiService.getLeagues()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { results ->
                    if (results != null) {
                        try {
                            callback.onLeaguesLoaded(results.leagues!!)
                        } catch (e: Exception) {
                            callback.onDataNotAvailable()
                        }
                    } else {
                        callback.onDataNotAvailable()
                    }
                }
    }

    override fun getEventByQuery(callback: SoccerDataSource.GetSearchCallback, name: String) {
        apiService.onQueryEvent(
                name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { results ->
                    if (results != null) {
                        try {
                            callback.onEventsLoaded(results.event!!)
                        } catch (e: Exception) {
                            callback.onDataNotAvailable()
                        }
                    } else {
                        callback.onDataNotAvailable()
                    }
                }
    }


    override fun getEvent(callback: SoccerDataSource.GetEventsCallback, id: String) {
        apiService.getEvent(
                id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { results ->
                    if (results != null) {
                        callback.onEventsLoaded(results.events!!)
                    } else {
                        callback.onDataNotAvailable()
                    }
                }
    }

    // Instance of the SoccerService
    private val apiService = SoccerService.Factory.create()

    /**
     * The method used to get the data for the last 15 events
     * @param callback
     * The interface which implements onDataLoaded, onDataNotAvailable and onError
     * @param id
     * The id of the league
     **/
    override fun getPrev(callback: SoccerDataSource.GetEventsCallback, @NotNull id: String) {
        apiService.getPrev(
                id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { results ->
                    if (results != null) {
                        callback.onEventsLoaded(results.events!!)
                    } else {
                        callback.onDataNotAvailable()
                    }
                }
    }

    /**
     * The method used to get the data for the last 15 events
     * @param callback
     * The interface which implements onDataLoaded, onDataNotAvailable and onError
     * @param id
     * The id of the league
     **/
    override fun getNext(callback: SoccerDataSource.GetEventsCallback, @NotNull id: String) {
        apiService.getNext(
                id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { results ->
                    if (results != null) {
                        callback.onEventsLoaded(results.events!!)
                    } else {
                        callback.onDataNotAvailable()
                    }
                }
    }

    /**
     * The method used to get the data for the last 15 events
     * @param callback
     * The interface which implements onDataLoaded, onDataNotAvailable and onError
     * @param name
     * The name of the team
     **/
    override fun getTeamsByName(callback: SoccerDataSource.GetTeamsCallback, name: String) {
        apiService.getTeams(
                name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { results ->
                    if (results != null) {
                        callback.onTeamsLoaded(results.teams!!)
                    } else {
                        callback.onDataNotAvailable()
                    }
                }
    }

}
