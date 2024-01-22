package com.malomnogo.unscramblegame

interface GameRepository : Score {

    fun currentWordPosition(): Int
    fun maxWordsCount(): Int
    fun shuffleWord(): String
    fun isTextCorrect(text: String): Boolean
    fun isLastWord(): Boolean
    fun next()
    fun restart()

    class Base(
        private val shuffle: Shuffle = Shuffle.Reversed(),
        private val wordsCount: Int = 2,
        private val scoreLogic: ScoreLogic = ScoreLogic.Base(),
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

        private var uiPosition = 1
        private var index = 0

        override fun currentWordPosition(): Int {
            return uiPosition
        }

        override fun maxWordsCount(): Int {
            return wordsCount
        }

        override fun score(): Int {
            return scoreLogic.score()
        }

        override fun shuffleWord(): String {
            return shuffle.shuffle(allWords[index])
        }

        override fun isTextCorrect(text: String): Boolean {
            val isCorrect = allWords[index] == text
            scoreLogic.calculate(isCorrect)
            return isCorrect
        }

        override fun isLastWord(): Boolean {
            return uiPosition == wordsCount
        }

        override fun next() {
            index++
            uiPosition++
        }

        override fun restart() {
            uiPosition = 1
            scoreLogic.clear()
            index++
            if (index == allWords.size) index = 0
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
        protected var score: Int = 0,
        protected var attempts: Int = 0
    ) : ScoreLogic {

        override fun score(): Int {
            return score
        }

        override fun clear() {
            score = 0
            attempts = 0
        }
    }

    class Base : Abstract() {

        override fun calculate(isCorrect: Boolean) {
            if (isCorrect) {
                score += 10
                if (attempts == 0)
                    score += 10

                attempts = 0
            } else
                attempts++
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