package com.mayburger.football.mvp.events.fav.events

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
import com.mayburger.football.espresso.EspressoIdlingResource
import com.mayburger.football.utils.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by Mayburger on 9/1/18.
 */

class FavEventsAdapter(var list: List<EventFavorites> = arrayListOf(), var ctx: Context, var repo: SoccerRepository, private val listener: (Events.Event) -> Unit) : BaseRecyclerAdapter<FavEventsAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(ctx).inflate(R.layout.row_fav, parent, false))
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

        // Center Data
        private val homeScore: TextView = view.find(R.id.homeScore)
        private val awayScore: TextView = view.find(R.id.awayScore)
        private val dash: TextView = view.find(R.id.dash)
        private val vs: TextView = view.find(R.id.vs)
        private val date: TextView = view.find(R.id.date)

        private val detail: CardView = view.find(R.id.detail)
        private val detail_text: TextView = view.find(R.id.detail_text)
        private val name: TextView = view.find(R.id.name)
        private val root:LinearLayout = view.find(R.id.rootId)

        fun bindItem(eventFavorites: EventFavorites, listener: (Events.Event) -> Unit) {
            EspressoIdlingResource.increment()
            try {
                repo.getEvent(object : SoccerDataSource.GetEventsCallback {
                    override fun onEventsLoaded(events: List<Events.Event>?) {
                        try {
                            if (events != null) {
                                if (!events.isEmpty()) {
                                    root.visibility = View.VISIBLE
                                    val event:Events.Event = events[0]

                                    if (event.intAwayScore != null) {
                                        homeScore.visibility = View.VISIBLE
                                        awayScore.visibility = View.VISIBLE
                                        dash.visibility = View.VISIBLE
                                        vs.visibility = View.GONE
                                        with(homeScore) {
                                            if (event.intHomeScore.toString() != "null") {
                                                text = event.intHomeScore.toString()
                                            } else {
                                                text = "?"
                                            }
                                            typeface = bold
                                        }
                                        with(awayScore) {
                                            if (event.intAwayScore.toString() != "null") {
                                                text = event.intAwayScore.toString()
                                            } else {
                                                text = "?"
                                            }
                                            typeface = bold
                                        }
                                        with(dash) {
                                            typeface = bold
                                        }
                                    } else if (event.intAwayScore == null) {
                                        homeScore.visibility = View.GONE
                                        awayScore.visibility = View.GONE
                                        dash.visibility = View.GONE
                                        vs.visibility = View.VISIBLE
                                        with(vs) {
                                            typeface = bold
                                        }
                                    } else{
                                        ctx.toast("wtf")
                                    }

                                    with(name) {
                                        text = event.strHomeTeam + "-" + event.strAwayTeam
                                        typeface = medium
                                    }
                                    setTeamsLogo(event.strAwayTeam!!, logoAway)
                                    setTeamsLogo(event.strHomeTeam!!, logoHome)

                                    detail.setOnClickListener {
                                        listener(event)
                                    }
                                    with(detail_text) {
                                        typeface = light
                                    }

                                    with(date)
                                    {
                                        try {
                                            if (event.strDate != null) {
                                                @SuppressLint("SimpleDateFormat") val strDate = SimpleDateFormat("dd-mm-yyyy").format(SimpleDateFormat("dd/mm/yy").parse(event.strDate))
                                                text = "Played On " + strDate
                                            } else {
                                                @SuppressLint("SimpleDateFormat") val strDate = SimpleDateFormat("dd-mm-yyyy").format(SimpleDateFormat("yyyy-mm-dd").parse(event.dateEvent))
                                                text = "Played On " + strDate
                                            }
                                        } catch (e: ParseException) {
                                            e.printStackTrace()
                                        }
                                        typeface = medium
                                    }

                                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                                        EspressoIdlingResource.decrement() // Set app as idle.
                                    }
                                }
                            }
                        } catch (e: Exception) {
                        }
                    }

                    override fun onDataNotAvailable() {}
                    override fun onError(errorMessage: String) {}
                }, eventFavorites.event_id.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setTeamsLogo(name: String, image: ImageView) {
        repo.getTeamsByName(object : SoccerDataSource.GetTeamsCallback {
            override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                if (teams != null) {
                    Glide.with(ctx).load(teams.get(0).strTeamBadge).apply(RequestOptions().centerCrop()).into(image)
                }
            }

            override fun onDataNotAvailable() {}
            override fun onError(errorMessage: String) {}

        }, name)
    }
}