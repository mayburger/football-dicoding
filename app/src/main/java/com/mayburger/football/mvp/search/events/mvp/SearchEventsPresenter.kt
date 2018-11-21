package com.mayburger.football.mvp.search.events.mvp

import com.mayburger.football.base.BasePresenter
import com.mayburger.football.data.model.Search
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.espresso.EspressoIdlingResource


/**
 * Created by Rosinante24 on 10/01/18.
 */

open class SearchEventsPresenter(view: SearchEventsView) : BasePresenter<SearchEventsView>() {
    init {
        attachView(view)
    }

    fun getEvents(soccerRepo: SoccerRepository,query:String) {
        EspressoIdlingResource.increment()
        try {
            soccerRepo.getEventByQuery(object : SoccerDataSource.GetEventsSearchCallback {
                override fun onEventsLoaded(events: List<Search.Event>?) {
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

                override fun onDataNotAvailable() {
                    mvpView!!.onGetEventsNotAvailable()
                }
                override fun onError(errorMessage: String) {}
            }, query)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
