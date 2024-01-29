package com.malomnogo.unscramblegame.game

import android.view.View
import com.malomnogo.unscramblegame.R
import com.malomnogo.unscramblegame.databinding.FragmentGameBinding
import java.io.Serializable

interface UiState : Serializable {
    fun show(binding: FragmentGameBinding)
    fun skip(viewModel: SkipActions): UiState = viewModel.skip()

    data class Initial(
        private val counter: String,
        private val score: Int,
        private val shuffleWord: String
    ) : UiState {
        override fun show(binding: FragmentGameBinding) {
            with(binding) {
                scoreTextView.text = scoreTextView.context.getString(R.string.score, score)
                counterTextView.text = counter
                input.show()
                shuffledWordTextView.text = shuffleWord
                submitButton.visibility = View.VISIBLE
                counterTextView.visibility = View.VISIBLE
                skipButton.setText(R.string.skip)
            }
        }
    }

    object Error : UiState {
        override fun show(binding: FragmentGameBinding) {
            with(binding) {
                input.showError()
                submitButton.isEnabled = false
                skipButton.setText(R.string.skip)
            }
        }
    }

    object ValidInput : UiState {
        override fun show(binding: FragmentGameBinding) {
            with(binding) {
                submitButton.isEnabled = true
                input.clearError()
                skipButton.setText(R.string.skip)
            }
        }
    }

    object InvalidInput : UiState {
        override fun show(binding: FragmentGameBinding) {
            with(binding) {
                input.clearError()
                submitButton.isEnabled = false
                skipButton.setText(R.string.skip)
            }
        }
    }

    object Empty : UiState {
        override fun show(binding: FragmentGameBinding) = Unit
    }

    data class GameOver(private val score: Int) : UiState {
        override fun show(binding: FragmentGameBinding) = with(binding) {
            input.hide()
            scoreTextView.text = scoreTextView.context.getString(R.string.score, score)
            submitButton.visibility = View.INVISIBLE
            counterTextView.visibility = View.INVISIBLE
            skipButton.setText(R.string.restart)
            shuffledWordTextView.setText(R.string.game_over)

        }

        override fun skip(viewModel: SkipActions) = viewModel.restart()
    }
}