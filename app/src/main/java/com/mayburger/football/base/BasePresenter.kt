package com.mayburger.football.base

import android.app.Activity
import android.content.Context
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Teams
import com.mayburger.football.db.DatabaseHelper
import com.mayburger.football.utils.KeyboardUtils


/**
 * Created by Mayburger on 02/02/2018.
 */

open class BasePresenter<V> {

    var mvpView: V? = null

    fun attachView(mvpView: V) {
        this.mvpView = mvpView
    }

    fun detachView() {
        this.mvpView = null
    }

    fun addEvent(event: Events.Event, ctx: Context) {
        DatabaseHelper().addEventToFavorite(event, ctx)
    }

    fun removeEvent(id: String, ctx: Context) {
        DatabaseHelper().removeEventFromFavorite(id, ctx)
    }

    fun addTeam(name: String, ctx: Context) {
        DatabaseHelper().addTeamToFavorite(name, ctx)
    }

    fun removeTeam(name: String, ctx: Context) {
        DatabaseHelper().removeTeamFromFavorite(name, ctx)
    }

    fun showInput(act: Activity) {
        KeyboardUtils().showInputMethod(act)
    }

    fun hideInput(act: Activity) {
        KeyboardUtils().hideInputMethod(act)
    }

}