package com.malomnogo.unscramblegame.game

import androidx.lifecycle.ViewModel
import com.malomnogo.unscramblegame.load.presentation.LoadScreen
import com.malomnogo.unscramblegame.main.Navigation
import com.malomnogo.unscramblegame.main.ScreenRepository

class GameViewModel(
    private val repository: GameRepository,
    private val screenRepository: ScreenRepository.Save,
    private val navigation: Navigation
) : ViewModel(), SkipActions {

    fun init() = UiState.Initial(
        "${repository.currentWordPosition()}/${repository.maxWordsCount()}",
        repository.score(),
        repository.shuffleWord()
    )

    fun update(text: String) = if (text.length == repository.shuffleWord().length)
        UiState.ValidInput
    else
        UiState.InvalidInput

    fun submit(text: String) = if (repository.isTextCorrect(text)) {
        skip()
    } else
        UiState.Error

    override fun skip() = if (repository.isLastWord())
        UiState.GameOver(repository.score())
    else {
        repository.next()
        init()
    }

    override fun restart(): UiState {
        repository.restart()
        screenRepository.saveNeedNewGameData()
        navigation.update(LoadScreen)
        return UiState.Empty
    }
}

interface SkipActions {
    fun skip(): UiState
    fun restart(): UiState
}