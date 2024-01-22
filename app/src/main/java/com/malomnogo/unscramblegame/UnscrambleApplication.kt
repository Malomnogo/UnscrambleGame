package com.malomnogo.unscramblegame

import android.app.Application

class UnscrambleApplication : Application() {

    private lateinit var viewModel: GameViewModel

    fun viewModel() = viewModel

    override fun onCreate() {
        super.onCreate()
        val isRelease = !BuildConfig.DEBUG
        val shuffle = if (isRelease) Shuffle.Base() else Shuffle.Reversed()
        val wordsCount = if (isRelease) 10 else 2
        viewModel =
            GameViewModel(
                GameRepository.Base(
                    permanentStorage = PermanentStorage.Base(this),
                    shuffle = shuffle,
                    wordsCount = wordsCount
                )
            )

    }
}