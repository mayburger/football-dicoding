package com.mayburger.football.base

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.data.source.remote.SoccerRemoteDataSource
import com.mayburger.football.db.EventFavorites
import com.mayburger.football.db.TeamFavorites
import com.mayburger.football.db.database
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


/**
 * Created by Mayburger on 9/1/18.
 */

open class BaseFragment : Fragment() {

    open fun isNetworkAvailable(): Boolean {
        val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    /** Instance of the SoccerRepository **/
    open fun repoInstance(): SoccerRepository {
        val eventsRemoteDataSource = SoccerRemoteDataSource()
        return SoccerRepository(eventsRemoteDataSource)
    }

    /** The method used to add another fragment on top of a fragment
     *  @param activity
     *  Instance of the AppCompatActivity. Required to get SupportFragmentManager instance
     *  @param fragment
     *  Selected fragment
     *  @param layout
     *  The integer id of the frame layout
     */
    fun addFragmentOnTop(activity: AppCompatActivity, fragment: Fragment, layout: Int) {
        activity.supportFragmentManager
                .beginTransaction()
                .add(layout, fragment)
                .addToBackStack(null)
                .commit()
    }

    /** The method used to replace existing fragment with another one
     *  @param activity
     *  Instance of the AppCompatActivity. Required to get SupportFragmentManager instance
     *  @param fragment
     *  Selected fragment
     *  @param layout
     *  The integer id of the frame layout
     */
    fun replaceFragment(activity: AppCompatActivity, fragment: Fragment, layout: Int) {
        activity.supportFragmentManager
                .beginTransaction()
                .replace(layout, fragment)
                .commit()
    }


}
