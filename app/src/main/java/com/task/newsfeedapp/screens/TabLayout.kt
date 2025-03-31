package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

/**
 * SANTHOSH
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleList(navController: NavHostController) {
    val searchQuery = remember { mutableStateOf("") }
    val tabIndex = remember { mutableIntStateOf(0) }
    val tabs = listOf("Feeds", "Bookmarks")

    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.height(20.dp))
            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                label = { Text("Search...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFFFFF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            TabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = tabIndex.value,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex.intValue]),
                        color = Color.Black
                    )
                }) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = {
                        Text(
                            text = title, style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        )
                    }, selected = tabIndex.intValue == index, onClick = { tabIndex.intValue = index })
                }
            }


            TabContent(tabIndex.intValue, navController)

        }
    }, content = { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Log.d("TAG", "ArticleList: ")
            TabContent(tabIndex.intValue, navController)

        }
    })
}


@Composable
fun TabContent(tabIndex: Int, navController: NavController) {
    when (tabIndex) {
        0 -> FeedsScreen(navController)
        1 -> BookmarksScreen(navController)


    }
}
