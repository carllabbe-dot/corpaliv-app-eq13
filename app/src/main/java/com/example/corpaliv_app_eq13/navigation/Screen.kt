package com.example.corpaliv_app_eq13.navigation

sealed class Screen(val route:String){
    data object Home : Screen (route="home_page")

    data object Profile: Screen (route="profile_page")

    data object Settings : Screen(route="settings_page")

    data object Login : Screen(route = "login_page")
    data object Forms : Screen(route = "forms_page")
    data object Colaborate : Screen(route = "colaborate_page")
    data object Us : Screen(route = "us_page")

    data object Solicitudes : Screen(route = "solicitudes_page")
    data object Discapacidad : Screen(route = "discapacidad_page")
}

data class Detail(val itemId:String) : Screen(route="detail_page{itemId}"){
    fun buildRoute():String{
        return route.replace(oldValue="{itemId}",newValue=itemId)
    }
}