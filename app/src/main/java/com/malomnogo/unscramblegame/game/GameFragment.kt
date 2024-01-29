package com.malomnogo.unscramblegame.game

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.malomnogo.unscramblegame.ProvideViewModel
import com.malomnogo.unscramblegame.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var uiState: UiState
    private lateinit var viewModel: GameViewModel

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity() as ProvideViewModel).viewModel(clasz = GameViewModel::class.java)

        binding.submitButton.setOnClickListener {
            uiState = viewModel.submit(binding.input.text())
            uiState.show(binding)
        }
        binding.skipButton.setOnClickListener {
            uiState = uiState.skip(viewModel)
            uiState.show(binding)
        }

        if (savedInstanceState == null) {
            uiState = viewModel.init()
            uiState.show(binding)
        }
    }

    private val watcher = object : AbstractTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            val uiState = viewModel.update(binding.input.text())
            uiState.show(binding)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.input.binding.inputEditText.addTextChangedListener(watcher)
    }

    override fun onPause() {
        super.onPause()
        binding.input.binding.inputEditText.removeTextChangedListener(watcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

abstract class AbstractTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

    }
}