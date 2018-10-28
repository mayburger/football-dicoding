package com.mayburger.football.data.model

class Teams {

    var teams: List<Team>? = null

    class Team {
        var strTeamBadge: String? = null
        var idTeam: String? = null
        var strTeam: String? = null
        var intFormedYear: String? = null
        var strManager: String? = null
        var strStadium: String? = null
        var strStadiumThumb: String? = null
        var strStadiumLocation: String? = null
        var strStadiumCapacity: String? = null
        var strWebsite: String? = null
        var strDescriptionEN: String? = null
        var strCountry: String? = null
    }
}
