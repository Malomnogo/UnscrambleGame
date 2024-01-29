package com.malomnogo.unscramblegame.main

import androidx.lifecycle.ViewModel
import com.malomnogo.unscramblegame.game.GameScreen
import com.malomnogo.unscramblegame.load.presentation.LoadScreen

class MainViewModel(
    private val repository: ScreenRepository.Read,
    private val navigation: NavigationObservable,
) : ViewModel() {

    fun screen() {
        val screen = if (repository.shouldLoadNewGame())
            LoadScreen
        else
            GameScreen
        navigation.update(screen)
    }

    fun startGettingUpdates(observer: Navigation) {
        navigation.updateNavigateObserver(observer)
    }

    fun stopGettingUpdates() {
        navigation.updateNavigateObserver(navigation)
    }

    fun notifyScreenObserved() {
        navigation.clear()
    }
}