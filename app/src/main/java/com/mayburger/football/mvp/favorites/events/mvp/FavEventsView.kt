package com.mayburger.football.mvp.events.next.mvp

import com.mayburger.football.db.EventFavorites


/**
 * Created by Mayburger on 10/01/18.
 */

interface FavEventsView {
    fun onGetEvents(events: List<EventFavorites>)
    fun onDataEmpty()
}
