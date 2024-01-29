package com.malomnogo.unscramblegame.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {
    fun show(id: Int, supportFragmentManager: FragmentManager)

    abstract class Replace(private val clasz: Class<out Fragment>) : Screen {
        override fun show(id: Int, supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction()
                .replace(id, clasz, null)
                .commit()
        }
    }

    object Empty : Screen {
        override fun show(id: Int, supportFragmentManager: FragmentManager) = Unit
    }
}