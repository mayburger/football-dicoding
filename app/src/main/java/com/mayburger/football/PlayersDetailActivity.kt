package com.mayburger.football

import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mayburger.football.base.BaseActivity
import com.mayburger.football.data.model.Players
import com.mayburger.football.utils.Constants.PLAYERS
import com.mayburger.football.utils.medium
import com.mayburger.football.utils.regular
import kotlinx.android.synthetic.main.activity_players_detail.*
import org.jetbrains.anko.ctx

class PlayersDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_detail)

        val player: Players.Player = intent.getSerializableExtra(PLAYERS) as Players.Player

        if (player.strThumb != null) {
            Glide.with(ctx).load(player.strThumb).into(image)
        } else if (player.strCutout != null) {
            Glide.with(ctx).load(player.strCutout).apply(RequestOptions().fitCenter()).into(image)
        }
        with(name)
        {
            text = player.strPlayer
            typeface = regular
        }
        with(name)
        {
            text = player.strPlayer
            typeface = regular
        }
        with(weight_title)
        {
            typeface = medium
        }
        with(weight)
        {
            if (player.strWeight != null) {
                text = player.strWeight
            } else {
                text = "?"
            }
            typeface = regular
        }
        with(height_title)
        {
            typeface = medium
        }
        with(height)
        {
            if (player.strHeight != null) {
                text = player.strHeight
            } else {
                text = "?"
            }
            typeface = regular
        }
        with(position)
        {
            text = player.strPosition
            typeface = regular
        }
        with(description)
        {
            text = player.strDescriptionEN
            typeface = regular
        }

    }

    fun dip2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    override fun onBackPressed() {
        finish()
    }
}
