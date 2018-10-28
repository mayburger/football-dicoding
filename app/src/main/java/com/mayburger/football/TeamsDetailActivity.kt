package com.mayburger.football

import android.os.Bundle
import com.mayburger.football.base.BaseActivity
import com.mayburger.football.db.EventFavorites.Companion.EVENT_ID
import com.mayburger.football.mvp.detail.EventsDetailFragment

class TeamsDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val events_id = intent.getStringExtra(EVENT_ID)
        if (events_id != null) {
            addFragmentOnTop(this@TeamsDetailActivity, EventsDetailFragment(events_id),R.id.frame_detail)
        }
    }
    override fun onBackPressed() {
        finish()
    }
}
