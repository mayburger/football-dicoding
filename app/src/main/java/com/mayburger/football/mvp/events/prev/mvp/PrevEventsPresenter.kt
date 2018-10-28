package com.mayburger.football.mvp.events.prev.mvp

import com.mayburger.football.base.BasePresenter
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Leagues
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.espresso.EspressoIdlingResource


/**
 * Created by Rosinante24 on 10/01/18.
 */

open class PrevEventsPresenter(view: PrevEventsView) : BasePresenter<PrevEventsView>() {
    init {
        attachView(view)
    }

    fun getEvents(soccerRepo: SoccerRepository, league:String) {
        EspressoIdlingResource.increment()
        try {
            soccerRepo.getPrev(object : SoccerDataSource.GetEventsCallback {
                override fun onEventsLoaded(events: List<Events.Event>?) {
                    try {
                        if (events != null) {
                            if (!events.isEmpty()) {
                                mvpView!!.onGetEvents(events)
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
            }, league)
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
