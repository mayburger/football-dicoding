package com.mayburger.football.mvp.detail.events

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.mayburger.football.R
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Teams
import com.mayburger.football.data.source.SoccerDataSource
import com.mayburger.football.data.source.SoccerRepository
import com.mayburger.football.mvp.detail.events.field.FieldFragment
import com.mayburger.football.ui.MvpFragment
import com.mayburger.football.utils.OnBackPressed
import com.mayburger.football.utils.bold
import com.mayburger.football.utils.medium
import com.mayburger.football.utils.regular
import kotlinx.android.synthetic.main.fragment_events_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.textColor
import java.text.ParseException
import java.text.SimpleDateFormat


@SuppressLint("ValidFragment")
class EventsDetailFragment constructor(internal var id_event: String) : MvpFragment<EventsDetailPresenter>(), OnBackPressed, EventsDetailView// Required empty public constructor
{
    override fun onGetEvent(event: Events.Event) {
        mvpPresenter?.isFavorite(event,ctx, event.idEvent.toString())
        with(nameHome) {
            text = event.strHomeTeam
            typeface = regular
        }
        with(nameAway)
        {
            text = event.strAwayTeam
            typeface = regular
        }
        setTeamsLogo(event.strHomeTeam!!, logoHome)
        setTeamsLogo(event.strAwayTeam!!, logoAway)
        with(league)
        {
            text = event.strLeague
        }
        with(scoreHome)
        {
            if (!event.intHomeScore.toString().equals("null")) {
                text = event.intHomeScore.toString()
            } else {
                text = "?"
            }
            typeface = medium
        }
        with(scoreAway)
        {
            if (!event.intAwayScore.toString().equals("null")) {
                text = event.intAwayScore.toString()
            } else {
                text = "?"
            }
            typeface = medium
        }

        if (event.strHomeLineupDefense.toString() != "null") {
            try {
                val strDefenseHome = event.strHomeLineupDefense!!.split("; ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val strForwardHome = event.strHomeLineupForward!!.split("; ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val strMidfieldHome = event.strHomeLineupMidfield!!.split("; ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val strGoalKeeperHome = event.strHomeLineupGoalkeeper!!.split("; ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val strDefenseAway = event.strAwayLineupDefense!!.split("; ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val strForwardAway = event.strAwayLineupForward!!.split("; ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val strMidfieldAway = event.strAwayLineupMidfield!!.split("; ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val strGoalKeeperAway = event.strAwayLineupGoalkeeper!!.split("; ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                teamLineup(event, strForwardHome, strMidfieldHome, strDefenseHome, strGoalKeeperHome, strForwardAway, strMidfieldAway, strDefenseAway, strGoalKeeperAway)
            } catch (e: Exception) {
                teamLineup(event, null, null, null, null, null, null, null, null)
            }
        } else {
            teamLineup(event, null, null, null, null, null, null, null, null)
        }

        back.setOnClickListener { activity!!.finish() }

        if (event.intAwayScore != null) {
            scoreHome.visibility = View.VISIBLE
            scoreAway.visibility = View.VISIBLE
            dash.visibility = View.VISIBLE
            vs.visibility = View.GONE
            with(scoreHome) {
                if (!event.intHomeScore.toString().equals("null")) {
                    text = event.intHomeScore.toString()
                } else {
                    text = "?"
                }
                typeface = bold
            }
            with(scoreAway) {
                if (!event.intAwayScore.toString().equals("null")) {
                    text = event.intAwayScore.toString()
                } else {
                    text = "?"
                }
                typeface = bold
            }
        } else if (event.intAwayScore == null) {
            scoreHome.visibility = View.GONE
            scoreAway.visibility = View.GONE
            dash.visibility = View.GONE
            vs.visibility = View.VISIBLE
        }

        with(date)
        {
            try {
                if (event.strDate != null) {
                    @SuppressLint("SimpleDateFormat") val strDate = SimpleDateFormat("dd-mm-yyyy").format(SimpleDateFormat("dd/mm/yy").parse(event.strDate))
                    text = "Played On " + strDate
                } else {
                    @SuppressLint("SimpleDateFormat") val strDate = SimpleDateFormat("dd-mm-yyyy").format(SimpleDateFormat("yyyy-mm-dd").parse(event.dateEvent))
                    text = "Played On " + strDate
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            typeface = medium
        }
    }


    override fun isFavorite(event: Events.Event, isFavorite: Boolean) {

        if (isFavorite) {
            unfavorite.visibility = View.VISIBLE
            favorite.visibility = View.GONE
        } else {
            unfavorite.visibility = View.GONE
            favorite.visibility = View.VISIBLE
        }

        favorite.setOnClickListener {
            mvpPresenter!!.addEvent(event, ctx)
            unfavorite.visibility = View.VISIBLE
            favorite.visibility = View.GONE
        }

        unfavorite.setOnClickListener {
            mvpPresenter!!.removeEvent(event.idEvent.toString(), ctx)
            unfavorite.visibility = View.GONE
            favorite.visibility = View.VISIBLE
        }

    }

    override fun createPresenter(): EventsDetailPresenter {
        return EventsDetailPresenter(this@EventsDetailFragment)
    }

    override fun onBackPressed() {
        fragmentManager!!.beginTransaction().remove(this).commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpPresenter!!.getEvents(repoInstance(), id_event)
    }


    fun teamLineup(event: Events.Event, strForwardHome: Array<String>?, strMidfieldHome: Array<String>?, strDefenseHome: Array<String>?, strGoalKeeperHome: Array<String>?, strForwardAway: Array<String>?, strMidfieldAway: Array<String>?, strDefenseAway: Array<String>?, strGoalKeeperAway: Array<String>?) {
        replaceFragment(activity as AppCompatActivity, FieldFragment(strForwardHome, strMidfieldHome, strDefenseHome, strGoalKeeperHome, event.intAwayScore), R.id.frame_detail_content)
        with(homeLine) {
            text = event.strHomeTeam
            typeface = bold
            onClick {
                replaceFragment(activity as AppCompatActivity, FieldFragment(strForwardHome, strMidfieldHome, strDefenseHome, strGoalKeeperHome, event.intAwayScore), R.id.frame_detail_content)
                textColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                awayLine.textColor = ContextCompat.getColor(ctx, R.color.colorTextGrey)
            }
        }
        with(awayLine) {
            text = event.strAwayTeam
            typeface = bold
            onClick {
                replaceFragment(activity as AppCompatActivity, FieldFragment(strForwardAway, strMidfieldAway, strDefenseAway, strGoalKeeperAway, event.intAwayScore), R.id.frame_detail_content)
                textColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                homeLine.textColor = ContextCompat.getColor(ctx, R.color.colorTextGrey)
            }
        }
    }

    fun setTeamsLogo(name: String, image: ImageView) {
        try {
            val repo: SoccerRepository = repoInstance()
            repo.getTeamsByName(object : SoccerDataSource.GetTeamsCallback {
                override fun onTeamsLoaded(teams: List<Teams.Team>?) {
                    try {
                        if (teams != null) {
                            Glide.with(ctx)
                                    .load(teams[0].strTeamBadge)
                                    .apply(RequestOptions().centerCrop())
                                    .apply(RequestOptions().override(200, 200))
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(image)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onDataNotAvailable() {
                    /**unused**/
                }

                override fun onError(errorMessage: String) {
                    /**unused**/
                }
            }, name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events_detail, container, false)
    }
}