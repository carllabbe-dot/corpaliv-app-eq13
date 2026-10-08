package com.example.corpaliv_app_eq13.viewmodel

import androidx.lifecycle.ViewModel
import com.example.corpaliv_app_eq13.navigation.NavigationEvent
import com.example.corpaliv_app_eq13.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainViewModel: ViewModel() {
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()
    fun navigateTo(screen: Screen) {
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.NavigateTo(route = screen))
        }
    }
    fun navigateBack() {
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.PopBackStack)


        }
    }
    fun navigateUp(){
            CoroutineScope(context = Dispatchers.Main).launch {
                _navigationEvents.emit(value = NavigationEvent.NavigateUp)
            }
    }
}
