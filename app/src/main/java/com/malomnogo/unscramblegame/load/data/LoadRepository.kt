package com.malomnogo.unscramblegame.load.data

import com.malomnogo.unscramblegame.load.presentation.LoadResult
import com.malomnogo.unscramblegame.main.ScreenRepository
import java.net.UnknownHostException

interface LoadRepository {
    suspend fun load() : LoadResult

    class Base(
        private val service: WordsService,
        private val cacheDataSource: WordsCacheDataSource.Save,
        private val screenRepository: ScreenRepository.Save,
        private val wordsCount: Int
    ) : LoadRepository {

        override suspend fun load() = try {
            val response = service.load(wordsCount).execute()
            val responseBody = response.body()!!
            if (responseBody.isEmpty()) {
                LoadResult.Error("empty list")
            } else {
                cacheDataSource.save(responseBody)
                screenRepository.saveNewGameStarted()
                LoadResult.Success
            }
        } catch (e: Exception) {
            if (e is UnknownHostException)
                LoadResult.Error("no internet connection")
            else LoadResult.Error("service unavailable")
        }
    }
}