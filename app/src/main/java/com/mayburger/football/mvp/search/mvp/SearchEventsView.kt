package com.mayburger.football.mvp.search.mvp

import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Search


/**
 * Created by Mayburger on 10/01/18.
 */

interface SearchEventsView {
    fun onGetEvents(events: List<Search.Event>)
    fun onGetEventsNotAvailable()
}
