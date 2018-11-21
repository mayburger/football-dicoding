package com.mayburger.football

import com.google.gson.Gson
import com.mayburger.football.mockito.*
import com.mayburger.football.utils.Constants
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Created by root on 2/28/18.
 */
class 1EventPresenterTest {
    @Mock
    private
    lateinit var view: EventView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPrevList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevMatch(Constants.ENGLISHPREMIERELEAGUE)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getPrevList(Constants.ENGLISHPREMIERELEAGUE)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventList(events)
        Mockito.verify(view).hideLoading()
    }
}