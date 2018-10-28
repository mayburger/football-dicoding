package com.mayburger.football.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter

import com.mayburger.football.base.BaseFragment
import com.mayburger.football.base.BasePresenter
import java.util.ArrayList


/**
 * Created by Mayburger on 10/01/18.
 */

abstract class MvpFragment<P : BasePresenter<*>> : BaseFragment() {

    var mvpPresenter: P? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpPresenter = createPresenter()
    }

    abstract fun createPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        if (mvpPresenter != null) {
            mvpPresenter!!.detachView()
        }
    }
}


