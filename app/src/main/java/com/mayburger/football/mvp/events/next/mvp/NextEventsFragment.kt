package com.mayburger.football.mvp.events.next.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mayburger.football.R
import com.mayburger.football.EventsDetailActivity
import com.mayburger.football.data.model.Events
import com.mayburger.football.data.model.Leagues
import com.mayburger.football.db.EventFavorites.Companion.EVENT_ID
import com.mayburger.football.mvp.events.next.NextEventsAdapter
import com.mayburger.football.ui.MvpFragment
import com.mayburger.football.utils.Constants
import kotlinx.android.synthetic.main.fragment_events.*
import org.jetbrains.anko.support.v4.ctx


open class NextEventsFragment : MvpFragment<NextEventsPresenter>(), NextEventsView// Required empty public constructor
{
    override fun createPresenter(): NextEventsPresenter {
        return NextEventsPresenter(this@NextEventsFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onGetLeagues(leagues: List<Leagues.League>) {
        mvpPresenter!!.getEventsData(leagues[0].idLeague.toString(), isNetworkAvailable(), recEvents, swipeRefresh, repoInstance(), ctx)
        val list = ArrayList<String>()
        for (i in 0..leagues.size) {
            if (leagues[i].strSport.equals(Constants.SPORT)) {
                list.add(leagues[i].strLeague.toString())
                val adapter = ArrayAdapter<String>(context, R.layout.spinner, list)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner!!.adapter = adapter

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                        mvpPresenter!!.getEventsData(leagues[position].idLeague.toString(), isNetworkAvailable(), recEvents, swipeRefresh, repoInstance(), ctx)
                    }
                }
            }
        }
    }

    private lateinit var adapter: NextEventsAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpPresenter!!.getLeagues(repoInstance())
    }

    override fun onGetEvents(events: List<Events.Event>) {
        try {
            swipeRefresh.isRefreshing = false
            recEvents.visibility = View.VISIBLE
            adapter = NextEventsAdapter(events, ctx, repoInstance()) { it ->
                val intent = Intent(context, EventsDetailActivity::class.java)
                val event: Events.Event = it
                intent.putExtra(EVENT_ID, event.idEvent)
                startActivity(intent)
            }
            recEvents.layoutManager = LinearLayoutManager(ctx)
            recEvents.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
