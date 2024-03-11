package com.daftmobile.listtap.presentation.maincontent

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daftmobile.listtap.R
import com.daftmobile.listtap.data.model.Element
import com.daftmobile.listtap.data.model.ElementColor
import com.daftmobile.listtap.presentation.Action

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    // In real life, you should use dependency injection to provide the ViewModel
    // But as we are not allowed to change the plugin section in build.gradle to add ksp or kapt plugin so we can't use DI.
    // So we use Factory to create ViewModel with parameter
    mainViewModel: MainContentViewModel = viewModel(factory = MainContentViewModelFactory())
) {

    val uiState by mainViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn {
            itemsIndexed(uiState.elements, key = { index, _ -> index }) { index, item ->
                Column(modifier = Modifier.animateItemPlacement()) {
                    ItemRow(item, index, mainViewModel::submitAction)
                    if (index < uiState.elements.lastIndex) Divider(color = MaterialTheme.colors.onBackground, thickness = 1.dp)
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.currentAction?.let {
                Text(text = it)
            }

            Button(modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (uiState.isRunning) colorResource(R.color.red) else colorResource(R.color.green),
                    contentColor = MaterialTheme.colors.onPrimary
                ),
                onClick = {
                    mainViewModel.submitAction(Action.Start)
                }) {
                if (uiState.isRunning) Text(text = stringResource(id = R.string.stop).uppercase(), fontSize = 24.sp)
                else Text(text = stringResource(id = R.string.start).uppercase(), fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun ItemRow(item: Element, index: Int, intent: (action: Action) -> Unit) {

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                intent(Action.IncrementItemOnClick(item, index))
            }) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .padding(16.dp)
                .background(
                    if (item.color == ElementColor.BLUE) colorResource(R.color.blue)
                    else colorResource(R.color.red), shape = CircleShape
                )
        )

        Text(
            text = if (item.color == ElementColor.RED) (item.value * 3).toString() else item.value.toString(),
            fontSize = 24.sp,
            color = MaterialTheme.colors.onBackground
        )
    }
}
