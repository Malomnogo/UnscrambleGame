package com.malomnogo.unscramblegame.main

interface NavigationObservable : Navigation, UpdateNavigationObserver {

    fun clear()

    class Base : NavigationObservable {
        private var cache: Screen = Screen.Empty
        private var observer: Navigation = Navigation.Empty
        override fun clear() {
           cache = Screen.Empty
        }

        override fun navigate(screen: Screen) {
            cache = screen
            observer.navigate(screen)
        }

        override fun updateNavigateObserver(observer: Navigation) {
            this.observer = observer
            observer.navigate(cache)
        }

    }
}

interface Navigation {

    fun navigate(screen: Screen)

    object Empty : Navigation {
        override fun navigate(screen: Screen) = Unit
    }
}

interface UpdateNavigationObserver {
    fun updateNavigateObserver(observer: Navigation)
}