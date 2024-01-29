package com.malomnogo.unscramblegame.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.malomnogo.unscramblegame.ProvideViewModel
import com.malomnogo.unscramblegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var navigation: Navigation
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = viewModel(MainViewModel::class.java)

        navigation = object : Navigation {
            override fun update(screen: Screen) {
                screen.show(binding.container.id, supportFragmentManager)
                viewModel.notifyScreenObserved()
            }
        }

        if (savedInstanceState == null) {
            viewModel.screen()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(navigation)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()

    }

    override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
        return (application as ProvideViewModel).viewModel(clasz)
    }
}