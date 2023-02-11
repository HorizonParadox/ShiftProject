package com.example.projectforshift.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectforshift.MainViewModel
import com.example.projectforshift.data.models.Card
import com.example.projectforshift.db.model.HistoryModel
import com.example.projectforshift.navigation.Screens
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel, bin: MutableState<String>) {
    val cardInfo = viewModel.allInfo.observeAsState()
    val historyInfo by viewModel.getAllHistory().observeAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            historyInfo?.let { TextField(bin = bin, historyInfo = it, viewModel = viewModel) }
            CardInfoItem(item = cardInfo.value)
            historyInfo?.let { BottomButton(navController) }
        }
    }
}

@Composable
fun CardInfoItem(item: Card?) {
    val context = LocalContext.current
    if (item != null) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxWidth(0.6f)
                ) {
                    Column() {
                        Text(
                            text = "SCHEME / NETWORK",
                            fontSize = 16.sp,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                        item.scheme?.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                        }?.let { Text(text = it, fontSize = 18.sp) }
                    }

                    Column() {
                        Text(
                            text = "BRAND",
                            fontSize = 16.sp,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                        item.brand?.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                        }?.let { Text(text = it, fontSize = 18.sp) }
                    }

                    Column() {
                        Text(
                            text = "CARD NUMBER",
                            fontSize = 16.sp,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                        Row() {
                            Column(modifier = Modifier.padding(end = 8.dp)) {
                                Text(
                                    text = "LENGTH",
                                    fontSize = 16.sp,
                                    color = Color.Black.copy(alpha = 0.5f)
                                )
                                item.number?.length?.let { length ->
                                    Text(text = "$length", fontSize = 18.sp)
                                }
                            }

                            Column() {
                                Text(
                                    text = "LUHN",
                                    fontSize = 16.sp,
                                    color = Color.Black.copy(alpha = 0.5f)
                                )
                                Text(
                                    text = when (item.number?.luhn) {
                                        true -> "Yes"
                                        false -> "No"
                                        null -> ""
                                    },
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxWidth(0.7f)
                ) {
                    Column() {
                        Text(
                            text = "TYPE",
                            fontSize = 16.sp,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                        item.type?.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                        }?.let { Text(text = it, fontSize = 18.sp) }
                    }

                    Column() {
                        Text(
                            text = "PREPAID",
                            fontSize = 16.sp,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                        Text(
                            text = when (item.prepaid) {
                                true -> "Yes"
                                false -> "No"
                                null -> ""
                            },
                            fontSize = 18.sp
                        )
                    }

                    Column() {
                        Text(
                            text = "CURRENCY",
                            fontSize = 16.sp,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                        item.country?.currency?.let { Text(text = it, fontSize = 18.sp) }
                    }
                }
            }

            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text(text = "COUNTRY", fontSize = 18.sp, color = Color.Black.copy(alpha = 0.5f))
                item.country?.emoji?.let { emoji ->
                    item.country.name?.let { name ->
                        item.country.alpha2?.let { alpha2 ->
                            Text(text = "$emoji $name ($alpha2)", fontSize = 20.sp)
                        }
                    }
                }
                item.country?.latitude?.let { latitude ->
                    item.country.longitude?.let { longitude ->
                        Text(
                            modifier = Modifier.clickable {
                                findCityOnMap(context, latitude, longitude)
                            },
                            text = "(latitude: ${latitude.toInt()}, longitude: ${longitude.toInt()})",
                            fontSize = 18.sp,
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }

            Row(modifier = Modifier.padding(top = 8.dp)) {
                Column() {
                    Text(
                        text = "BANK",
                        fontSize = 18.sp,
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                    item.bank?.name?.let { name -> Text(text = name, fontSize = 20.sp) }
                    item.bank?.url?.let { url ->
                        Text(
                            modifier = Modifier.clickable { goToWebsite(context, url) },
                            text = url,
                            fontSize = 20.sp,
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                    item.bank?.phone?.let { phone ->
                        Text(
                            modifier = Modifier.clickable { phoneCall(context, phone) },
                            text = phone,
                            fontSize = 20.sp,
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Здесь отобразится необходимая информация", fontSize = 14.sp)
        }
    }
}

@Composable
fun TextField(
    bin: MutableState<String>,
    historyInfo: List<HistoryModel>,
    viewModel: MainViewModel
) {
    var showClearButton by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .onFocusChanged { focusState ->
                expanded = focusState.isFocused
                showClearButton = (focusState.isFocused)
            },
        value = bin.value,
        onValueChange = { newText ->
            if (newText.length <= 12) bin.value = newText
        },
        label = { Text(text = "BIN/IIN") },
        textStyle = TextStyle(fontSize = 25.sp),
        singleLine = true,
        trailingIcon = {
            if (showClearButton) {
                IconButton(onClick = { bin.value = "" }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Clear")
                }
            }

        },
        keyboardActions = KeyboardActions(
            onSearch = {
                if (bin.value.length >= 4) {
                    viewModel.getAllInfo(bin.value)
                    if (historyInfo.find { it.cardNumber == bin.value } == null) {
                        coroutineScope.launch {
                            viewModel.addNewCardNumber(HistoryModel(cardNumber = bin.value))
                        }
                    }
                } else
                    Toast.makeText(context, "Incorrect number!", Toast.LENGTH_SHORT).show()
                expanded = false
                focusManager.clearFocus()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.NumberPassword
        )
    )
}

@Composable
fun BottomButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                navController.navigate(Screens.History.route)
            },
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        )
        {
            Text(text = "History of previous requests")
        }
    }
}

fun phoneCall(context: Context, phone: String) {
    val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    context.startActivity(phoneIntent)
}

fun goToWebsite(context: Context, link: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://${link}"))
    context.startActivity(browserIntent)
}

fun findCityOnMap(context: Context, latitude: Float, longitude: Float) {
    val uri = java.lang.String.format(
        Locale.ENGLISH, "geo:%f,%f", latitude,
        longitude
    )
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    context.startActivity(intent)
}