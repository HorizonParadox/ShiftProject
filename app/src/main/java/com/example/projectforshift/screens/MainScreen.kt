package com.example.projectforshift.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.projectforshift.MainViewModel
import com.example.projectforshift.data.models.Card

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    val cardInfo = viewModel.allInfo.observeAsState().value
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = cardInfo.toString())
    }
}
@Composable
fun CardInfoItem(item: Card){

}