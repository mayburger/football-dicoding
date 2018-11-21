package com.mayburger.football.mvp.detail.events

import com.mayburger.football.data.model.Events


/**
 * Created by Mayburger on 10/01/18.
 */

interface EventsDetailView {
    fun isFavorite(event: Events.Event, isFavorite: Boolean)
    fun onGetEvent(event: Events.Event)
}
