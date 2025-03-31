package com.task.newsfeedapp.screens.login_screesn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.task.newsfeedapp.R
import kotlinx.coroutines.delay


@Composable
fun VerificationSuccess(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds delay
        navController.navigate("BottomSheetNavigationApp") // Replace with actual destination
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(R.drawable.login_success), contentDescription = "",Modifier.size(200.dp))
                Spacer(modifier = Modifier.height(16.dp))

                Image(painter = painterResource(R.drawable.success_tick), contentDescription = "")
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Mobile verification has successfully done")

            }
        }

    }

}


@Preview
@Composable
fun PreaviewVerificationSuccess() {

    VerificationSuccess(navController = NavHostController(LocalContext.current))

}
