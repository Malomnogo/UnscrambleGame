package com.malomnogo.unscramblegame.main

import com.malomnogo.unscramblegame.game.LocalStorage

interface ScreenRepository {

    interface Read {
        fun shouldLoadNewGame(): Boolean
    }

    interface Save {
        fun saveNewGameStarted()
        fun saveNeedNewGameData()
    }

    interface Mutable : Read, Save

    class Base(private val localStorage: LocalStorage) : Mutable {
        override fun shouldLoadNewGame(): Boolean {
            return localStorage.read(KEY, true)
        }

        override fun saveNewGameStarted() {
            localStorage.save(key = KEY, false)
        }

        override fun saveNeedNewGameData() {
            localStorage.save(key = KEY, true)
        }

        companion object {
            private const val KEY = "shouldLoadNewGame"
        }
    }
}