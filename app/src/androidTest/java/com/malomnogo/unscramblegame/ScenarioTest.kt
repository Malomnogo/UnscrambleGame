package com.malomnogo.unscramblegame

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun correctTwice() {
        var initialPage = InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "lamina")
        initialPage.checkVisible()
        initialPage.clickSubmit()
        initialPage.checkVisible()
        var buffer = ""
        "anima".forEach {
            buffer += it
            initialPage.typeText(word = it)
            initialPage.clickSubmit()
            initialPage.checkVisible(input = buffer)

        }
        initialPage.typeText(word = "l")
        initialPage.checkVisible(input = "animal")
        initialPage.typeText(word = "l")
        initialPage.clickSubmit()
        initialPage.checkVisible(input = "animall")
        initialPage.replaceText(text = "animal")
        initialPage.clickSubmit()


        initialPage = InitialPage(counter = "2/2", score = "Score: 20", shuffledWord = "otua")
        initialPage.checkVisible()
        initialPage.clickSubmit()
        initialPage.checkVisible()
        buffer = ""
        "aut".forEach {
            buffer += it
            initialPage.typeText(word = it)
            initialPage.clickSubmit()
            initialPage.checkVisible(input = buffer)

        }
        initialPage.typeText(word = "o")
        initialPage.checkVisible(input = "auto")
        initialPage.clickSubmit()
        val gameOverPage = GameOverPage(score = "Score: 40")
        initialPage.checkNotVisible()
        gameOverPage.checkVisible()
        gameOverPage.clickRestart()


        initialPage =
            InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "anecdote".reversed())
        initialPage.checkVisible()
    }

    @Test
    fun incorrectThenCorrectThenTwiceIncorrectAndCorrect() {
        var initialPage = InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "lamina")
        initialPage.replaceText(text = "abcdef")
        initialPage.clickSubmit()
        var errorPage = ErrorPage()
        errorPage.checkVisible()
        initialPage.replaceText(text = "animal")
        initialPage.clickSubmit()
        initialPage = InitialPage(counter = "2/2", score = "Score: 10", shuffledWord = "otua")
        initialPage.replaceText(text = "abcd")
        initialPage.clickSubmit()
        errorPage = ErrorPage()
        errorPage.checkVisible()
        initialPage.replaceText(text = "abcw")
        initialPage.clickSubmit()
        errorPage.checkVisible()

        val gameOverPage = GameOverPage(score = "Score: 20")
        initialPage.checkNotVisible()
        gameOverPage.checkVisible()
        gameOverPage.clickRestart()


        initialPage =
            InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "anecdote".reversed())
        initialPage.checkVisible()
    }

    @Test
    fun skipTwice() {
        var initialPage = InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "lamina")
        initialPage.checkVisible()
        initialPage.clickSubmit()
        initialPage.checkVisible()
        initialPage.clickSkip()

        initialPage = InitialPage(counter = "2/2", score = "Score: 0", shuffledWord = "otua")
        initialPage.checkVisible()
        initialPage.clickSubmit()
        initialPage.checkVisible()
        initialPage.clickSkip()

        val gameOverPage = GameOverPage(score = "Score: 0")
        initialPage.checkNotVisible()
        gameOverPage.checkVisible()
        gameOverPage.clickRestart()

        initialPage =
            InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "anecdote".reversed())
        initialPage.checkVisible()

    }

    @Test
    fun skipThenCorrect{
        var initialPage = InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "lamina")
        initialPage.checkVisible()
        initialPage.clickSubmit()
        initialPage.checkVisible()
        initialPage.clickSkip()

        initialPage = InitialPage(counter = "2/2", score = "Score: 0", shuffledWord = "otua")
        initialPage.checkVisible()
        initialPage.typeText(word = "auto")
        initialPage.clickSubmit()

        val gameOverPage = GameOverPage(score = "Score: 20")
        initialPage.checkNotVisible()
        gameOverPage.checkVisible()
        gameOverPage.clickRestart()


        initialPage =
            InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "anecdote".reversed())
        initialPage.checkVisible()
    }

    @Test
    fun correctThenSkip() {
        var initialPage = InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "lamina")
        initialPage.checkVisible()
        initialPage.typeText(word = "animal")
        initialPage.clickSubmit()
        initialPage.checkVisible()

        initialPage = InitialPage(counter = "2/2", score = "Score: 20", shuffledWord = "otua")
        initialPage.checkVisible()
        initialPage.clickSkip()


        val gameOverPage = GameOverPage(score = "Score: 20")
        initialPage.checkNotVisible()
        gameOverPage.checkVisible()
        gameOverPage.clickRestart()


        initialPage =
            InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "anecdote".reversed())
        initialPage.checkVisible()
    }

    @Test
    fun skipThenErrorThenSkip() {
        var initialPage = InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "lamina")
        initialPage.checkVisible()
        initialPage.clickSubmit()
        initialPage.checkVisible()
        initialPage.clickSkip()

        initialPage = InitialPage(counter = "2/2", score = "Score: 0", shuffledWord = "otua")
        initialPage.checkVisible()
        initialPage.replaceText(text = "abcd")
        initialPage.clickSubmit()

        val errorPage = ErrorPage()
        errorPage.checkVisible()

        initialPage.clickSkip()
        val gameOverPage = GameOverPage(score = "Score: 0")
        initialPage.checkNotVisible()
        gameOverPage.checkVisible()
        gameOverPage.clickRestart()

        initialPage =
            InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "anecdote".reversed())
        initialPage.checkVisible()
    }
    @Test
    fun errorThenSkipThenErrorThenCorrect() {
        var initialPage = InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "lamina")
        initialPage.checkVisible()
        initialPage.typeText(word = "animad")
        initialPage.clickSubmit()

        val errorPage = ErrorPage()
        errorPage.checkVisible()

        initialPage.clickSkip()
        initialPage = InitialPage(counter = "2/2", score = "Score: 0", shuffledWord = "otua")
        initialPage.checkVisible()
        initialPage.typeText(word = "abcd")
        initialPage.clickSubmit()

        errorPage.checkVisible()

        initialPage.replaceText(text = "auto")
        initialPage.clickSubmit()

        val gameOverPage = GameOverPage(score = "Score: 10")
        initialPage.checkNotVisible()
        gameOverPage.checkVisible()
        gameOverPage.clickRestart()

        initialPage =
            InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "anecdote".reversed())
        initialPage.checkVisible()

    }

    @Test
    fun skipThenErrorThenCorrect() {
        var initialPage = InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "lamina")
        initialPage.checkVisible()
        initialPage.clickSkip()

        initialPage = InitialPage(counter = "2/2", score = "Score: 0", shuffledWord = "otua")
        initialPage.checkVisible()
        initialPage.typeText(word = "abcd")
        initialPage.clickSubmit()

        val errorPage = ErrorPage()
        errorPage.checkVisible()

        initialPage.replaceText(text = "auto")
        initialPage.clickSubmit()

        val gameOverPage = GameOverPage(score = "Score: 10")
        initialPage.checkNotVisible()
        gameOverPage.checkVisible()
        gameOverPage.clickRestart()

        initialPage =
            InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "anecdote".reversed())
        initialPage.checkVisible()

    }

    @Test
    fun incorrectThenCorrectThenSkip( ) {
        var initialPage = InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "lamina")
        initialPage.checkVisible()
        initialPage.replaceText(text = "abcder")
        initialPage.clickSubmit()

        val errorPage = ErrorPage()
        errorPage.checkVisible()
        initialPage.replaceText(text = "animal")
        initialPage.clickSubmit()
        initialPage = InitialPage(counter = "2/2", score = "Score: 10", shuffledWord = "otua")

        initialPage.checkVisible()
        initialPage.clickSkip()

        val gameOverPage = GameOverPage(score = "Score: 10")
        initialPage.checkNotVisible()
        gameOverPage.checkVisible()
        gameOverPage.clickRestart()

        initialPage =
            InitialPage(counter = "1/2", score = "Score: 0", shuffledWord = "anecdote".reversed())
        initialPage.checkVisible()
    }

}