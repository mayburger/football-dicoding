package com.mayburger.football.data.model

import java.io.Serializable

data class Players(val a: List<Players.Player>){

    var player: List<Player>? = null

    class Player :Serializable{
        var idPlayer: String? = null
        var strHeight: String? = null
        var strWeight: String? = null
        var strPosition: String? = null
        var strDescriptionEN: String? = null
        var strThumb: String? = null
        var strCutout: String? = null
        var strPlayer:String? = null
    }
}
