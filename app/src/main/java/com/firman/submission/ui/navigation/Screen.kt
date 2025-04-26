package com.firman.submission.ui.navigation

sealed class Screen (val route: String){
    data object Home: Screen("home")
    object Detail : Screen("detail/{characterId}") {
        fun createRoute(characterId: Long) = "detail/$characterId"
    }
    data object About: Screen("about")
}