package com.malomnogo.unscramblegame.load.presentation

import com.malomnogo.unscramblegame.load.data.LoadRepository
import com.malomnogo.unscramblegame.main.NavigationObservable

class LoadViewModel(
    private val repository: LoadRepository,
    private val uiObservable: UiObservable,
    private val navigation: NavigationObservable,
    runAsync: RunAsync,
) : BaseViewModel(runAsync){

    fun load() {
        uiObservable.updateUi(LoadUiState.Progress)
        runAsync({ repository.load() }) { loadResult ->
            loadResult.handle(navigation)
            loadResult.handle(uiObservable)
        }
    }

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) load()
    }

    fun startGettingUpdates(uiCallBack: UiCallback) {
        uiObservable.updateObserver(uiCallBack)
    }

    fun stopGettingUpdates() {
        uiObservable.updateObserver(UiCallback.Empty)
    }
}