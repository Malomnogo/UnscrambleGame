package com.malomnogo.unscramblegame

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.malomnogo.unscramblegame.game.GameRepository
import com.malomnogo.unscramblegame.game.GameViewModel
import com.malomnogo.unscramblegame.game.LocalStorage
import com.malomnogo.unscramblegame.game.PermanentStorage
import com.malomnogo.unscramblegame.game.Shuffle
import com.malomnogo.unscramblegame.load.data.LoadRepository
import com.malomnogo.unscramblegame.load.data.WordsCacheDataSource
import com.malomnogo.unscramblegame.load.data.WordsService
import com.malomnogo.unscramblegame.load.presentation.LoadViewModel
import com.malomnogo.unscramblegame.load.presentation.RunAsync
import com.malomnogo.unscramblegame.load.presentation.UiObservable
import com.malomnogo.unscramblegame.main.MainViewModel
import com.malomnogo.unscramblegame.main.NavigationObservable
import com.malomnogo.unscramblegame.main.ScreenRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UnscrambleApplication : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val makeViewModel = ProvideViewModel.Base(this)
        factory = ProvideViewModel.Factory(makeViewModel)
    }

    override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
        return factory.viewModel(clasz)
    }

}

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(clasz: Class<out T>): T

    class Factory(private val makeViewModel: ProvideViewModel) : ProvideViewModel {
        private val map = HashMap<Class<out ViewModel>, ViewModel>()
        override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
            return if (map.containsKey(clasz))
                map[clasz]
            else {
                val viewModel = makeViewModel.viewModel(clasz)
                map[clasz] = viewModel
                viewModel
            } as T
        }
    }

    class Base(context: Context) : ProvideViewModel {
        private val localStorage = LocalStorage.Base(context)
        private val screenRepository = ScreenRepository.Base(localStorage)
        private val cacheDataSource = WordsCacheDataSource.Base(localStorage, Gson())
        private val navigationObservable: NavigationObservable = NavigationObservable.Base()

        override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
            val isRelease = !BuildConfig.DEBUG
            val shuffle = if (isRelease) Shuffle.Base() else Shuffle.Reversed()
            val wordsCount = if (isRelease) 10 else 2

            return when (clasz) {
                LoadViewModel::class.java -> {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    val client: OkHttpClient = OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build()
                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://random-word-api.vercel.app/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    LoadViewModel(
                        repository = LoadRepository.Base(
                            retrofit.create(WordsService::class.java),
                            cacheDataSource,
                            screenRepository,
                            wordsCount
                        ),
                        uiObservable = UiObservable.Base(),
                        navigation = navigationObservable,
                        runAsync = RunAsync.Base()
                    )
                }

                MainViewModel::class.java -> MainViewModel(
                    repository = screenRepository,
                    navigation = navigationObservable
                )

                GameViewModel::class.java -> GameViewModel(
                    repository = GameRepository.Base(
                        shuffle = shuffle,
                        wordsCount = wordsCount,
                        permanentStorage = PermanentStorage.Base(localStorage),
                        cacheDataSource = cacheDataSource
                    ),
                    screenRepository = screenRepository,
                    navigation = navigationObservable
                )

                else -> throw IllegalStateException()
            } as T
        }
    }
}