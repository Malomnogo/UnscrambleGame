package com.malomnogo.unscramblegame.load.presentation

import android.view.View
import com.malomnogo.unscramblegame.databinding.FragmentLoadBinding

interface LoadUiState {
    fun show(binding: FragmentLoadBinding)

    object Progress : LoadUiState {
        override fun show(binding: FragmentLoadBinding) = with(binding){
            progressBar.visibility = View.VISIBLE
            retryButton.visibility = View.GONE
            errorTextView.visibility = View.GONE
        }
    }

    data class Error(private val message: String) : LoadUiState {
        override fun show(binding: FragmentLoadBinding) = with(binding){
            progressBar.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = message
            retryButton.visibility = View.VISIBLE
        }
    }

    object Empty : LoadUiState {
        override fun show(binding: FragmentLoadBinding) = Unit
    }
}