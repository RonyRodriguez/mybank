package com.storicard.mybank.ui.navigation

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.storicard.mybank.ui.handler.CameraHandler
import com.storicard.mybank.ui.screen.dashboard.DashboardScreen
import com.storicard.mybank.ui.screen.login.LoginScreen
import com.storicard.mybank.ui.screen.registration.WizardScreen
import com.storicard.presentation.navigation.Screen
import com.storicard.presentation.viewmodel.SharedViewModel

object AppNavGraph {

    private const val startDestination = Screen.LOGIN

    @Composable
    fun MyAppNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        sharedViewModel: SharedViewModel,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Screen.LOGIN) {
                LoginScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(Screen.REGISTRATION) {
                val (controller, cameraHandler) = provideCameraControllerAndHandler()
                WizardScreen(cameraHandler, controller, navController = navController)
            }

            composable(Screen.DASHBOARD) {



                DashboardScreen(navController, sharedViewModel = sharedViewModel)
            }

        }
    }

}

@Composable
private fun provideCameraControllerAndHandler(): Pair<LifecycleCameraController, CameraHandler> {
    val context = LocalContext.current.applicationContext

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }

    val cameraHandler = remember {
        CameraHandler(controller, context)
    }

    return controller to cameraHandler
}
