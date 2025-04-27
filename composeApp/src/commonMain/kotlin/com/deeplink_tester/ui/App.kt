package com.deeplink_tester.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deeplink_tester.data.DeeplinkProvider
import com.deeplink_tester.data.models.BaseUrl
import com.deeplink_tester.data.models.Category
import com.deeplink_tester.data.models.Deeplink
import com.deeplink_tester.domain.viewmodel.DeeplinkViewModel
import com.deeplink_tester.rememberPlatformState
//import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun App(viewModel: DeeplinkViewModel = viewModel { DeeplinkViewModel() }) {
    MaterialTheme {
        val isWeb = rememberPlatformState().getPlatform() == "Web"
        val baseUrls = DeeplinkProvider.getBaseUrls(isWeb)
        var expanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf(baseUrls[0]) }
        var selectedBaseUrl by remember { mutableStateOf("${DeeplinkProvider.URL_SCHEME}${selectedItem.url}") }
        var port by remember { mutableStateOf("") }

        viewModel.fetchDeepLinks()

        val state by viewModel.uiState.collectAsState()

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Deeplink Tester",
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Row {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedItem.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Base Url", style = TextStyle(fontSize = 16.sp)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.padding(PaddingValues(top = 8.dp))
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        baseUrls.forEach { item ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedItem = item
                                    selectedBaseUrl =
                                        if (selectedItem.isLocalhost()) item.url else "${DeeplinkProvider.LOCALHOST_SCHEME}${item.url}"
                                    expanded = false
                                },
                            ) { DropdownTextColumn(item) }
                        }
                    }
                }
                if (isWeb && selectedItem.isLocalhost()) {
                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                    OutlinedTextField(
                        value = port,
                        onValueChange = {
                            if (it.length <= DeeplinkProvider.PORT_LENGTH) {
                                val intPort = it.toIntOrNull()
                                if (intPort != null || it.isEmpty()) {
                                    port = it
                                }
                                if (it.length == DeeplinkProvider.PORT_LENGTH) {
                                    val split = selectedBaseUrl.split(":")
                                    selectedBaseUrl = if (split.size > 1) {
                                        "${split[0]}:${it}"
                                    } else {
                                        "$selectedBaseUrl:${it}"
                                    }
                                }
                            }
                        },
                        readOnly = false,
                        label = { Text("Enter the PORT", style = TextStyle(fontSize = 16.sp)) },
                        modifier = Modifier.padding(PaddingValues(top = 8.dp))
                    )
                }
            }
            ExpandableList(state.categories, selectedBaseUrl)
        }
    }
}

@Composable
fun DropdownTextColumn(baseUrl: BaseUrl) {
    Column {
        Text(
            text = baseUrl.name,
            style = TextStyle(fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        )
        Text(text = baseUrl.url, style = TextStyle(fontSize = 16.sp))
    }
}

@Composable
fun ExpandableList(items: List<Category>, baseUrl: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        items.forEach { item ->
            ExpandableListItemView(item, baseUrl)
        }
    }
}

@Composable
fun ExpandableListItemView(item: Category, baseUrl: String) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().clickable(true, onClick = {
                    expanded = !expanded
                })
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand/Collapse"
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            if (expanded) {
                DeeplinkListView(
                    items = item.deepLinks,
                    baseUrl = baseUrl,
                )
            }
        }
    }
}

@Composable
fun DeeplinkListView(
    items: List<Deeplink>,
    baseUrl: String,
) {
    val count = items.size
    val clipboardManager = LocalClipboardManager.current
    val platformState = rememberPlatformState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(count) { index ->
            val deeplink = items[index]
            var absoluteUrl by remember(
                baseUrl,
                deeplink.url
            ) { mutableStateOf("${baseUrl}${deeplink.url}") }

            Row {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(4f)
                ) {
                    Text(
                        text = deeplink.name,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    TextField(
                        value = absoluteUrl,
                        onValueChange = {
                            absoluteUrl = if (!absoluteUrl.startsWith("${baseUrl}/")) {
                                "${baseUrl}/"
                            } else {
                                it
                            }
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            cursorColor = MaterialTheme.colors.primary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .background(Color.White)
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { platformState.launchDeeplink(absoluteUrl) }) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "LaunchDeeplink",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(absoluteUrl))
                        //showToast("Copied to clipboard")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "CopyDeeplink",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}


