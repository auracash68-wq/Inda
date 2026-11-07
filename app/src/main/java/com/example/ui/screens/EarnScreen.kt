package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActiveSheet
import com.example.model.TaskFilter
import com.example.ui.components.AppTaskCard
import com.example.ui.components.CoinBalanceHeader
import com.example.ui.components.RewardCard
import com.example.ui.components.SurveyCard
import com.example.ui.theme.CashIndiaFontFamily
import com.example.ui.theme.MainScreenBackground
import com.example.ui.theme.OrangeButtonGradient
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.PureWhite
import com.example.ui.theme.UnselectedChipBg
import com.example.viewmodel.CashIndiaUiState

@Composable
fun EarnScreen(
    uiState: CashIndiaUiState,
    onSelectFilter: (TaskFilter) -> Unit,
    onOpenSheet: (ActiveSheet) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val filteredTasks = when (uiState.selectedTaskFilter) {
        TaskFilter.ALL_TASKS -> uiState.allAppTasks
        TaskFilter.EASY_INSTALL -> uiState.allAppTasks.filter { it.isEasyInstall }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MainScreenBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("earn_screen")
    ) {
        // A. Top Header
        CoinBalanceHeader(
            isEarnHomeHeader = true,
            title = "Cash India",
            badgeText = uiState.earnHeaderBalanceText
        )

        Spacer(modifier = Modifier.height(6.dp))

        // B. Featured Gaming Banner
        RewardCard(
            onPlayNowClick = {
                onOpenSheet(
                    ActiveSheet.OfferLaunch(
                        title = "Play Games & Earn Every Minute",
                        provider = "adjoe Playtime",
                        rewardText = "Instant coins per minute",
                        instructions = "Select any partner game from the adjoe Playtime wall. Coins are credited after playtime telemetry verification with VPN turned off."
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // C. Daily Paid Surveys Section
        Text(
            text = "Daily Paid Surveys (Guaranteed Bonus)",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 17.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            uiState.surveys.forEach { survey ->
                SurveyCard(
                    survey = survey,
                    onStartSurvey = {
                        onOpenSheet(
                            ActiveSheet.OfferLaunch(
                                title = "${survey.durationText} Daily Survey",
                                provider = survey.providerName,
                                rewardText = survey.rewardText,
                                instructions = "Complete the ${survey.durationText} survey honestly. Rewards (${survey.rewardText}) are credited once CPX Research confirms survey completion."
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // D. Instant App Tasks Section Header + Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Instant App Tasks (2 Mins)",
                fontFamily = CashIndiaFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp,
                color = PrimaryNavy,
                modifier = Modifier.weight(1f, fill = false)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isAllSelected = uiState.selectedTaskFilter == TaskFilter.ALL_TASKS
                Box(
                    modifier = Modifier
                        .height(28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .then(
                            if (isAllSelected) {
                                Modifier.background(brush = OrangeButtonGradient)
                            } else {
                                Modifier.background(UnselectedChipBg)
                            }
                        )
                        .clickable { onSelectFilter(TaskFilter.ALL_TASKS) }
                        .padding(horizontal = 11.dp)
                        .testTag("chip_all_tasks"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "All Tasks",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.5.sp,
                        color = if (isAllSelected) PureWhite else PrimaryNavy
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                val isEasySelected = uiState.selectedTaskFilter == TaskFilter.EASY_INSTALL
                Box(
                    modifier = Modifier
                        .height(28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .then(
                            if (isEasySelected) {
                                Modifier.background(brush = OrangeButtonGradient)
                            } else {
                                Modifier.background(UnselectedChipBg)
                            }
                        )
                        .clickable { onSelectFilter(TaskFilter.EASY_INSTALL) }
                        .padding(horizontal = 11.dp)
                        .testTag("chip_easy_install"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Easy Install",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.5.sp,
                        color = if (isEasySelected) PureWhite else PrimaryNavy
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Task Cards
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            filteredTasks.forEach { task ->
                AppTaskCard(
                    task = task,
                    onEarnClick = {
                        onOpenSheet(
                            ActiveSheet.OfferLaunch(
                                title = task.title,
                                provider = "Verified Partner Task",
                                rewardText = task.rewardText,
                                instructions = "${task.description}. Keep VPN turned off so attribution tracks accurately and credits your wallet upon verification."
                            )
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
    }
}
