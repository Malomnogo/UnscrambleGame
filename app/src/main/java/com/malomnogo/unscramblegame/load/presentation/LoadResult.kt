package com.malomnogo.unscramblegame.load.presentation

import com.malomnogo.unscramblegame.game.GameScreen
import com.malomnogo.unscramblegame.main.NavigationObservable

interface LoadResult {

    fun handle(navigation: NavigationObservable) = Unit

    fun handle(observable: UiObservable) = Unit

    data class Error(private val message: String) : LoadResult {

        override fun handle(observable: UiObservable) =
            observable.updateUi(LoadUiState.Error(message))
    }

    object Success : LoadResult {

        override fun handle(navigation: NavigationObservable) = navigation.navigate(GameScreen)
    }
}