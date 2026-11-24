package com.example.corpaliv_app_eq13.viewmodel

import com.example.corpaliv_app_eq13.navigation.NavigationEvent
import com.example.corpaliv_app_eq13.navigation.Screen

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*

import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {

        Dispatchers.setMain(dispatcher)

        viewModel = MainViewModel()
    }

    @After
    fun tearDown() {

        Dispatchers.resetMain()
    }

    @Test
    fun navegarAPantalla() = runTest(dispatcher) {

        val evento = async(
            start = CoroutineStart.UNDISPATCHED
        ) {
            viewModel.navigationEvents.first()
        }

        viewModel.navigateTo(Screen.Profile)

        advanceUntilIdle()

        val resultado = evento.await()

        assertTrue(
            resultado is NavigationEvent.NavigateTo
        )

        assertEquals(
            Screen.Profile,
            (resultado as NavigationEvent.NavigateTo).route
        )
    }

    @Test
    fun navegarHaciaAtras() = runTest(dispatcher) {

        val evento = async(
            start = CoroutineStart.UNDISPATCHED
        ) {
            viewModel.navigationEvents.first()
        }

        viewModel.navigateBack()

        advanceUntilIdle()

        assertEquals(
            NavigationEvent.PopBackStack,
            evento.await()
        )
    }

    @Test
    fun navegarHaciaArriba() = runTest(dispatcher) {

        val evento = async(
            start = CoroutineStart.UNDISPATCHED
        ) {
            viewModel.navigationEvents.first()
        }

        viewModel.navigateUp()

        advanceUntilIdle()

        assertEquals(
            NavigationEvent.NavigateUp,
            evento.await()
        )
    }
}