package com.task.newsfeedapp.screens.login_screesn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.task.newsfeedapp.R
import com.task.newsfeedapp.base.BaseViewModel
import com.task.newsfeedapp.component.login_component.EmailTextField
import com.task.newsfeedapp.component.login_component.PasswordTextField
import com.task.newsfeedapp.component.login_component.PrivacyAndTerms
import com.task.newsfeedapp.navigation.OnboardingNavigationObject
import com.task.newsfeedapp.utils.state.AuthState

@Composable
fun LoginScreen(navController: NavController,authViewModel: BaseViewModel) {
    var email by rememberSaveable  { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var hasNavigated by rememberSaveable { mutableStateOf(false) }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(authState.value) {
        if (!hasNavigated){
            when (authState.value) {
                is AuthState.Authenticate -> {
                    hasNavigated=true
                    navController.navigate(OnboardingNavigationObject.BOTTOM_SHEET_SCREEN)
                }
                is AuthState.Error -> Toast.makeText(
                    context,
                    (authState.value as AuthState.Error).message,
                    Toast.LENGTH_LONG
                ).show()

                else -> Unit
            }
        }

    }
    Surface(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                Box(
                    modifier = Modifier.fillMaxWidth().wrapContentSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.login_corner_bg),
                        contentDescription = "",
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.TopEnd)
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(80.dp)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.img_login),
                            contentDescription = "Login Illustration",
                            modifier = Modifier
                                .size(200.dp) // Adjust size as needed
                                .align(Alignment.Center) // Center it in Box
                        )
                    }
                }


                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column {
                                    Text(
                                        "Hi Welcome!",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("Submit your Mobile number", color = Color.White, fontSize = 14.sp)
                                }

                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Divider(
                                    color = Color.White,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp)
                                )
                                Text(
                                    text = "Login or Sign up",
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                Divider(
                                    color = Color.White,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            EmailTextField(value = email, onValueChange = {email=it})
                            Spacer(modifier = Modifier.height(10.dp))
                            PasswordTextField(value = password, onValueChange = {password=it})
                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = { authViewModel.login(email,password)},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFFFA000
                                    )
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Login")
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Divider(
                                    color = Color.White,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp)
                                )
                                Text(
                                    text = "Or",
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                Divider(
                                    color = Color.White,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp)
                                )
                            }

                            Button(
                                onClick = { navController.navigate(OnboardingNavigationObject.SIGNUP_SCREEN) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFFFA000
                                    )
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("SignUp")
                            }

                            Spacer(modifier = Modifier.weight(1f)) // Pushes the text to the bottom
                            PrivacyAndTerms()
                        }
                    }
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAstrologyTalk() {
//    LoginScreen(navController = NavController(LocalContext.current), authViewModel = AuthViewModel())

}