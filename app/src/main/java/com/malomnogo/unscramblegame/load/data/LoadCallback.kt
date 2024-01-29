package com.malomnogo.unscramblegame.load.data

interface LoadCallback {
    fun success()
    fun error(msg: String)
}