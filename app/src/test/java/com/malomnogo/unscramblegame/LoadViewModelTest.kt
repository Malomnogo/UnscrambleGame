package com.malomnogo.unscramblegame

import androidx.fragment.app.FragmentManager
import com.malomnogo.unscramblegame.databinding.FragmentLoadBinding
import com.malomnogo.unscramblegame.load.data.LoadRepository
import com.malomnogo.unscramblegame.load.presentation.LoadResult
import com.malomnogo.unscramblegame.load.presentation.LoadUiState
import com.malomnogo.unscramblegame.load.presentation.LoadViewModel
import com.malomnogo.unscramblegame.load.presentation.RunAsync
import com.malomnogo.unscramblegame.load.presentation.UiCallback
import com.malomnogo.unscramblegame.load.presentation.UiObservable
import com.malomnogo.unscramblegame.main.NavigationObservable
import com.malomnogo.unscramblegame.main.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LoadViewModelTest {

    @Test
    fun test() {
        val navigation = Navigation()
        val observable = FakeUiObservable()
        val repository = Repository()
        val runAsync = FakeRunAsync()
        val viewModel = LoadViewModel(
            repository = repository,
            uiObservable = observable,
            navigation = navigation,
            runAsync = runAsync
        )

        viewModel.load()
        assertEquals(listOf(LoadUiState.Progress), observable.states)

        runAsync.returnResult()
        assertEquals(FakeScreen, navigation.result)
        assertEquals(listOf(LoadUiState.Progress, FakeUiState), observable.states)
    }
}

class FakeRunAsync : RunAsync {

    private var cachedUiBlock: (Any) -> Unit = {}
    private var cachedResult: Any = ""

    fun returnResult() {
        cachedUiBlock.invoke(cachedResult)
    }

    override fun <T : Any> start(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        uiBlock: (T) -> Unit
    ) = runBlocking {
        val result = background.invoke()
        cachedResult = result
        cachedUiBlock = uiBlock as (Any) -> Unit
    }
}

private class Navigation(var result: Screen = Screen.Empty) : NavigationObservable {

    override fun clear() = Unit

    override fun navigate(screen: Screen) {
        result = screen
    }

    override fun updateNavigateObserver(observer: com.malomnogo.unscramblegame.main.Navigation) =
        Unit
}

private class FakeUiObservable : UiObservable {

    val states = mutableListOf<LoadUiState>()

    override fun updateUi(loadUiState: LoadUiState) {
        states.add(loadUiState)
    }

    override fun updateObserver(observer: UiCallback) = Unit
}

private class Repository : LoadRepository {

    override suspend fun load() = FakeResult
}

private object FakeResult : LoadResult {

    override fun handle(navigation: NavigationObservable) {
        navigation.navigate(FakeScreen)
    }

    override fun handle(observable: UiObservable) {
        observable.updateUi(FakeUiState)
    }
}

private object FakeScreen : Screen {
    override fun show(id: Int, supportFragmentManager: FragmentManager) = Unit
}

private object FakeUiState : LoadUiState {
    override fun show(binding: FragmentLoadBinding) = Unit
}