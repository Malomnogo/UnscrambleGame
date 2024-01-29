package com.malomnogo.unscramblegame.load.presentation

import androidx.lifecycle.ViewModel
import com.malomnogo.unscramblegame.game.GameScreen
import com.malomnogo.unscramblegame.load.data.LoadCallback
import com.malomnogo.unscramblegame.load.data.LoadRepository
import com.malomnogo.unscramblegame.main.NavigationObservable

class LoadViewModel(
    private val repository: LoadRepository,
    private val uiObservable: UiObservable,
    private val navigation: NavigationObservable,
) : ViewModel(), LoadCallback {
    fun load() {
        uiObservable.updateUi(LoadUiState.Progress)
        repository.load(this)
    }

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) load()
    }

    override fun success() {
        navigation.update(GameScreen)
    }

    override fun error(msg: String) {
        uiObservable.updateUi(LoadUiState.Error(message = msg))
    }

    fun startGettingUpdates(uiCallBack: UiCallBack) {
        uiObservable.updateObserver(uiCallBack)
    }

    fun stopGettingUpdates() {
        uiObservable.updateObserver(UiCallBack.Empty)
    }
}