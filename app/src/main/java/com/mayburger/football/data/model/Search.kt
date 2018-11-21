package com.mayburger.football.data.model

import java.io.Serializable

data class Search(val a: List<Event>) {

    var event: List<Event>? = null

    class Event : Serializable {

        // Scores
        var intAwayScore: String? = null
        var intHomeScore: String? = null

        var strLeague: String? = null

        // Lineup Defense
        var strAwayLineupDefense: String? = null
        var strHomeLineupDefense: String? = null

        // Lineup Forward
        var strAwayLineupForward: String? = null
        var strHomeLineupForward: String? = null

        // Lineup Goalkeeper
        var strAwayLineupGoalkeeper: String? = null
        var strHomeLineupGoalkeeper: String? = null

        // Lineup Midfield
        var strAwayLineupMidfield: String? = null
        var strHomeLineupMidfield: String? = null

        // Event names
        var strAwayTeam: String? = null
        var strHomeTeam: String? = null

        // Date of the EventFavorites
        var strDate: String? = null

        var typeEvent: String? = null
        var idEvent: String? = null
    }
}
