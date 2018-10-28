package com.mayburger.football.mvp.events.fav.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mayburger.football.EventsDetailActivity
import com.mayburger.football.R
import com.mayburger.football.data.model.Events
import com.mayburger.football.db.EventFavorites
import com.mayburger.football.db.EventFavorites.Companion.EVENT_ID
import com.mayburger.football.mvp.events.fav.events.FavEventsAdapter
import com.mayburger.football.mvp.events.next.mvp.FavEventsPresenter
import com.mayburger.football.mvp.events.next.mvp.FavEventsView
import com.mayburger.football.ui.MvpFragment
import kotlinx.android.synthetic.main.fragment_events.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


open class FavEventsFragment : MvpFragment<FavEventsPresenter>(), FavEventsView// Required empty public constructor
{

    override fun createPresenter(): FavEventsPresenter {
        return FavEventsPresenter(this@FavEventsFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }


    open lateinit var adapter: FavEventsAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpPresenter!!.getEvents(ctx)
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(ctx, R.color.colorAccent))
        swipeRefresh.setOnRefreshListener {
            mvpPresenter!!.getEvents(ctx)
        }
    }

    override fun onGetEvents(events: List<EventFavorites>) {
        recEvents.visibility = View.VISIBLE
        swipeRefresh.isRefreshing = false
        if (!events.isEmpty()) {
            recEvents.visibility = View.VISIBLE
            swipeRefresh.isRefreshing = false
            adapter = FavEventsAdapter(events, ctx, repoInstance()) { it ->
                val intent = Intent(context, EventsDetailActivity::class.java)
                val event = Events.Event()
                event.idEvent = it.idEvent
                intent.putExtra(EVENT_ID, event.idEvent)
                startActivity(intent)
            }
            recEvents.layoutManager = LinearLayoutManager(ctx)
            recEvents.adapter = adapter
        } else {
            toast("No Data")
        }
    }

    override fun onDataEmpty() {
        swipeRefresh.isRefreshing = false
        recEvents.visibility = View.GONE
    }

}
