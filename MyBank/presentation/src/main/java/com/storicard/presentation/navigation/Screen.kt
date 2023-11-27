package com.storicard.presentation.navigation

sealed class Screen(val route: String) {
    companion object {
        const val LOGIN = "login"
        const val REGISTRATION = "registration"
        const val CAMERA = "camera"
        const val DASHBOARD = "dashboard"
    }

    object Login : Screen(LOGIN)
    object Registration : Screen(REGISTRATION)
}
