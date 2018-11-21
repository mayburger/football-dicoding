package com.mayburger.football.db

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.mayburger.football.data.model.Events
import com.mayburger.football.db.EventFavorites.Companion.EVENT_ID
import com.mayburger.football.db.TeamFavorites.Companion.TEAM_NAME
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast

/**
 * Created by Mayburger on 10/28/18.
 */

open class DatabaseHelper {

    open fun addEventToFavorite(event: Events.Event, ctx: Context) {
        try {
            ctx.database.use {
                insert(EventFavorites.TABLE_EVENTS,
                        EventFavorites.EVENT_ID to event.idEvent.toString()
                )
            }
            ctx.toast("Added to ic_favorite")
        } catch (e: Exception) {
            ctx.toast("An error has occured")
        }
    }

    open fun removeEventFromFavorite(id: String, ctx: Context) {
        try {
            ctx.database.use {
                delete(EventFavorites.TABLE_EVENTS, "(" + EVENT_ID + " = {id})",
                        "id" to id)
            }
        } catch (e: SQLiteConstraintException) {
        }
    }

    open fun addTeamToFavorite(name:String, ctx: Context) {
        try {
            ctx.database.use {
                insert(TeamFavorites.TABLE_TEAMS,
                        TeamFavorites.TEAM_NAME to name
                )
            }
            ctx.toast("Added to ic_favorite")
        } catch (e: Exception) {
            ctx.toast("An error has occured")
        }
    }

    open fun removeTeamFromFavorite(name: String, ctx: Context) {
        try {
            ctx.database.use {
                delete(TeamFavorites.TABLE_TEAMS, "(" + TEAM_NAME + " = {name})",
                        "name" to name)
            }
        } catch (e: SQLiteConstraintException) {
        }
    }
}