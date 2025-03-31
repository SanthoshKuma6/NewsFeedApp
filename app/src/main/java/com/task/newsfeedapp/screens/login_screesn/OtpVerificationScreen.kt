package com.task.newsfeedapp.screens.login_screesn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.task.newsfeedapp.R
import com.task.newsfeedapp.component.login_component.OtpInputBox


@Composable
fun OtpVerificationScreen(navController: NavHostController) {
    var otp by remember { mutableStateOf("") }
    val annotatedText = buildAnnotatedString {
        append("If you didn't receive a code! ")
        withStyle(
            style = SpanStyle(
                color = Color.Yellow,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Resend")
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.login_corner_bg),
                        contentDescription = "",
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.TopEnd)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(80.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.otp_verification_icon),
                            contentDescription = "",
                            modifier = Modifier
                                .align(
                                    Alignment.Center
                                )
                                .size(200.dp)
                        )

                    }

                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxWidth()
                            .fillMaxSize(),
                        shape = RoundedCornerShape(16.dp),
                        colors = androidx.compose.material3.CardDefaults.cardColors(
                            containerColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Column(modifier = Modifier.padding(15.dp)) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier) {
                                    Text(
                                        "OTP Verification",
                                        color = Color.White,
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        "An 4 digit code has been sent to your number",
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )


                                }

                            }
                            Spacer(Modifier.height(10.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center


                            ){
                                Text(
                                    "08.56",
                                    modifier = Modifier,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            }




                            Spacer(Modifier.height(10.dp))
                            OtpInputBox(otpValue = otp, onOtpChange = { otp = it })
                            Spacer(Modifier.height(10.dp))
                            Button(
                                onClick = { navController.navigate("VerificationSuccess") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFFFA000
                                    )
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Verify OTP", modifier = Modifier)

                            }
                            Spacer(Modifier.height(10.dp))

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = annotatedText,
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    modifier = Modifier.clickable { /* Handle resend action */ }
                                )
                            }

                            Spacer(Modifier.height(10.dp))


                        }

                    }
                }

            }


        }

    }
}