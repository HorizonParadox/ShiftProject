package com.example.projectforshift.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.example.projectforshift.MainViewModel
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectforshift.db.model.HistoryModel


@Composable
fun HistoryScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    bin: MutableState<String>
) {
    val historyInfo = viewModel.getAllHistory().observeAsState(listOf()).value
    if (historyInfo.isNotEmpty()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                items(historyInfo) { item ->
                    HistoryItem(navController, item, bin, viewModel)
                }
            }
        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Здесь отобразится история запросов", fontSize = 14.sp)
        }
    }
}

@Composable
fun HistoryItem(
    navController: NavHostController,
    item: HistoryModel,
    bin: MutableState<String>,
    viewModel: MainViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable {
                bin.value = item.cardNumber
                viewModel.getAllInfo(bin.value)
                navController.navigateUp()
            },
    ) {
        Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = "${item.id}",
                fontSize = 24.sp
            )

            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = "BIN/IIN:",
                    fontSize = 20.sp,
                    color = Color.Black.copy(alpha = 0.5f)
                )
                Text(
                    text = item.cardNumber,
                    fontSize = 24.sp
                )
            }
        }
    }
}

