package com.mayburger.football.mvp.detail

import android.content.Context
import com.mayburger.football.base.BasePresenter
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.db.EventFavorites
import com.mayburger.football.db.database
import com.mayburger.football.espresso.EspressoIdlingResource
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


/**
 * Created by Rosinante24 on 10/01/18.
 */

open class EventsDetailPresenter(viewEvents: EventsDetailView) : BasePresenter<EventsDetailView>() {
    init {
        attachView(viewEvents)
    }

    open fun isFavorite(event: Events.Event, idApi: String, ctx: Context, id: String) {
        ctx.database.use {
            val result = select(EventFavorites.TABLE_EVENTS).whereArgs("(EVENT_ID = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<EventFavorites>())

            if (favorite.isEmpty()) {
                mvpView?.isFavorite(event, false)
            } else {
                val idSqlite = favorite.get(0).event_id.toString()
                mvpView?.isFavorite(event, idApi == idSqlite)
            }
        }
    }

    fun getEvents(soccerRepo: SoccerRepository, id: String) {
        EspressoIdlingResource.increment()
        try {
            soccerRepo.getEvent(object : SoccerDataSource.GetEventsCallback {
                override fun onEventsLoaded(events: List<Events.Event>?) {
                    try {
                        if (events != null) {
                            if (!events.isEmpty()) {
                                mvpView!!.onGetEvent(events[0])
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
            }, id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
