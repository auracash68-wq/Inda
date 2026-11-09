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
import com.example.ui.components.CpxSurveyLiveCard
import com.example.ui.components.PubScaleOfferwallLiveCard
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
    onCreditCoins: (Int, String) -> Unit = { _, _ -> },
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("earn_screen")
    ) {
        // A. Top Header
        CoinBalanceHeader(
            isEarnHomeHeader = true,
            title = "Cash India",
            badgeText = uiState.earnHeaderBalanceText
        )

        Spacer(modifier = Modifier.height(10.dp))

        // B. Playtime Earning Banner
        RewardCard(
            onPlayNowClick = {
                onOpenSheet(
                    ActiveSheet.OfferLaunch(
                        title = "adjoe Playtime",
                        provider = "Playtime Engine",
                        rewardText = "Play Now",
                        instructions = "Play featured games to earn coins continuously. Partner engine ready for integration."
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // C. Paid Surveys Section
        Text(
            text = "Paid Surveys",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (uiState.surveys.isNotEmpty()) {
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
                                    title = "${survey.durationText} Survey",
                                    provider = survey.providerName,
                                    rewardText = survey.rewardText,
                                    instructions = "Complete the survey honestly. Rewards are credited upon partner verification."
                                )
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        } else {
            // Live Dynamic Survey Card
            CpxSurveyLiveCard(
                onStartSurvey = {
                    onOpenSheet(
                        ActiveSheet.OfferLaunch(
                            title = "CPX Research Surveys",
                            provider = "CPX Research",
                            rewardText = "5 - 15 Mins",
                            instructions = "Complete surveys with instant verification. Connect your survey partner to begin."
                        )
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // D. App Tasks Section Header + Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "App Tasks",
                fontFamily = CashIndiaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.5.sp,
                color = PrimaryNavy
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isAllSelected = uiState.selectedTaskFilter == TaskFilter.ALL_TASKS
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .then(
                            if (isAllSelected) {
                                Modifier.background(brush = OrangeButtonGradient)
                            } else {
                                Modifier.background(UnselectedChipBg)
                            }
                        )
                        .clickable { onSelectFilter(TaskFilter.ALL_TASKS) }
                        .padding(horizontal = 12.dp)
                        .testTag("chip_all_tasks"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "All Tasks",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.5.sp,
                        color = if (isAllSelected) PureWhite else PrimaryNavy
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                val isEasySelected = uiState.selectedTaskFilter == TaskFilter.EASY_INSTALL
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .then(
                            if (isEasySelected) {
                                Modifier.background(brush = OrangeButtonGradient)
                            } else {
                                Modifier.background(UnselectedChipBg)
                            }
                        )
                        .clickable { onSelectFilter(TaskFilter.EASY_INSTALL) }
                        .padding(horizontal = 12.dp)
                        .testTag("chip_easy_install"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Easy Install",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.5.sp,
                        color = if (isEasySelected) PureWhite else PrimaryNavy
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (filteredTasks.isNotEmpty()) {
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
                                    provider = "Offerwall",
                                    rewardText = task.rewardText,
                                    instructions = "${task.description}. Follow partner instructions to receive credit."
                                )
                            )
                        }
                    )
                }
            }
        } else {
            // Live Dynamic Offerwall Card
            PubScaleOfferwallLiveCard(
                onOpenOfferwall = {
                    onOpenSheet(
                        ActiveSheet.OfferLaunch(
                            title = "PubScale High-Paying Tasks",
                            provider = "Offerwall Partner",
                            rewardText = "Earn ₹10 to ₹500+",
                            instructions = "Complete app install tasks and offers. Ready for partner engine integration."
                        )
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
