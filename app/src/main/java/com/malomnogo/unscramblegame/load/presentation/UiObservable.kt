package com.malomnogo.unscramblegame.load.presentation

interface UiObservable : UpdateUi, UpdateObserver {
    class Base : UiObservable {

        private var cache: LoadUiState = LoadUiState.Empty
        private var observer: UiCallBack = UiCallBack.Empty
        override fun updateUi(loadUiState: LoadUiState) {
            cache = loadUiState
            observer.update(cache)
        }

        override fun updateObserver(observer: UiCallBack) {
            this.observer = observer
            observer.update(cache)
        }
    }
}

interface UpdateUi {
    fun updateUi(loadUiState: LoadUiState)
}

interface UpdateObserver {
    fun updateObserver(observer: UiCallBack)
}