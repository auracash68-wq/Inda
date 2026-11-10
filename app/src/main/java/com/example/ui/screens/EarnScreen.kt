package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.components.CoinBalanceHeader
import com.example.ui.theme.MainScreenBackground
import com.example.viewmodel.CashIndiaUiState

@Composable
fun EarnScreen(
    uiState: CashIndiaUiState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MainScreenBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("earn_screen")
    ) {
        CoinBalanceHeader(
            isEarnHomeHeader = true,
            title = "Cash India",
            badgeText = uiState.earnHeaderBalanceText
        )
    }
}
