package com.mayburger.football.mvp.events.prev.mvp

import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Leagues


/**
 * Created by Mayburger on 10/01/18.
 */

interface PrevEventsView {
    fun onGetEvents(events: List<Events.Event>)
    fun onGetLeagues(leagues: List<Leagues.League>)
}
