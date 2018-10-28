package com.mayburger.football.mvp.teams

import com.mayburger.football.base.BasePresenter
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Leagues
import com.mayburger.football.data.model.Teams
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.espresso.EspressoIdlingResource


/**
 * Created by Rosinante24 on 10/01/18.
 */

open class TeamsPresenter(view: TeamsView) : BasePresenter<TeamsView>() {
    init {
        attachView(view)
    }

    fun getEvents(soccerRepo: SoccerRepository, id: String) {
        EspressoIdlingResource.increment()
        try {
            soccerRepo.getTeamsByLeague(object : SoccerDataSource.GetTeamsCallback {
                override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                    if (teams != null) {
                        mvpView!!.onGetTeams(teams)
                        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                            EspressoIdlingResource.decrement() // Set app as idle.
                        }
                    }
                }

                override fun onDataNotAvailable() {}
                override fun onError(errorMessage: String) {}
            }, id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getLeagues(soccerRepo: SoccerRepository) {
        EspressoIdlingResource.increment()
        try {
            soccerRepo.getLeagues(object : SoccerDataSource.GetLeaguesCallback {
                override fun onLeaguesLoaded(leagues: List<Leagues.League>?) {
                    try {
                        if (leagues != null) {
                            if (!leagues.isEmpty()) {
                                mvpView!!.onGetLeagues(leagues)
                                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                                    EspressoIdlingResource.decrement() // Set app as idle.
                                }
                            }
                        }
                    } catch (e: Exception) {
                    }
                }

                override fun onDataNotAvailable() {}
                override fun onError(errorMessage: String) {}
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
