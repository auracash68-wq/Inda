package com.example.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppTaskItem
import com.example.model.CompletedTaskItem
import com.example.model.OngoingTaskItem
import com.example.model.SurveyOffer
import com.example.model.WithdrawalMethod
import com.example.model.WithdrawalTransaction
import com.example.ui.theme.CardBorderColor
import com.example.ui.theme.CashIndiaFontFamily
import com.example.ui.theme.DividerColor
import com.example.ui.theme.GreenButtonGradient
import com.example.ui.theme.OrangeButtonGradient
import com.example.ui.theme.PendingCapsuleBg
import com.example.ui.theme.PendingCapsuleBorder
import com.example.ui.theme.PendingCapsuleText
import com.example.ui.theme.PendingOrange
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.PureWhite
import com.example.ui.theme.ReferralYellow
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SecondaryText
import com.example.ui.theme.SuccessCapsuleDarkText
import com.example.ui.theme.SuccessCapsuleLightBg
import com.example.ui.theme.SuccessGreen

@Composable
fun RewardCard(
    onPlayNowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier,
        cornerRadius = 18.dp,
        leftTricolorStrip = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: adjoe Playtime game controller illustration
            Image(
                painter = painterResource(id = R.drawable.img_adjoe_controller),
                contentDescription = "adjoe Playtime Game Controller",
                modifier = Modifier
                    .width(104.dp)
                    .height(106.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Vertical divider
            Box(
                modifier = Modifier
                    .width(1.2.dp)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
                    .background(Color(0xFF9EA9C4))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Right: Heading, supporting text, and large orange gradient button
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Play Games &\nEarn Every Minute",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 19.sp,
                    lineHeight = 23.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "No hard levels • Instant coins per minute",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp,
                    lineHeight = 14.sp,
                    color = SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(9.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(brush = OrangeButtonGradient)
                        .clickable { onPlayNowClick() }
                        .testTag("play_now_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Play Now",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = PureWhite
                    )
                }
            }
        }
    }
}

@Composable
fun SurveyCard(
    survey: SurveyOffer,
    onStartSurvey: () -> Unit,
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier,
        cornerRadius = 16.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp, vertical = 11.dp)
        ) {
            // Provider Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CpxResearchLogoIcon(size = 21.dp)
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = survey.providerName,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.5.sp,
                    color = PrimaryNavy,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            // Duration Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = "Survey Duration",
                    tint = PrimaryNavy,
                    modifier = Modifier.size(19.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = survey.durationText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.5.sp,
                    color = PrimaryNavy
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Green reward capsule
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(31.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(brush = GreenButtonGradient),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = survey.rewardText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = PureWhite,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Outlined Navy Start Survey Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(33.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .border(
                        width = 1.4.dp,
                        color = PrimaryNavy,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .clickable { onStartSurvey() }
                    .testTag("start_survey_${survey.id}"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Start Survey",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.5.sp,
                    color = PrimaryNavy
                )
            }
        }
    }
}

@Composable
fun AppTaskCard(
    task: AppTaskItem,
    onEarnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier,
        cornerRadius = 16.dp,
        leftTricolorStrip = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = task.iconRes),
                contentDescription = task.title,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = PrimaryNavy,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = task.description,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.5.sp,
                    color = SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(brush = GreenButtonGradient)
                    .clickable { onEarnClick() }
                    .padding(horizontal = 16.dp)
                    .testTag("task_earn_${task.id}"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = task.rewardText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = PureWhite
                )
            }
        }
    }
}

@Composable
fun EarningsSummaryCard(
    todaysCredited: String,
    pendingVerification: String,
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier,
        cornerRadius = 18.dp,
        topTricolorStrip = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Column: Today's Credited
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Today's Credited",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.5.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    val split = todaysCredited.split(" ", limit = 2)
                    Text(
                        text = split.getOrElse(0) { todaysCredited },
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 19.sp,
                        color = SuccessGreen
                    )
                    if (split.size > 1) {
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = split[1],
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.5.sp,
                            color = SuccessGreen,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
                }
            }

            // Vertical Divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 2.dp)
                    .background(DividerColor)
            )

            // Right Column: Pending Verification
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pending Verification",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.5.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    val split = pendingVerification.split(" ", limit = 2)
                    Text(
                        text = split.getOrElse(0) { pendingVerification },
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 19.sp,
                        color = PendingOrange
                    )
                    if (split.size > 1) {
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = split[1],
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            color = PendingOrange,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OngoingTaskStatusCard(
    task: OngoingTaskItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier.clickable { onClick() },
        cornerRadius = 16.dp,
        leftSolidStripColor = SaffronOrange
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = task.iconRes),
                contentDescription = task.title,
                modifier = Modifier
                    .width(62.dp)
                    .height(64.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.5.sp,
                    color = PrimaryNavy,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (task.progressFraction != null && task.progressLabel != null) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE2E8F2))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(task.progressFraction)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SaffronOrange)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = task.progressLabel,
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                            color = PrimaryNavy
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                } else {
                    Spacer(modifier = Modifier.height(6.dp))
                }
                Text(
                    text = task.subtitle,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            // Orange outlined status capsule
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(PendingCapsuleBg)
                    .border(1.2.dp, PendingCapsuleBorder, RoundedCornerShape(18.dp))
                    .padding(horizontal = 9.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = PendingCapsuleText,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = task.statusText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 11.5.sp,
                    color = PendingCapsuleText,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun TaskStatusCard(
    task: CompletedTaskItem,
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier,
        cornerRadius = 16.dp,
        leftSolidStripColor = PrimaryGreen
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = task.iconRes),
                contentDescription = task.title,
                modifier = Modifier
                    .width(66.dp)
                    .height(58.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.5.sp,
                    color = PrimaryNavy,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = task.description,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.5.sp,
                    color = SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            // Green success capsule with check icon
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(brush = GreenButtonGradient)
                    .padding(horizontal = 10.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = PureWhite,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = task.paidBadgeText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.5.sp,
                    color = PureWhite,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun WithdrawalMethodCard(
    method: WithdrawalMethod,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(106.dp)
            .clickable { onSelect() }
            .testTag("withdrawal_method_${method.id}")
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(14.dp),
            color = PureWhite,
            border = BorderStroke(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) PrimaryGreen else CardBorderColor
            ),
            shadowElevation = 2.dp
        ) {
            when (method) {
                WithdrawalMethod.UPI -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 6.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_upi_logos),
                            contentDescription = "UPI Google Pay PhonePe Paytm",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            contentScale = ContentScale.Fit
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(26.dp)
                                .clip(RoundedCornerShape(13.dp))
                                .background(PrimaryGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Instant UPI",
                                fontFamily = CashIndiaFontFamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 11.5.sp,
                                color = PureWhite
                            )
                        }
                    }
                }

                WithdrawalMethod.GOOGLE_PLAY -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 6.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_google_play),
                            contentDescription = "Google Play Code",
                            modifier = Modifier.size(44.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Google Play\nCode",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.5.sp,
                            lineHeight = 14.sp,
                            color = PrimaryNavy,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                WithdrawalMethod.AMAZON_PAY -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 6.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_amazon_pay),
                            contentDescription = "Amazon Pay",
                            modifier = Modifier
                                .width(68.dp)
                                .height(48.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Amazon Pay",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.5.sp,
                            color = PrimaryNavy,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Top-right green selected check indicator
        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-5).dp)
                    .size(21.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen)
                    .border(1.5.dp, PureWhite, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Selected",
                    tint = PureWhite,
                    modifier = Modifier.size(13.dp)
                )
            }
        }
    }
}

@Composable
fun AmountSelectionButton(
    amount: Int,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(19.dp))
            .then(
                if (isSelected) {
                    Modifier.background(brush = OrangeButtonGradient)
                } else {
                    Modifier
                        .background(PureWhite)
                        .border(1.2.dp, Color(0xFF3E63B6), RoundedCornerShape(19.dp))
                }
            )
            .clickable { onSelect() }
            .testTag("amount_btn_$amount"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "₹$amount",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.5.sp,
            color = if (isSelected) PureWhite else PrimaryNavy
        )
    }
}

@Composable
fun TransactionCard(
    transaction: WithdrawalTransaction,
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier,
        cornerRadius = 15.dp,
        leftSolidStripColor = PrimaryGreen
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (transaction.isUpiCheckIcon) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(PrimaryGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = PureWhite,
                        modifier = Modifier.size(21.dp)
                    )
                }
            } else if (transaction.iconRes != null) {
                Image(
                    painter = painterResource(id = transaction.iconRes),
                    contentDescription = transaction.title,
                    modifier = Modifier.size(34.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = PrimaryNavy,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = transaction.subtitle,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Light green Success status capsule
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(SuccessCapsuleLightBg)
                    .border(1.dp, Color(0xFF7AD889), RoundedCornerShape(14.dp))
                    .padding(horizontal = 11.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = transaction.statusText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.5.sp,
                    color = SuccessCapsuleDarkText
                )
            }
        }
    }
}

@Composable
fun ProfileHeaderCard(
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier,
        cornerRadius = 18.dp,
        topTricolorStrip = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 12.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular blue avatar with SG white initials and thin saffron & green outer border
            Box(
                modifier = Modifier.size(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val stroke = 2.5.dp.toPx()
                    drawArc(
                        color = SaffronOrange,
                        startAngle = 140f,
                        sweepAngle = 200f,
                        useCenter = false,
                        topLeft = Offset(stroke / 2f, stroke / 2f),
                        size = Size(size.width - stroke, size.height - stroke),
                        style = Stroke(width = stroke, cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = PrimaryGreen,
                        startAngle = -20f,
                        sweepAngle = 160f,
                        useCenter = false,
                        topLeft = Offset(stroke / 2f, stroke / 2f),
                        size = Size(size.width - stroke, size.height - stroke),
                        style = Stroke(width = stroke, cap = StrokeCap.Round)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF0E68FF), Color(0xFF0A2E8A))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SG",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 19.sp,
                        color = PureWhite
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Vertical divider between avatar and profile information
            Box(
                modifier = Modifier
                    .width(1.2.dp)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
                    .background(Color(0xFF9EA9C4))
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Middle + Right
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sahid Hosen",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = PrimaryNavy
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShieldVerifiedIcon(size = 19.dp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Active",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.5.sp,
                            color = PrimaryGreen
                        )
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "UPI: sahid@paytm • Member since Oct 2026",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ReferralBanner(
    onInviteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bannerGradient = Brush.horizontalGradient(
        0.0f to Color(0xFFFF7B00),
        0.35f to Color(0xFFD67A00),
        0.65f to Color(0xFF428B0B),
        1.0f to Color(0xFF0A8F12)
    )
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(brush = bannerGradient)
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Gift box illustration
            Image(
                painter = painterResource(id = R.drawable.img_gift_box),
                contentDescription = "Referral Gift Box",
                modifier = Modifier
                    .width(96.dp)
                    .height(106.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Vertical translucent white divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
                    .background(Color(0x88FFFFFF))
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Right: Referral texts + WhatsApp Invite button
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Refer Friends & Earn",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = PureWhite
                )
                Text(
                    text = "₹10 Extra",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    lineHeight = 29.sp,
                    color = ReferralYellow
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Get 10% lifetime commission on friend's earnings",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.5.sp,
                    color = PureWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(7.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(PureWhite)
                        .clickable { onInviteClick() }
                        .testTag("whatsapp_invite_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        WhatsAppCircleIcon(size = 20.dp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Invite Now (WhatsApp)",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.sp,
                            color = Color(0xFFE65C00)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsItemCard(
    title: String,
    subtitle: String,
    leadingIcon: @Composable () -> Unit,
    showGreenStatusDot: Boolean = false,
    isToggleSetting: Boolean = false,
    toggleChecked: Boolean = true,
    onToggleChange: ((Boolean) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    testTag: String = "",
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier.then(
            if (onClick != null) Modifier.clickable { onClick() } else Modifier
        ).testTag(testTag),
        cornerRadius = 15.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                leadingIcon()
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = subtitle,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (isToggleSetting) {
                Switch(
                    checked = toggleChecked,
                    onCheckedChange = { onToggleChange?.invoke(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = PureWhite,
                        checkedTrackColor = SaffronOrange,
                        uncheckedThumbColor = PureWhite,
                        uncheckedTrackColor = Color(0xFFB5C0D4)
                    ),
                    modifier = Modifier.testTag("${testTag}_switch")
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showGreenStatusDot) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(PrimaryGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = PrimaryNavy,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SupportCard(
    title: String,
    description: String,
    leadingIcon: @Composable () -> Unit,
    buttonText: String,
    isGreenChatButton: Boolean,
    onButtonClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    CashIndiaCardSurface(
        modifier = modifier,
        cornerRadius = 15.dp,
        leftSolidStripColor = PrimaryGreen
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                leadingIcon()
            }
            Spacer(modifier = Modifier.width(11.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.5.sp,
                    color = SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (isGreenChatButton) {
                Box(
                    modifier = Modifier
                        .height(34.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(brush = GreenButtonGradient)
                        .clickable { onButtonClick() }
                        .padding(horizontal = 14.dp)
                        .testTag(testTag),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ChatBubble,
                            contentDescription = null,
                            tint = PureWhite,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = buttonText,
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.5.sp,
                            color = PureWhite
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .height(34.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .border(1.4.dp, PrimaryNavy, RoundedCornerShape(17.dp))
                        .clickable { onButtonClick() }
                        .padding(horizontal = 16.dp)
                        .testTag(testTag),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonText,
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.5.sp,
                        color = PrimaryNavy
                    )
                }
            }
        }
    }
}
