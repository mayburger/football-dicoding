package com.mayburger.football.mockito


object TheSportDBApi {

    fun getPrevMatch(id: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + id
    }
}