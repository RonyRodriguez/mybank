package com.storicard.mybank.ui.screen.registration


import android.net.Uri
import android.widget.Toast
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.storicard.datasource.dto.UserDTO
import com.storicard.mybank.ui.handler.CameraHandler
import com.storicard.presentation.model.toUserModel
import com.storicard.presentation.viewmodel.AddUserState
import com.storicard.presentation.viewmodel.AddUserViewModel

@Composable
fun RegistrationPage2(
    cameraHandler: CameraHandler,
    lifecycleCameraController: LifecycleCameraController,
    userDTO: UserDTO,
    addUserViewModel: AddUserViewModel = hiltViewModel(),
    onNext: (UserDTO) -> Unit
) {

    val userDTO by remember { mutableStateOf(userDTO) }

    val addUserState by addUserViewModel.addUserState.observeAsState()
    when (addUserState) {
        is AddUserState.Success -> {
            onNext(userDTO)
        }

        is AddUserState.Error -> {
            Toast.makeText(
                LocalContext.current,
                "Error al hacer login",
                Toast.LENGTH_SHORT
            ).show()
        }

        else -> {
            //
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CameraPreview(
            controller = lifecycleCameraController,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        TakePictureButton(cameraHandler) { uri ->
            addUserViewModel.addUser(userDTO.toUserModel(), uri)
        }
    }
}

@Composable
fun TakePictureButton(cameraHandler: CameraHandler, onUriSelected: (Uri) -> Unit) {
    Button(
        onClick = {
            cameraHandler.takePhoto(
                onUriSelected
            )
        },
        modifier = Modifier
            .padding(16.dp)
            .widthIn(max = 200.dp)
    ) {
        Text("Take Picture")
    }
}


@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}
