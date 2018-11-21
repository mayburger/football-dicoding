package com.mayburger.football.mvp.events.next.mvp

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mayburger.football.R
import com.mayburger.football.base.BasePresenter
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Leagues
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.espresso.EspressoIdlingResource
import org.jetbrains.anko.toast


/**
 * Created by Rosinante24 on 10/01/18.
 */

open class NextEventsPresenter(view: NextEventsView) : BasePresenter<NextEventsView>() {
    init {
        attachView(view)
    }

    fun getEvents(soccerRepo: SoccerRepository, id: String) {
        try {
            soccerRepo.getNext(object : SoccerDataSource.GetEventsCallback {
                override fun onEventsLoaded(events: List<Events.Event>?) {
                    if (events != null) {
                        mvpView!!.onGetEvents(events)
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

    fun getEventsData(id: String, isNetworkAvailable: Boolean, recycler: RecyclerView, swipeRefresh: SwipeRefreshLayout, repo: SoccerRepository,ctx:Context) {
        if (isNetworkAvailable) {
            getEvents(repo, id)
            recycler.visibility = View.GONE
            swipeRefresh.isRefreshing = true
            swipeRefresh.setColorSchemeColors(ContextCompat.getColor(ctx, R.color.colorAccent))
        } else {
            ctx.toast("Please check your internet connection!")
        }

        swipeRefresh.setOnRefreshListener {
            if (isNetworkAvailable) {
                getEvents(repo, id)
                recycler.visibility = View.GONE
                swipeRefresh.isRefreshing = true
            } else {
                ctx.toast("Please check your internet connection!")
            }
        }
    }

}
