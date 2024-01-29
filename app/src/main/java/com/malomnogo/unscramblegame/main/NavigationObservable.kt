package com.malomnogo.unscramblegame.main

interface NavigationObservable : Navigation, UpdateNavigationObserver {

    fun clear()

    class Base : NavigationObservable {
        private var cache: Screen = Screen.Empty
        private var observer: Navigation = Navigation.Empty
        override fun clear() {
           cache = Screen.Empty
        }

        override fun update(screen: Screen) {
            cache = screen
            observer.update(screen)
        }

        override fun updateNavigateObserver(observer: Navigation) {
            this.observer = observer
            observer.update(cache)
        }

    }
}

interface Navigation {

    fun update(screen: Screen)

    object Empty : Navigation {
        override fun update(screen: Screen) = Unit
    }
}

interface UpdateNavigationObserver {
    fun updateNavigateObserver(observer: Navigation)
}