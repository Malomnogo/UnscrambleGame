package com.malomnogo.unscramblegame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.malomnogo.unscramblegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = GameViewModel()

        binding.submitButton.setOnClickListener {
            val uiState = viewModel.submit(binding.inputEditText.text.toString())
            uiState.show(binding)
        }
        binding.skipButton.setOnClickListener {
            val uiState = viewModel.skip()
            uiState.show(binding)
        }
        binding.inputEditText.doAfterTextChanged {
            val uiState = viewModel.update(binding.inputEditText.text.toString())
            uiState.show(binding)
        }

        val uiState = viewModel.init()
        uiState.show(binding)

    }
}