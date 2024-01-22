package com.malomnogo.unscramblegame

class GameViewModel(private val repository: GameRepository) : SkipActions {

    fun init() = UiState.Initial(
        "${repository.currentWordPosition()}/${repository.maxWordsCount()}",
        repository.score(),
        repository.shuffleWord()
    )

    fun update(text: String) = if (text.length == repository.shuffleWord().length)
        UiState.ValidInput(
            "${repository.currentWordPosition()}/${repository.maxWordsCount()}",
            repository.score(),
            repository.shuffleWord()
        )
    else
        UiState.InvalidInput(
            "${repository.currentWordPosition()}/${repository.maxWordsCount()}",
            repository.score(),
            repository.shuffleWord()
        )

    fun submit(text: String) = if (repository.isTextCorrect(text))
        skip()
    else
        UiState.Error(
            "${repository.currentWordPosition()}/${repository.maxWordsCount()}",
            repository.score(),
            repository.shuffleWord()
        )

    override fun skip() = if (repository.isLastWord())
        UiState.GameOver(repository.score())
    else {
        repository.next()
        init()
    }

    override fun restart(): UiState {
        repository.restart()
        return init()
    }
}

interface SkipActions {

    fun skip(): UiState

    fun restart(): UiState
}