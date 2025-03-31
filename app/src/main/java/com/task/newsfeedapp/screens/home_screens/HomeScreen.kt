package com.task.newsfeedapp.screens.home_screens


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.task.newsfeedapp.R
import com.task.newsfeedapp.screens.payment.StartPayment

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    Surface(modifier = Modifier
        .fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.home_background))
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection()
            Spacer(Modifier.height(20.dp))
            HoroscopeSection()
            ServiceSection()
            RecommendedAstrologers(context)
        }
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.wrapContentSize()) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth()
            ) {
                // Background Image
                Image(
                    painter = painterResource(R.drawable.home_screen_bg),
                    contentDescription = "home",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                // Text Overlay on Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp), // Match image height
                    contentAlignment = Alignment.Center // Center content inside
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Hello Rajeshwari,",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Welcome to Astro",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    }
}


@Composable
fun HoroscopeSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Daily Horoscope", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(
            text = "The stars are on your favor, you can't be horrible. They won't let you down!",
            fontSize = 14.sp
        )
        Spacer(Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth() .horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp),) {
            HoroscopeCard(icon = R.drawable.ic_lio, "Leo")
            HoroscopeCard(icon = R.drawable.ic_virgo, "Virgo")
            HoroscopeCard(icon = R.drawable.ic_cancer, "Cancer")
            HoroscopeCard(icon = R.drawable.ic_cancer, "Cancer")
            HoroscopeCard(icon = R.drawable.ic_lio, "Leo")
            HoroscopeCard(icon = R.drawable.ic_cancer, "Cancer")
            HoroscopeCard(icon = R.drawable.ic_lio, "Leo")
            HoroscopeCard(icon = R.drawable.ic_virgo, "Virgo")
            HoroscopeCard(icon = R.drawable.ic_cancer, "Cancer")
            HoroscopeCard(icon = R.drawable.ic_lio, "Leo")
            HoroscopeCard(icon = R.drawable.ic_virgo, "Virgo")
            HoroscopeCard(icon = R.drawable.ic_cancer, "Cancer")
        }
    }
}

@Composable
fun HoroscopeCard(icon: Int, sign: String) {
    Card(
        modifier = Modifier
            .width(117.dp)
            .height(138.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = sign,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(Modifier.height(10.dp))
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(
                        text = sign,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )

                }

            }
        }
    }
}

@Composable
fun ServiceSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Astro Services", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),elevation = 4.dp,
            backgroundColor = colorResource(R.color.card_color)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ServiceButton(icon = R.drawable.direct_call, "Call")
                ServiceButton(icon = R.drawable.direct_chat, "Chat")
                ServiceButton(icon = R.drawable.direct_video, "Video")
                ServiceButton(icon = R.drawable.ic_report, "Report")
            }
        }
    }
}

@Composable
fun ServiceButton(icon: Int, service: String) {
    Button(
        onClick = { },
        modifier = Modifier
            .wrapContentSize()
            .wrapContentHeight()
            .wrapContentWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White) // Set background color here
    ) {
        Column(modifier = Modifier) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Green
                )
            }
            Spacer(Modifier.height(10.dp))

            Text(text = service, color = Color.Black) // Set text color for visibility

        }
    }
}
@Composable
fun RecommendedAstrologers(context: Context) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Recommended Astrologers", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()), // Enable horizontal scrolling
            horizontalArrangement = Arrangement.spacedBy(10.dp) // Spacing between cards
        ) {
            val astrologers = listOf(
                "Astro Vivek K.",
                "Acharya Anu",
                "Dharmik",
                "Pandit Raj"
            )

            astrologers.forEach { name ->
                AstrologerCard(imageRes = R.drawable.profile_pic, name = name) {
                    // Handle button click (e.g., navigate to payment)
                    StartPayment(context, 30)
                }
            }
        }
    }
}

@Composable
fun AstrologerCard(imageRes: Int, name: String, onConnectClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(145.dp)
            .height(204.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Astrologer Image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(10.dp))

            Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))

            Text(text = "₹ 30/min", fontSize = 14.sp, color = Color.Gray)
            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { onConnectClick() }, // Trigger payment or navigation
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(text = "Connect", color = Color.White)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CallPreviewProfileCard() {
    HomeScreen(navController = NavHostController(LocalContext.current))
}
