package com.mayburger.football.db

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Teams
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast

/**
 * Created by Mayburger on 10/28/18.
 */

open class DatabaseHelper {

    /** The method used to add the data to the ic_favorite database
     *  @param event
     *  Selected data
     */
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

    open fun clearDatabase(ctx: Context) {
        try {
            ctx.database.use {
                clearDatabase(ctx)
            }
        } catch (e: Exception) {
        }
    }


    /** The method used to remove data from the ic_favorite database
     *  @param id
     *  The id used to query the event for deletion
     **/
    open fun removeEventFromFavorite(id: String, ctx: Context) {
        try {
            ctx.database.use {
                delete(EventFavorites.TABLE_EVENTS, "(EVENT_ID = {id})",
                        "id" to id)
            }
        } catch (e: SQLiteConstraintException) {
        }

    }

    /** The method used to add the data to the ic_favorite database
     *  @param team
     *  Selected data
     */
    open fun addTeamToFavorite(team: Teams.Team, ctx: Context) {
        try {
            ctx.database.use {
                insert(TeamFavorites.TABLE_TEAMS,
                        TeamFavorites.TEAM_ID to team.idTeam.toString()
                )
            }
            ctx.toast("Added to ic_favorite")
        } catch (e: Exception) {
            ctx.toast("An error has occured")
        }
    }

    /** The method used to remove data from the ic_favorite database
     *  @param id
     *  The id used to query the event for deletion
     **/
    open fun removeTeamFromFavorite(id: String, ctx: Context) {
        try {
            ctx.database.use {
                delete(TeamFavorites.TABLE_TEAMS, "(TEAM_ID = {id})",
                        "id" to id)
            }
        } catch (e: SQLiteConstraintException) {
        }

    }
}