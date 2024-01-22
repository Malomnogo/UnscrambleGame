package com.malomnogo.unscramblegame

import android.content.Context

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
        private val allWords: List<String> = listOf(
            "animal",
            "auto",
            "anecdote",
            "alphabet",
            "all",
            "awesome",
            "arise",
            "balloon",
            "basket",
            "bench",
            "best",
            "birthday",
            "book",
            "briefcase",
            "camera",
            "camping",
            "candle",
            "cat",
            "cauliflower",
            "chat",
            "children",
            "class",
            "classic",
            "classroom",
            "coffee",
            "colorful",
            "cookie",
            "creative",
            "cruise",
            "dance",
            "daytime",
            "dinosaur",
            "doorknob",
            "dine",
            "dream",
            "dusk",
            "eating",
            "elephant",
            "emerald",
            "eerie",
            "electric",
            "finish",
            "flowers",
            "follow",
            "fox",
            "frame",
            "free",
            "frequent",
            "funnel",
            "green",
            "guitar",
            "grocery",
            "glass",
            "great",
            "giggle",
            "haircut",
            "half",
            "homemade",
            "happen",
            "honey",
            "hurry",
            "hundred",
            "ice",
            "igloo",
            "invest",
            "invite",
            "icon",
            "introduce",
            "joke",
            "jovial",
            "journal",
            "jump",
            "join",
            "kangaroo",
            "keyboard",
            "kitchen",
            "koala",
            "kind",
            "kaleidoscope",
            "landscape",
            "late",
            "laugh",
            "learning",
            "lemon",
            "letter",
            "lily",
            "magazine",
            "marine",
            "marshmallow",
            "maze",
            "meditate",
            "melody",
            "minute",
            "monument",
            "moon",
            "motorcycle",
            "mountain",
            "music",
            "north",
            "nose",
            "night",
            "name",
            "never",
            "negotiate",
            "number",
            "opposite",
            "octopus",
            "oak",
            "order",
            "open",
            "polar",
            "pack",
            "painting",
            "person",
            "picnic",
            "pillow",
            "pizza",
            "podcast",
            "presentation",
            "puppy",
            "puzzle",
            "recipe",
            "release",
            "restaurant",
            "revolve",
            "rewind",
            "room",
            "run",
            "secret",
            "seed",
            "ship",
            "shirt",
            "should",
            "small",
            "spaceship",
            "stargazing",
            "skill",
            "street",
            "style",
            "sunrise",
            "taxi",
            "tidy",
            "timer",
            "together",
            "tooth",
            "tourist",
            "travel",
            "truck",
            "under",
            "useful",
            "unicorn",
            "unique",
            "uplift",
            "uniform",
            "vase",
            "violin",
            "visitor",
            "vision",
            "volume",
            "view",
            "walrus",
            "wander",
            "world",
            "winter",
            "well",
            "whirlwind",
            "x-ray",
            "xylophone",
            "yoga",
            "yogurt",
            "yoyo",
            "you",
            "year",
            "yummy",
            "zebra",
            "zigzag",
            "zoology",
            "zone",
            "zeal"
        )
    ) : GameRepository {

        private var shuffled = ""

        override fun currentWordPosition(): Int {
            return permanentStorage.uiPosition()
        }

        override fun maxWordsCount(): Int {
            return wordsCount
        }

        override fun score(): Int {
            return scoreLogic.score()
        }

        override fun shuffleWord(): String {
            if (shuffled.isEmpty()) {
                shuffled = shuffle.shuffle(allWords[permanentStorage.index()])
            }
            return shuffled
        }

        override fun isTextCorrect(text: String): Boolean {
            val isCorrect = allWords[permanentStorage.index()] == text
            scoreLogic.calculate(isCorrect)
            return isCorrect
        }

        override fun isLastWord(): Boolean {
            return permanentStorage.uiPosition() == wordsCount
        }

        override fun next() {
            shuffled = ""
            permanentStorage.saveIndex(permanentStorage.index() + 1)
            permanentStorage.saveUiPosition(permanentStorage.uiPosition() + 1)
        }

        override fun restart() {
            next()
            permanentStorage.saveUiPosition(1)
            scoreLogic.clear()
            if (permanentStorage.index() == allWords.size) permanentStorage.saveIndex(0)
        }
    }
}

interface PermanentStorage {

    fun uiPosition(): Int
    fun index(): Int

    fun saveUiPosition(position: Int)
    fun saveIndex(index: Int)
    fun score(): Int
    fun saveScore(score: Int)
    fun attempts(): Int
    fun saveAttempts(attempts: Int)

    class Base(context: Context) : PermanentStorage {

        private val sharedPref =
            context.getSharedPreferences("unscrambleData", Context.MODE_PRIVATE)

        override fun uiPosition(): Int {
            return sharedPref.getInt("position", 1)
        }

        override fun index(): Int {
            return sharedPref.getInt("index", 0)
        }

        override fun saveUiPosition(position: Int) {
            sharedPref.edit().putInt("position", position).apply()
        }

        override fun saveIndex(index: Int) {
            sharedPref.edit().putInt("index", index).apply()
        }

        override fun score(): Int {
            return sharedPref.getInt("score", 0)
        }

        override fun saveScore(score: Int) {
            sharedPref.edit().putInt("score", score).apply()
        }

        override fun attempts(): Int {
            return sharedPref.getInt("attempts", 0)
        }

        override fun saveAttempts(attempts: Int) {
            sharedPref.edit().putInt("attempts", attempts).apply()
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

        override fun score(): Int {
            return permanentStorage.score()
        }

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

        override fun shuffle(source: String): String {
            return source.reversed()
        }
    }
}