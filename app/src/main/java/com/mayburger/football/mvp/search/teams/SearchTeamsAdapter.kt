package com.mayburger.football.mvp.search.teams

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
import com.mayburger.football.data.model.Search
import com.mayburger.football.data.model.Teams
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.espresso.EspressoIdlingResource
import com.mayburger.football.utils.*
import org.jetbrains.anko.find
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by Mayburger on 9/1/18.
 */

class SearchTeamsAdapter(var list: List<Search.Event> = arrayListOf(), var ctx: Context, var repo: SoccerRepository, private val listener: (Search.Event) -> Unit) : BaseRecyclerAdapter<SearchTeamsAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(ctx).inflate(R.layout.row_events_fav, parent, false))
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(list[position], listener)
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

        private val name: TextView = view.find(R.id.name)
        private val root: LinearLayout = view.find(R.id.rootId)

        fun bindItem(event: Search.Event, listener: (Search.Event) -> Unit) {

            root.visibility = View.VISIBLE

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
            }

            with(name) {
                text = event.strHomeTeam + "-" + event.strAwayTeam
                typeface = medium
            }

            with(date) {
                typeface = regular
                if (event.strDate != null) {
                    try {
                        @SuppressLint("SimpleDateFormat") val strDate = SimpleDateFormat("dd-mm-yyyy").format(SimpleDateFormat("dd/mm/yy").parse(event.strDate))
                        text = strDate
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }
            }
            setTeamsLogo(event.strAwayTeam!!, logoAway)
            setTeamsLogo(event.strHomeTeam!!, logoHome)

            root.setOnClickListener {
                listener(event)
            }

            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement() // Set app as idle.
            }
        }
    }

    fun setTeamsLogo(name: String, image: ImageView) {
        repo.getTeamsByName(object : SoccerDataSource.GetTeamsCallback {
            override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                if (teams != null) {
                    try{
                    Glide.with(ctx).load(teams.get(0).strTeamBadge).apply(RequestOptions().centerCrop()).into(image)
                    }catch (e:Exception){}
                }
            }

            override fun onDataNotAvailable() {}
            override fun onError(errorMessage: String) {}

        }, name)
    }
}