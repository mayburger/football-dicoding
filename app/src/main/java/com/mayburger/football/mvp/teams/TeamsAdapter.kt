package com.mayburger.football.mvp.teams

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
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Teams
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.utils.light
import com.mayburger.football.utils.medium
import org.jetbrains.anko.find

/**
 * Created by Mayburger on 9/1/18.
 */

open class TeamsAdapter(var list: List<Teams.Team> = arrayListOf(), var ctx: Context, var repo: SoccerRepository, private val listener: (Teams.Team) -> Unit) : BaseRecyclerAdapter<TeamsAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(ctx).inflate(R.layout.row_teams, parent, false))
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(list.get(position), listener)
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
        fun bindItem(teams: Teams.Team, listener: (Teams.Team) -> Unit) {
            with(text) {
                text = teams.strTeam
                typeface = medium
            }
            with(country) {
                text = teams.strCountry
                typeface = light
            }
            try{
            Glide.with(ctx).load(teams.strTeamBadge).apply(RequestOptions().centerCrop()).into(image)
            }catch (e:Exception){}
            root.setOnClickListener {
                listener(teams)
            }
        }
    }
}