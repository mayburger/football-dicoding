package com.mayburger.football.mvp.search.teams.mvp

import com.mayburger.football.base.BasePresenter
import com.mayburger.football.data.model.Search
import com.mayburger.football.data.model.Teams
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.espresso.EspressoIdlingResource


/**
 * Created by Rosinante24 on 10/01/18.
 */

open class SearchTeamsPresenter(view: SearchTeamsView) : BasePresenter<SearchTeamsView>() {
    init {
        attachView(view)
    }

    fun getEvents(soccerRepo: SoccerRepository, name: String) {
        EspressoIdlingResource.increment()
        try {
            soccerRepo.getTeamsByName(object : SoccerDataSource.GetTeamsCallback {
                override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                    try {
                        if (teams != null) {
                            if (!teams.isEmpty()) {
                                mvpView!!.onGetTeams(teams)
                                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                                    EspressoIdlingResource.decrement() // Set app as idle.
                                }
                            }
                        }
                    } catch (e: Exception) {
                    }
                }

                override fun onDataNotAvailable() {
                    mvpView!!.onGetTeamsNotAvailable()
                }

                override fun onError(errorMessage: String) {}
            }, name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
