package com.mayburger.football.mvp.search.events.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.mayburger.football.DetailActivity
import com.mayburger.football.R
import com.mayburger.football.data.model.Search
import com.mayburger.football.db.EventFavorites.Companion.EVENT_ID
import com.mayburger.football.mvp.search.events.SearchEventsAdapter
import com.mayburger.football.ui.MvpFragment
import com.mayburger.football.utils.OnBackPressed
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


open class SearchEventsFragment : MvpFragment<SearchEventsPresenter>(), SearchEventsView, OnBackPressed// Required empty public constructor
{

    override fun onBackPressed() {
        fragmentManager!!.beginTransaction().remove(this).commit()
    }

    override fun createPresenter(): SearchEventsPresenter {
        return SearchEventsPresenter(this@SearchEventsFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private lateinit var adapter: SearchEventsAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (isNetworkAvailable()) {
            mvpPresenter!!.showInput(act)
            search.setOnClickListener {
                mvpPresenter!!.showInput(act)
            }
            search.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mvpPresenter!!.getEvents(repoInstance(), search.text.toString())
                    swipeRefresh.isRefreshing = true
                    mvpPresenter!!.hideInput(act)
                    true
                } else {
                    false
                }
            }

            recSearch.visibility = View.GONE
            swipeRefresh.setColorSchemeColors(ContextCompat.getColor(ctx, R.color.colorAccent))
            swipeRefresh.isEnabled = false
        } else {
            toast("Please check your internet connection!")
        }
    }

    override fun onGetEvents(events: List<Search.Event>) {
        try {
            swipeRefresh.isRefreshing = false
            recSearch.visibility = View.VISIBLE
            adapter = SearchEventsAdapter(events, ctx, repoInstance()) { it ->
                val intent = Intent(context, DetailActivity::class.java)
                val event: Search.Event = it
                intent.putExtra(EVENT_ID, event.idEvent)
                startActivity(intent)
            }
            recSearch.layoutManager = LinearLayoutManager(ctx)
            recSearch.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onGetEventsNotAvailable() {
        swipeRefresh.isRefreshing = false
        toast("No data response")
    }

}
