package com.mayburger.football.mvp.detail.teams.content

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mayburger.football.R
import com.mayburger.football.base.BaseFragment
import com.mayburger.football.data.model.Teams
import com.mayburger.football.utils.regular
import kotlinx.android.synthetic.main.fragment_overview.*

/**
 * Created by Mayburger on 10/31/18.
 */


@SuppressLint("ValidFragment")
open class OverviewFragment @SuppressLint("ValidFragment") constructor
(var teams: Teams.Team) : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(text) {
            typeface = regular
            text = teams.strDescriptionEN
        }
    }
}

