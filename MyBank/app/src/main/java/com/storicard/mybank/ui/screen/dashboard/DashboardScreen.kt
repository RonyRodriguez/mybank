package com.storicard.mybank.ui.screen.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.storicard.mybank.ui.theme.Grey40
import com.storicard.mybank.ui.theme.Grey5
import com.storicard.mybank.ui.theme.Indigo900
import com.storicard.mybank.ui.theme.LightBlue100
import com.storicard.mybank.ui.theme.LightBlue500
import com.storicard.presentation.model.MovementModel
import com.storicard.presentation.model.UserModel
import com.storicard.presentation.viewmodel.SharedViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    sharedViewModel.userData.value?.let { Detail(userModel = it) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(userModel: UserModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Grey5)
    ) {
        TopAppBar(
            title = {

                Text(
                    text = userModel.getFullName(),
                    color = LightBlue100,
                    style = MaterialTheme.typography.displayMedium
                )

            }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
                .background(LightBlue500)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Balance",
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = userModel.bankInfoModel.balance.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.small

        ) {
            LazyColumn {
                items(userModel.bankInfoModel.movementModels ?: emptyList()) { movement ->
                    MovementItem(movement = movement)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}

@Composable
fun MovementItem(movement: MovementModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = movement.description,
                color = Indigo900,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = movement.description,
                color = Grey40,
                style = MaterialTheme.typography.titleSmall
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = movement.description,
                color = LightBlue500,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = movement.amount.toString(),
                color = Grey40,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}





