package com.mayburger.football.data.source.remote

import com.mayburger.football.data.model.*
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import com.mayburger.football.utils.Constants.BASE_URL
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Mayburger on 1/9/18.
 */

internal interface SoccerService {

    @GET("eventspastleague.php")
    fun getPrev(
            @Query("id") id: String
    ): Observable<Events>

    @GET("eventsnextleague.php")
    fun getNext(
            @Query("id") date: String
    ): Observable<Events>

    @GET("searchteams.php")
    fun getTeams(
            @Query("t") name: String
    ): Observable<Teams>

    @GET("lookupevent.php")
    fun getEvent(
            @Query("id") id: String
    ): Observable<Events>

    @GET("searchevents.php")
    fun onQueryEvent(
            @Query("e") id: String
    ): Observable<Search>

    @GET("all_leagues.php")
    fun getLeagues(): Observable<Leagues>

    @GET("search_all_teams.php")
    fun getTeamsByLeague(
            @Query("l") league: String
    ): Observable<Teams>

    @GET("searchplayers.php")
    fun getPlayers(
            @Query("t") team: String
    ): Observable<Players>


    object Factory {
        fun create(): SoccerService {
            val mLoggingInterceptor = HttpLoggingInterceptor()
            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val mClient = OkHttpClient.Builder()
                    .addInterceptor(mLoggingInterceptor)
                    .readTimeout(30L, TimeUnit.SECONDS)
                    .connectTimeout(30L, TimeUnit.SECONDS)
                    .build()
            val mRetrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mClient)
                    .build()
            val soccerService = mRetrofit.create(SoccerService::class.java)
            return soccerService as SoccerService
        }
    }

}
