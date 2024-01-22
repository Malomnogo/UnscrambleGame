package com.malomnogo.unscramblegame

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.malomnogo.unscramblegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var uiState: UiState
    private lateinit var viewModel: GameViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as UnscrambleApplication).viewModel()

        binding.submitButton.setOnClickListener {
            uiState = viewModel.submit(binding.inputEditText.text.toString())
            uiState.show(binding)
        }
        binding.skipButton.setOnClickListener {
            uiState = uiState.skip(viewModel)
            uiState.show(binding)
        }
        binding.inputEditText.doAfterTextChanged {
            val uiState = viewModel.update(binding.inputEditText.text.toString())
            uiState.show(binding)
        }

        uiState = if (savedInstanceState == null) {
            viewModel.init()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getSerializable("uiState", UiState::class.java) as UiState
            } else {
                savedInstanceState.getSerializable("uiState") as UiState
            }
        }
        uiState.show(binding)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("uiState", uiState)
    }
}