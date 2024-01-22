package com.malomnogo.unscramblegame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.malomnogo.unscramblegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var uiState: UiState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isRelease = !BuildConfig.DEBUG
        val shuffle = if (isRelease) Shuffle.Base() else Shuffle.Reversed()
        val wordsCount = if (isRelease) 10 else 2
        val viewModel = GameViewModel(GameRepository.Base(shuffle, wordsCount))

        binding.submitButton.setOnClickListener {
            uiState = viewModel.submit(binding.inputEditText.text.toString())
            uiState.show(binding)
        }
        binding.skipButton.setOnClickListener {
            uiState = uiState.skip(viewModel)
            uiState.show(binding)
        }
        binding.inputEditText.doAfterTextChanged {
            uiState = viewModel.update(binding.inputEditText.text.toString())
            uiState.show(binding)
        }

        uiState = viewModel.init()
        uiState.show(binding)
    }
}