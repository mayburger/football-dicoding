package com.mayburger.football.mvp.events.next.mvp

import android.content.Context
import com.mayburger.football.base.BasePresenter
import com.mayburger.football.db.EventFavorites
import com.mayburger.football.db.database
import com.mayburger.football.espresso.EspressoIdlingResource
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


/**
 * Created by Rosinante24 on 10/01/18.
 */

open class FavEventsPresenter(view: FavEventsView) : BasePresenter<FavEventsView>() {
    init {
        attachView(view)
    }

    fun getEvents(ctx: Context) {
        EspressoIdlingResource.increment()
        ctx.database.use {
            val result = select(EventFavorites.TABLE_EVENTS)
            val favorite = result.parseList(classParser<EventFavorites>())
            if (!favorite.isEmpty()) {
                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }
                mvpView?.onGetEvents(favorite)
            } else{
                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }
                mvpView?.onDataEmpty()
            }
        }
    }
}
