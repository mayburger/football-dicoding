package com.mayburger.football.mvp.detail.teams.content

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
import com.mayburger.football.data.model.Players
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.utils.light
import com.mayburger.football.utils.medium
import org.jetbrains.anko.find

/**
 * Created by Mayburger on 9/1/18.
 */

open class PlayersAdapter(var list: List<Players.Player> = arrayListOf(), var ctx: Context, var repo: SoccerRepository, private val listener: (Players.Player) -> Unit) : BaseRecyclerAdapter<PlayersAdapter.PlayersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        return PlayersViewHolder(LayoutInflater.from(ctx).inflate(R.layout.row_players, parent, false))
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        holder.bindItem(list.get(position), listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PlayersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.find(R.id.image)
        private val name: TextView = view.find(R.id.name)
        private val position: TextView = view.find(R.id.position)
        private val root: LinearLayout = view.find(R.id.root)

        @SuppressLint("SetTextI18n")
        fun bindItem(teams: Players.Player, listener: (Players.Player) -> Unit) {
            with(name) {
                text = teams.strPlayer
                typeface = medium
            }
            with(position) {
                text = teams.strPosition
                typeface = light
            }
            if (teams.strCutout != null) {
                Glide.with(ctx).load(teams.strCutout).apply(RequestOptions().centerCrop()).into(image)
            }
            root.setOnClickListener {
                listener(teams)
            }
        }
    }
}