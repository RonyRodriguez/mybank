package com.storicard.mybank.ui.screen.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.storicard.mybank.R
import com.storicard.mybank.ui.theme.BlueGrey200
import com.storicard.mybank.ui.theme.BlueGrey300
import com.storicard.mybank.ui.theme.BlueGrey800
import com.storicard.mybank.ui.theme.PurpleA400
import com.storicard.presentation.navigation.Screen
import com.storicard.presentation.viewmodel.LoginState
import com.storicard.presentation.viewmodel.LoginViewModel
import com.storicard.presentation.viewmodel.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel(), sharedViewModel: SharedViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by loginViewModel.loginState.observeAsState()
    when (val state = loginState) {
        is LoginState.Success -> {
            sharedViewModel.setUser(state.user)
            navController.navigate(Screen.DASHBOARD)
        }

        is LoginState.Error -> {
            Log.e("login","login error")
        }

        else -> {
            //
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LoginTop()

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textStyle = TextStyle(color = BlueGrey800),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = BlueGrey800
            )
        )

        Spacer16()

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textStyle = TextStyle(color = BlueGrey800),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = BlueGrey300
            )
        )

        Spacer16()

        Spacer16()

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    loginViewModel.loginUser(
                        email = email,
                        password = password
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Login",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer16()

        Row(
            modifier = Modifier.clickable { navController.navigate(Screen.REGISTRATION) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "New user? ",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = BlueGrey300
                )
            )

            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = PurpleA400,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer24()
    }
}

@Composable
fun LoginTop() {

    Spacer(modifier = Modifier.height(16.dp))

    Image(
        painter = painterResource(id = R.drawable.logo_small),
        contentDescription = "Logo",
        modifier = Modifier.size(80.dp),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
    )

    Spacer(modifier = Modifier.height(24.dp))

    Text(
        text = "Welcome Back,",
        style = TextStyle(
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    )

    Text(
        text = "Sign in to continue",
        style = TextStyle(
            fontSize = 16.sp,
            color = BlueGrey200,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun Spacer16() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun Spacer24() {
    Spacer(modifier = Modifier.height(24.dp))
}



