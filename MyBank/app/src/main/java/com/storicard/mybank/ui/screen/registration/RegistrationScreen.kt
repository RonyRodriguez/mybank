package com.storicard.mybank.ui.screen.registration

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.storicard.datasource.dto.UserDTO
import com.storicard.mybank.ui.handler.CameraHandler
import com.storicard.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WizardScreen(
    cameraHandler: CameraHandler,
    lifecycleCameraController: LifecycleCameraController,
    navController: NavController
) {

    var registrationDTO by remember { mutableStateOf(UserDTO()) }
    var currentPage by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Wizard - Pag $currentPage of 3")
                },
                navigationIcon = {
                    if (currentPage > 1) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentPage) {
                1 -> {
                    RegistrationPage1(registrationDTO) {
                        registrationDTO = it
                        currentPage = 2
                    }
                }

                2 -> {
                    RegistrationPage2(
                        cameraHandler,
                        lifecycleCameraController,
                        registrationDTO
                    ) {
                        registrationDTO = it
                        currentPage = 3
                    }
                }

                3 -> {
                    RegistrationPage3(registrationDTO){
                        navController.navigate(Screen.DASHBOARD)
                    }
                }
            }
        }
    }
}
