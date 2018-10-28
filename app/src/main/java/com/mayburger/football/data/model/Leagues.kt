package com.mayburger.football.data.model

import java.io.Serializable

data class Leagues(val a: List<League>){

    var leagues: List<League>? = null

    class League : Serializable {
        var idLeague:String? = null
        var strLeague:String? = null
        var strSport:String? = null
    }
}
