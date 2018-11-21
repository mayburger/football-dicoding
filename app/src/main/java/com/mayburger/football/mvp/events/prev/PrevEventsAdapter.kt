package com.mayburger.football.mvp.events.prev

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mayburger.football.R
import com.mayburger.football.base.BaseRecyclerAdapter
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Teams
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.db.EventFavorites
import com.mayburger.football.db.database
import com.mayburger.football.utils.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by Mayburger on 9/1/18.
 */

open class PrevEventsAdapter(var list: List<Events.Event> = arrayListOf(), var ctx: Context, var repo: SoccerRepository, private val listener: (Events.Event) -> Unit) : BaseRecyclerAdapter<PrevEventsAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(ctx).inflate(R.layout.row_prev, parent, false))
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(list.get(position), listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val logoHome: ImageView = view.find(R.id.logoHome)
        private val logoAway: ImageView = view.find(R.id.logoAway)
        private val homeScore: TextView = view.find(R.id.homeScore)
        private val awayScore: TextView = view.find(R.id.awayScore)
        private val dash: TextView = view.find(R.id.dash)
        private val date: TextView = view.find(R.id.date)
        private val name: TextView = view.find(R.id.name)

        private val favorite: CardView = view.find(R.id.favorite)
        private val favorite_text: TextView = view.find(R.id.favorite_text)
        private val unfavorite: CardView = view.find(R.id.unfavorite)
        private val unfavorite_text: TextView = view.find(R.id.unfavorite_text)
        private val root: LinearLayout = view.find(R.id.root)

        @SuppressLint("SetTextI18n")
        fun bindItem(events: Events.Event, listener: (Events.Event) -> Unit) {
            try {
                setTeamsLogo(events.strAwayTeam!!, logoAway)
                setTeamsLogo(events.strHomeTeam!!, logoHome)
            } catch (e: Exception) {
            }

            with(homeScore) {
                if (!events.intHomeScore.toString().equals("null")) {
                    text = events.intHomeScore.toString()
                } else {
                    text = "?"
                }
                typeface = bold
            }
            with(awayScore) {
                if (!events.intAwayScore.toString().equals("null")) {
                    text = events.intAwayScore.toString()
                } else {
                    text = "?"
                }
                typeface = bold
            }
            with(dash) {
                typeface = bold
            }
            with(date)
            {
                try {
                    if (events.strDate != null) {
                        @SuppressLint("SimpleDateFormat") val strDate = SimpleDateFormat("dd-mm-yyyy").format(SimpleDateFormat("dd/mm/yy").parse(events.strDate))
                        text = "Played On " + strDate
                    } else {
                        @SuppressLint("SimpleDateFormat") val strDate = SimpleDateFormat("dd-mm-yyyy").format(SimpleDateFormat("yyyy-mm-dd").parse(events.dateEvent))
                        text = "Played On " + strDate
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                typeface = medium
            }
            with(name) {
                text = events.strHomeTeam + "-" + events.strAwayTeam
                typeface = medium
            }

            ctx.database.use {
                val result = select(EventFavorites.TABLE_EVENTS).whereArgs("(EVENT_ID = {id})",
                        "id" to events.idEvent.toString())
                val data = result.parseList(classParser<EventFavorites>())

                if (data.isEmpty()) {
                    unfavorite.visibility = View.GONE
                    favorite.visibility = View.VISIBLE
                } else {
                    unfavorite.visibility = View.VISIBLE
                    favorite.visibility = View.GONE
                }
            }

            favorite.setOnClickListener {
                events.typeEvent = Constants.PREV_EVENTS
                addEvent(events, ctx)
                unfavorite.visibility = View.VISIBLE
                favorite.visibility = View.GONE
            }
            with(favorite_text) {
                typeface = light
            }
            unfavorite.setOnClickListener {
                removeEvent(events.idEvent.toString(), ctx)
                unfavorite.visibility = View.GONE
                favorite.visibility = View.VISIBLE
            }
            with(unfavorite_text) {
                typeface = light
            }
            root.setOnClickListener {
                listener(events)
            }
        }
    }


    fun setTeamsLogo(name: String, image: ImageView) {
        repo.getTeamsByName(object : SoccerDataSource.GetTeamsCallback {
            override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                if (teams != null) {
                    try {
                        Glide.with(ctx).load(teams.get(0).strTeamBadge).apply(RequestOptions().centerCrop()).into(image)
                    }catch (e:Exception){}
                }
            }

            override fun onDataNotAvailable() {}
            override fun onError(errorMessage: String) {}

        }, name)
    }

}