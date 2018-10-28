package com.mayburger.football.base


import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.provider.SyncStateContract.Helpers.insert
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Teams
import com.mayburger.football.db.DatabaseHelper
import com.mayburger.football.db.EventFavorites
import com.mayburger.football.db.database
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

/**
 * Created by Mayburger on 1/11/18.
 */

open class BaseRecyclerAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    fun addEvent(event: Events.Event, ctx: Context) {
        DatabaseHelper().addEventToFavorite(event, ctx)
    }

    fun removeEvent(id: String, ctx: Context) {
        DatabaseHelper().removeEventFromFavorite(id, ctx)
    }

    fun addTeam(team: Teams.Team, ctx: Context) {
        DatabaseHelper().addTeamToFavorite(team, ctx)
    }

    fun removeTeam(id: String, ctx: Context) {
        DatabaseHelper().removeTeamFromFavorite(id, ctx)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return null!!
    }


    override fun onBindViewHolder(holder: T, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }
}
