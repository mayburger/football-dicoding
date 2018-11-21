package com.mayburger.football.mvp.favorites.teams

import android.annotation.SuppressLint
import android.content.Context
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
import com.mayburger.football.data.model.Teams
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.db.TeamFavorites
import com.mayburger.football.utils.*
import org.jetbrains.anko.find

/**
 * Created by Mayburger on 9/1/18.
 */

class FavTeamsAdapter(var list: List<TeamFavorites> = arrayListOf(), var ctx: Context, var repo: SoccerRepository, private val listener: (Teams.Team) -> Unit) : BaseRecyclerAdapter<FavTeamsAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(ctx).inflate(R.layout.row_teams, parent, false))
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(list[position], listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.find(R.id.image)
        private val text: TextView = view.find(R.id.text)
        private val country: TextView = view.find(R.id.country)
        private val root: LinearLayout = view.find(R.id.root)

        @SuppressLint("SetTextI18n")
        fun bindItem(teams: TeamFavorites, listener: (Teams.Team) -> Unit) {
            repo.getTeamsByName(object : SoccerDataSource.GetTeamsCallback {
                override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                    if (teams != null) {
                        with(text) {
                            text = teams[0].strTeam
                            typeface = medium
                        }
                        with(country) {
                            text = teams[0].strCountry
                            typeface = light
                        }
                        Glide.with(ctx).load(teams.get(0).strTeamBadge).apply(RequestOptions().centerCrop()).into(image)
                        root.setOnClickListener {
                            listener(teams[0])
                        }
                    }
                }

                override fun onDataNotAvailable() {}
                override fun onError(errorMessage: String) {}

            }, teams.team_name.toString())
        }
    }
}