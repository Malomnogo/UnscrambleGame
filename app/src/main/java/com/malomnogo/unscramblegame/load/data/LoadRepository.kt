package com.malomnogo.unscramblegame.load.data

import com.malomnogo.unscramblegame.main.ScreenRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

interface LoadRepository {
    fun load(callback: LoadCallback)

    class Base(
        private val service: WordsService,
        private val cacheDataSource: WordsCacheDataSource.Save,
        private val screenRepository: ScreenRepository.Save,
        private val wordsCount: Int
    ) : LoadRepository {
        override fun load(callback: LoadCallback) {
            service.load(wordsCount).enqueue(object : Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val words = responseBody ?: emptyList()
                        if (words.isEmpty()) {
                            callback.error("empty list")
                        } else {
                            cacheDataSource.save(words)
                            screenRepository.saveNewGameStarted()
                            callback.success()
                        }
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    if (t is UnknownHostException) {
                        callback.error("No internet")
                    } else {
                        callback.error("Error")
                    }
                }
            })
        }
    }
}