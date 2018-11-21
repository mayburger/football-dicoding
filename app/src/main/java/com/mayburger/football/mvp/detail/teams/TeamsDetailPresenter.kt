package com.mayburger.football.mvp.detail.teams

import android.content.Context
import com.mayburger.football.base.BasePresenter
import com.mayburger.football.data.model.Teams
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.db.TeamFavorites
import com.mayburger.football.db.database
import com.mayburger.football.espresso.EspressoIdlingResource
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


/**
 * Created by Rosinante24 on 10/01/18.
 */

open class TeamsDetailPresenter(view: TeamsDetailView) : BasePresenter<TeamsDetailView>() {
    init {
        attachView(view)
    }

    open fun isFavorite(name: String, ctx: Context) {
        ctx.database.use {
            val result = select(TeamFavorites.TABLE_TEAMS).whereArgs("(TEAM_NAME = {id})",
                    "id" to name)
            val favorite = result.parseList(classParser<TeamFavorites>())

            if (favorite.isEmpty()) {
                mvpView?.isFavorite(name, false)
            } else {
                val idSqlite = favorite.get(0).team_name.toString()
                mvpView?.isFavorite(name, name == idSqlite)
            }
        }
    }

    fun getTeam(soccerRepo: SoccerRepository, name: String) {
        EspressoIdlingResource.increment()
        try {
            soccerRepo.getTeamsByName(object : SoccerDataSource.GetTeamsCallback {
                override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                    try {
                        if (teams != null) {
                            if (!teams.isEmpty()) {
                                mvpView!!.onGetTeam(teams[0])
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
            }, name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
