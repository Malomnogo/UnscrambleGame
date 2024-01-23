package com.malomnogo.unscramblegame

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameViewModelTest {
    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        viewModel = GameViewModel(FakeRepository())
    }

    @Test
    fun correctTwiceThenRestart() {
        var actual: UiState = viewModel.init()
        var expected: UiState =
            UiState.Initial(counter = "1/2", score = 0, shuffleWord = "apple".reversed())
        assertEquals(expected, actual)

        actual = viewModel.update(text = "a")
        expected = UiState.InvalidInput
        assertEquals(expected, actual)

        actual = viewModel.update(text = "ap")
        expected = UiState.InvalidInput
        assertEquals(expected, actual)

        actual = viewModel.update(text = "app")
        expected = UiState.InvalidInput
        assertEquals(expected, actual)

        actual = viewModel.update(text = "appl")
        expected = UiState.InvalidInput
        assertEquals(expected, actual)

        actual = viewModel.update(text = "apple")
        expected = UiState.ValidInput
        assertEquals(expected, actual)

        actual = viewModel.update(text = "applee")
        expected = UiState.InvalidInput
        assertEquals(expected, actual)

        actual = viewModel.update(text = "apple")
        expected = UiState.ValidInput
        assertEquals(expected, actual)

        actual = viewModel.submit(text = "apple")
        expected = UiState.Initial(counter = "2/2", score = 20, shuffleWord = "orange".reversed())
        assertEquals(expected, actual)

        actual = viewModel.update(text = "orange")
        expected = UiState.ValidInput
        assertEquals(expected, actual)
        actual = viewModel.submit(text = "orange")
        expected = UiState.GameOver(score = 40)
        assertEquals(expected, actual)

        actual = viewModel.restart()
        expected = UiState.Initial(counter = "1/2", score = 0, shuffleWord = "banana".reversed())
        assertEquals(expected, actual)

    }

    @Test
    fun incorrectCorrectThenSkip() {
        var actual: UiState = viewModel.init()
        var expected: UiState =
            UiState.Initial(counter = "1/2", score = 0, shuffleWord = "apple".reversed())
        assertEquals(expected, actual)

        actual = viewModel.update(text = "abcde")
        expected = UiState.ValidInput
        assertEquals(expected, actual)

        actual = viewModel.submit(text = "abcde")
        expected = UiState.Error
        assertEquals(expected, actual)

        actual = viewModel.submit(text = "apple")
        expected = UiState.Initial(counter = "2/2", score = 10, shuffleWord = "orange".reversed())
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = UiState.GameOver(score = 10)
        assertEquals(expected, actual)
    }

    @Test
    fun skipThenIncorrectThenSkip() {
        var actual: UiState = viewModel.init()
        var expected: UiState =
            UiState.Initial(counter = "1/2", score = 0, shuffleWord = "apple".reversed())
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = UiState.Initial(counter = "2/2", score = 0, shuffleWord = "orange".reversed())
        assertEquals(expected, actual)

        actual = viewModel.update(text = "abcdef")
        expected = UiState.ValidInput
        assertEquals(expected, actual)

        actual = viewModel.submit(text = "abcdef")
        expected = UiState.Error
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = UiState.GameOver(score = 0)
        assertEquals(expected, actual)
    }


}

private class FakeRepository : GameRepository {

    private var index = 0
    private var score = 0
    private val words = listOf("apple", "orange", "banana")
    private var attempts = 0
    private var uiPosition = 1

    override fun currentWordPosition(): Int {
        return uiPosition
    }

    override fun maxWordsCount() = 2

    override fun score(): Int {
        return score
    }

    override fun shuffleWord(): String {
        return words[index].reversed()
    }

    override fun isTextCorrect(text: String): Boolean {
        val isCorrect = words[index] == text

        if (isCorrect) {
            score += if (attempts == 0) {
                20
            } else {
                10
            }
            attempts = 0
        } else {
            attempts++
        }
        return isCorrect
    }

    override fun isLastWord(): Boolean {
        return index == 1
    }

    override fun next() {
        index++
        uiPosition++
    }

    override fun restart() {
        uiPosition = 1
        score = 0
        attempts = 0
        index++
    }

}