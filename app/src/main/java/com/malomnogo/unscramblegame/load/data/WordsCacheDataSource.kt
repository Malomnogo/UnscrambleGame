package com.malomnogo.unscramblegame.load.data

import com.google.gson.Gson
import com.malomnogo.unscramblegame.game.LocalStorage

interface WordsCacheDataSource {

    interface Save {
        fun save(words: List<String>)
    }

    interface Read {
        fun words(): List<String>
    }

    interface Mutable : Save, Read

    class Base(private val localStorage: LocalStorage, private val gson: Gson) : Mutable {
        override fun save(words: List<String>) {
            localStorage.save(key = KEY, gson.toJson(WordsWrapper(words)))
        }

        override fun words(): List<String> {
            val default = gson.toJson(WordsWrapper(emptyList()))
            val string = localStorage.read(KEY, default)
            val wrapper: WordsWrapper = gson.fromJson(string, WordsWrapper::class.java)
            return wrapper.words
        }

        private data class WordsWrapper(val words: List<String>)

        companion object {
            private const val KEY = "words"
        }
    }

    class Fake : Mutable {
        override fun save(words: List<String>) {
        }

        override fun words(): List<String> {
            return listOf("animal", "blablabla")
        }
    }
}