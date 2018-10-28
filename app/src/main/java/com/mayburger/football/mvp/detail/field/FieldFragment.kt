package com.mayburger.football.mvp.detail.field


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mayburger.football.R
import com.mayburger.football.base.BaseFragment
import com.mayburger.football.utils.Constants
import com.mayburger.football.utils.Constants.DEFENSE
import com.mayburger.football.utils.Constants.FORWARD
import com.mayburger.football.utils.Constants.MIDFIELD
import com.mayburger.football.utils.bold
import com.mayburger.football.utils.medium
import kotlinx.android.synthetic.main.fragment_field.*
import org.jetbrains.anko.support.v4.ctx


@SuppressLint("ValidFragment")
/**
 * A simple [Fragment] subclass.
 */
class FieldFragment constructor(
        var strForward: Array<String>?,
        var strMidField: Array<String>?,
        var strDefense: Array<String>?,
        var strGoalkeeper: Array<String>?,
        var intAwayScore: String?

) : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_field, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recForward.adapter = FieldAdapter(strForward, FORWARD)
        recMidfield.adapter = FieldAdapter(strMidField, MIDFIELD)
        recDefense.adapter = FieldAdapter(strDefense, DEFENSE)

        if (intAwayScore != null) {
            try {
                val forwardLayoutManager = GridLayoutManager(ctx, strForward?.size!!)
                val midfieldLayoutManager = GridLayoutManager(ctx, strMidField?.size!!)
                val defenseLayoutManager = GridLayoutManager(ctx, strDefense?.size!!)
                recForward.layoutManager = forwardLayoutManager
                recForward.isLayoutFrozen = true
                recMidfield.layoutManager = midfieldLayoutManager
                recMidfield.isLayoutFrozen = true
                recDefense.layoutManager = defenseLayoutManager
                recDefense.isLayoutFrozen = true
                with(goalkeeper) {
                    text = strGoalkeeper?.get(0)!!
                    typeface = medium
                }
            } catch (e: Exception) {
                field_root.visibility = View.GONE
                with(error) {
                    visibility = View.VISIBLE
                }
            }
        } else if (intAwayScore == null) {
            field_root.visibility = View.GONE
            with(error) {
                visibility = View.VISIBLE
                typeface = bold
            }
        }
    }

}// Required empty public constructor
