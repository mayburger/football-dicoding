package com.mayburger.football

import android.os.Bundle
import com.mayburger.football.base.BaseActivity
import com.mayburger.football.data.model.Events
import com.mayburger.football.db.EventFavorites.Companion.EVENT_ID
import com.mayburger.football.db.TeamFavorites.Companion.TEAM_NAME
import com.mayburger.football.mvp.detail.events.EventsDetailFragment
import com.mayburger.football.mvp.detail.teams.TeamsDetailFragment

class DetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val events_id = intent.getStringExtra(EVENT_ID)
        val teams_name = intent.getStringExtra(TEAM_NAME)
        val events: Events.Event = intent.getSerializableExtra("") as Events.Event
        if (events_id != null) {
            addFragmentOnTop(this@DetailActivity, EventsDetailFragment(events_id), R.id.frame_detail)
        } else if (teams_name != null) {
            addFragmentOnTop(this@DetailActivity, TeamsDetailFragment(teams_name), R.id.frame_detail)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
