package com.mayburger.football.mvp.detail.field

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mayburger.football.R
import com.mayburger.football.utils.Constants.DEFENSE
import com.mayburger.football.utils.Constants.FORWARD
import com.mayburger.football.utils.Constants.MIDFIELD
import com.mayburger.football.utils.medium
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource

/**
 * Created by Mayburger on 9/1/18.
 */

class FieldAdapter(var players: Array<String>?, var type: String) : RecyclerView.Adapter<FieldAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.row_field_players, parent, false))
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(players?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return players?.size!!
    }

    inner class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.find(R.id.image)
        private val text: TextView = view.find(R.id.text)

        fun bindItem(player: String) {
            with(text) {
                text = player
                typeface = medium
            }
            if (type.equals(FORWARD)) {
                image.imageResource = R.drawable.ic_forward
            } else if (type.equals(MIDFIELD)) {
                image.imageResource = R.drawable.ic_midfield
            } else if (type.equals(DEFENSE)) {
                image.imageResource = R.drawable.ic_defense
            }

        }
    }

}