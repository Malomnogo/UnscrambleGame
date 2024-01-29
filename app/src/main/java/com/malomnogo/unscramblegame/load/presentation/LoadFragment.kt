package com.malomnogo.unscramblegame.load.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.malomnogo.unscramblegame.ProvideViewModel
import com.malomnogo.unscramblegame.databinding.FragmentLoadBinding

class LoadFragment : Fragment() {

    private var _binding: FragmentLoadBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoadViewModel

    private lateinit var uiCallBack: UiCallBack


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            (requireActivity() as ProvideViewModel).viewModel(clasz = LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }
        viewModel.init(savedInstanceState == null)

        uiCallBack = object : UiCallBack {
            override fun update(loadUiState: LoadUiState) {
                loadUiState.show(binding)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(uiCallBack)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface UiCallBack {

    fun update(loadUiState: LoadUiState)

    object Empty : UiCallBack {
        override fun update(loadUiState: LoadUiState) = Unit
    }
}