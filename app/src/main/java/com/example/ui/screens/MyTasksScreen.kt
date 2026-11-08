package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
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
import com.example.model.MyTasksSegment
import com.example.ui.components.CoinBalanceHeader
import com.example.ui.components.EarningsSummaryCard
import com.example.ui.components.OngoingTaskStatusCard
import com.example.ui.components.TaskStatusCard
import com.example.ui.theme.CardBorderColor
import com.example.ui.theme.CashIndiaFontFamily
import com.example.ui.theme.InfoBannerBlueBg
import com.example.ui.theme.LightSurface
import com.example.ui.theme.MainScreenBackground
import com.example.ui.theme.OrangeHorizontalGradient
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.PureWhite
import com.example.viewmodel.CashIndiaUiState

@Composable
fun MyTasksScreen(
    uiState: CashIndiaUiState,
    onSelectSegment: (MyTasksSegment) -> Unit,
    onOpenSheet: (ActiveSheet) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MainScreenBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("my_tasks_screen")
    ) {
        // A. Header
        CoinBalanceHeader(
            isEarnHomeHeader = false,
            title = "My Tasks",
            subtitle = "Track Your Rewards",
            badgeText = uiState.earnHeaderBalanceText
        )

        Spacer(modifier = Modifier.height(10.dp))

        // B. Earnings Summary Card
        EarningsSummaryCard(
            todaysCredited = uiState.todaysCreditedText,
            pendingVerification = uiState.pendingVerificationText
        )

        Spacer(modifier = Modifier.height(16.dp))

        // C. Task Filter Tabs (Ongoing / Completed)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            shape = RoundedCornerShape(20.dp),
            color = LightSurface,
            border = BorderStroke(1.dp, CardBorderColor)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isOngoingSelected =
                    uiState.selectedMyTasksSegment == MyTasksSegment.ONGOING_PENDING

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .then(
                            if (isOngoingSelected) {
                                Modifier.background(brush = OrangeHorizontalGradient)
                            } else {
                                Modifier.background(LightSurface)
                            }
                        )
                        .clickable { onSelectSegment(MyTasksSegment.ONGOING_PENDING) }
                        .testTag("segment_ongoing_pending"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ongoing",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = if (isOngoingSelected) PureWhite else PrimaryNavy
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .then(
                            if (!isOngoingSelected) {
                                Modifier.background(brush = OrangeHorizontalGradient)
                            } else {
                                Modifier.background(LightSurface)
                            }
                        )
                        .clickable { onSelectSegment(MyTasksSegment.COMPLETED_PAID) }
                        .testTag("segment_completed_paid"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Completed",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = if (!isOngoingSelected) PureWhite else PrimaryNavy
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.selectedMyTasksSegment == MyTasksSegment.ONGOING_PENDING) {
            // D. Ongoing Task Cards
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                uiState.ongoingTasks.forEach { task ->
                    OngoingTaskStatusCard(
                        task = task,
                        onClick = {
                            onOpenSheet(
                                ActiveSheet.OfferLaunch(
                                    title = task.title,
                                    provider = "Live Task Telemetry",
                                    rewardText = task.statusText,
                                    instructions = "${task.subtitle}. Keep VPN off during verification."
                                )
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // E. Recently Completed Section
        Text(
            text = "Recently Completed",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            uiState.completedTasks.forEach { completedTask ->
                TaskStatusCard(task = completedTask)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // F. Information Notice Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(InfoBannerBlueBg)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(PrimaryNavy),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "i",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = PureWhite
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Keep VPN off during verification.",
                fontFamily = CashIndiaFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = PrimaryNavy
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
