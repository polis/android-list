package com.daftmobile.listtap.presentation.maincontent

import androidx.compose.runtime.Stable
import com.daftmobile.listtap.data.model.Element

@Stable
data class UiState(
    val elements: List<Element> = listOf(),
    val isRunning: Boolean = false,
    val currentAction: String? = null
)

