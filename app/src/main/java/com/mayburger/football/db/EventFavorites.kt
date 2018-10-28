package com.mayburger.football.db

import java.io.Serializable

/**
 * Created by Mayburger on 9/24/18.
 */

data class EventFavorites(
        val event_id: String?

) : Serializable {

    companion object {
        const val TABLE_EVENTS: String = "TABLE_EVENTS"
        const val EVENT_ID: String = "EVENT_ID"
    }
}