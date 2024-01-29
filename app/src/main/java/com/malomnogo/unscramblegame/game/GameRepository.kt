package com.malomnogo.unscramblegame.game

import android.content.Context
import com.malomnogo.unscramblegame.load.data.WordsCacheDataSource

interface GameRepository : Score {
    fun currentWordPosition(): Int
    fun maxWordsCount(): Int
    fun shuffleWord(): String
    fun isTextCorrect(text: String): Boolean
    fun isLastWord(): Boolean
    fun next()
    fun restart()

    class Base(
        private val permanentStorage: PermanentStorage,
        private val shuffle: Shuffle = Shuffle.Reversed(),
        private val wordsCount: Int = 2,
        private val scoreLogic: ScoreLogic = ScoreLogic.Base(permanentStorage),
        private val cacheDataSource: WordsCacheDataSource.Read
    ) : GameRepository {


        private var shuffled = ""
        override fun currentWordPosition() = permanentStorage.uiPosition()
        override fun maxWordsCount() = wordsCount
        override fun score() = scoreLogic.score()
        override fun shuffleWord(): String {
            if (shuffled.isEmpty()) {
                shuffled = shuffle.shuffle(cacheDataSource.words()[permanentStorage.index()])
            }
            return shuffled
        }

        override fun isTextCorrect(text: String): Boolean {
            val isCorrect = cacheDataSource.words()[permanentStorage.index()] == text
            scoreLogic.calculate(isCorrect)
            return isCorrect
        }

        override fun isLastWord() = permanentStorage.uiPosition() == wordsCount
        override fun next() {
            shuffled = ""
            permanentStorage.saveIndex(permanentStorage.index() + 1)
            permanentStorage.saveUiPosition(permanentStorage.uiPosition() + 1)
        }

        override fun restart() {
            next()
            permanentStorage.saveUiPosition(1)
            scoreLogic.clear()
            if (permanentStorage.index() == cacheDataSource.words().size) permanentStorage.saveIndex(
                index = 0
            )
        }
    }
}

interface LocalStorage {
    fun save(key: String, value: Int)
    fun save(key: String, value: Boolean)
    fun save(key: String, value: String)
    fun read(key: String, default: Int): Int
    fun read(key: String, default: Boolean): Boolean
    fun read(key: String, default: String): String

    class Base(context: Context) : LocalStorage {

        private val sharedPreferences =
            context.getSharedPreferences("unScrambleStorage", Context.MODE_PRIVATE)

        override fun save(key: String, value: Int) {
            sharedPreferences.edit().putInt(key, value).apply()
        }

        override fun save(key: String, value: Boolean) {
            sharedPreferences.edit().putBoolean(key, value).apply()
        }

        override fun save(key: String, value: String) {
            sharedPreferences.edit().putString(key, value).apply()
        }

        override fun read(key: String, default: Int): Int {
            return sharedPreferences.getInt(key, default)
        }

        override fun read(key: String, default: Boolean): Boolean {
            return sharedPreferences.getBoolean(key, default)
        }

        override fun read(key: String, default: String): String {
            return sharedPreferences.getString(key, default) ?: default
        }
    }
}

interface PermanentStorage {
    fun index(): Int
    fun saveIndex(index: Int)
    fun uiPosition(): Int
    fun saveUiPosition(uiPosition: Int)
    fun score(): Int
    fun saveScore(score: Int)
    fun attempts(): Int
    fun saveAttempts(attempts: Int)

    class Base(private val localStorage: LocalStorage) : PermanentStorage {

        override fun index() = localStorage.read(KEY_INDEX, 0)
        override fun saveIndex(index: Int) {
            localStorage.save(KEY_INDEX, index)
        }

        override fun uiPosition() = localStorage.read(KEY_UI_POSITION, 1)
        override fun saveUiPosition(uiPosition: Int) {
            localStorage.save(KEY_UI_POSITION, uiPosition)
        }

        override fun score(): Int {
            return localStorage.read(KEY_SCORE, 0)
        }

        override fun saveScore(score: Int) {
            localStorage.save(KEY_SCORE, score)
        }

        override fun attempts(): Int {
            return localStorage.read(KEY_ATTEMPTS, 0)
        }

        override fun saveAttempts(attempts: Int) {
            localStorage.save(KEY_ATTEMPTS, attempts)
        }

        companion object {
            private const val KEY_INDEX = "index"
            private const val KEY_UI_POSITION = "uiPosition"
            private const val KEY_SCORE = "key_score"
            private const val KEY_ATTEMPTS = "key_attempts"
        }
    }
}

interface Score {
    fun score(): Int
}

interface ScoreLogic : Score {

    fun calculate(isCorrect: Boolean)
    fun clear()

    abstract class Abstract(
        protected val permanentStorage: PermanentStorage
    ) : ScoreLogic {
        override fun score(): Int = permanentStorage.score()
        override fun clear() {
            permanentStorage.saveScore(score = 0)
            permanentStorage.saveAttempts(attempts = 0)
        }
    }

    class Base(permanentStorage: PermanentStorage) : Abstract(permanentStorage) {
        override fun calculate(isCorrect: Boolean) {
            var score = permanentStorage.score()
            var attempts = permanentStorage.attempts()
            if (isCorrect) {
                score += 10
                if (attempts == 0)
                    score += 10
                attempts = 0
            } else
                attempts++
            permanentStorage.saveScore(score)
            permanentStorage.saveAttempts(attempts)
        }
    }
}

interface Shuffle {
    fun shuffle(source: String): String
    class Base : Shuffle {
        override fun shuffle(source: String): String {
            val array = source.toCharArray()
            array.shuffle()
            val stringBuilder = StringBuilder()
            array.forEach {
                stringBuilder.append(it)
            }
            return stringBuilder.toString()
        }
    }

    class Reversed : Shuffle {
        override fun shuffle(source: String) = source.reversed()
    }
}