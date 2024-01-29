package com.malomnogo.unscramblegame.load.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordsService {

    //https://random-word-api.vercel.app/api?words=10
    @GET("api")
    fun load(@Query("words") number: Int = 10) : Call<List<String>>
}