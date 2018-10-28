package com.mayburger.football.base

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.mayburger.football.utils.OnBackPressedFun

/**
* Created by Mayburger on 9/1/18.
*/

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

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

    /** This method is called on the onBackPressed method inside an activity where the onBackPressed
     *  method is required inside a fragment
     *  See [com.mayburger.football.detail.DetailFragment.onBackPressed]
     **/
    fun initBackPressed(activity: AppCompatActivity) {
        OnBackPressedFun().detailFragmentBack(activity)
        OnBackPressedFun().searchFragmentBack(activity)
    }

}
