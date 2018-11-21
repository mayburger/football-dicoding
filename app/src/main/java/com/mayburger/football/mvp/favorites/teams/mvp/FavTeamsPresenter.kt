package com.mayburger.football.mvp.favorites.teams.mvp

import android.content.Context
import com.mayburger.football.base.BasePresenter
import com.mayburger.football.db.TeamFavorites
import com.mayburger.football.db.database
import com.mayburger.football.espresso.EspressoIdlingResource
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


/**
 * Created by Rosinante24 on 10/01/18.
 */

open class FavTeamsPresenter(view: FavTeamsView) : BasePresenter<FavTeamsView>() {
    init {
        attachView(view)
    }

    fun getTeams(ctx: Context) {
        EspressoIdlingResource.increment()
        ctx.database.use {
            val result = select(TeamFavorites.TABLE_TEAMS)
            val favorite = result.parseList(classParser<TeamFavorites>())
            if (!favorite.isEmpty()) {
                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }
                mvpView?.onGetTeams(favorite)
            } else{
                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }
                mvpView?.onDataEmpty()
            }
        }
    }
}
