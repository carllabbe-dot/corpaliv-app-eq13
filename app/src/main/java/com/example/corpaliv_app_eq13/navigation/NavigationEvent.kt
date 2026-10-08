package com.example.corpaliv_app_eq13.navigation

sealed class NavigationEvent {
    data class NavigateTo(
    val route:Screen,
    val popUpToRoute: Screen? = null,
    val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ): NavigationEvent()

    object PopBackStack: NavigationEvent()
    object NavigateUp:NavigationEvent()
}