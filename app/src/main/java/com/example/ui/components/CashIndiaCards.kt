package com.example.ui.components

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
        cornerRadius = 16.dp,
        leftTricolorStrip = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_adjoe_controller),
                contentDescription = "adjoe Playtime",
                modifier = Modifier
                    .width(92.dp)
                    .height(94.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
                    .background(DividerColor)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Play & Earn",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Earn Coins Per Minute",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    lineHeight = 17.sp,
                    color = SecondaryText
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .clip(RoundedCornerShape(19.dp))
                        .background(brush = OrangeButtonGradient)
                        .clickable { onPlayNowClick() }
                        .testTag("play_now_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Play Now",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.5.sp,
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
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CpxResearchLogoIcon(size = 19.dp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = survey.providerName,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = PrimaryNavy
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = "Survey Duration",
                    tint = SecondaryText,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = survey.durationText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = SecondaryText
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(brush = GreenButtonGradient),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = survey.rewardText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.5.sp,
                    color = PureWhite
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .border(
                        width = 1.2.dp,
                        color = PrimaryNavy,
                        shape = RoundedCornerShape(17.dp)
                    )
                    .clickable { onStartSurvey() }
                    .testTag("start_survey_${survey.id}"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Start Survey",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
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
        modifier = modifier.clickable { onEarnClick() },
        cornerRadius = 16.dp,
        leftTricolorStrip = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = task.iconRes),
                contentDescription = task.title,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.5.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = task.description,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = SecondaryText
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .height(34.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .background(brush = GreenButtonGradient)
                    .clickable { onEarnClick() }
                    .padding(horizontal = 15.dp)
                    .testTag("task_earn_${task.id}"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = task.rewardText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.5.sp,
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
        cornerRadius = 16.dp,
        topTricolorStrip = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(vertical = 14.dp, horizontal = 10.dp),
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
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = SecondaryText
                )
                Spacer(modifier = Modifier.height(4.dp))
                val split = todaysCredited.split(" ", limit = 2)
                Text(
                    text = split.getOrElse(0) { todaysCredited },
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = SuccessGreen
                )
                if (split.size > 1) {
                    Text(
                        text = split[1],
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = SuccessGreen
                    )
                }
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
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
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = SecondaryText
                )
                Spacer(modifier = Modifier.height(4.dp))
                val split = pendingVerification.split(" ", limit = 2)
                Text(
                    text = split.getOrElse(0) { pendingVerification },
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PendingOrange
                )
                if (split.size > 1) {
                    Text(
                        text = split[1],
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = PendingOrange
                    )
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
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = task.iconRes),
                contentDescription = task.title,
                modifier = Modifier
                    .size(54.dp)
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
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.5.sp,
                    color = PrimaryNavy
                )
                if (task.progressFraction != null && task.progressLabel != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFFE2E8F0))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(task.progressFraction)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(SaffronOrange)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = task.progressLabel,
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = PrimaryNavy
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                } else {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = task.subtitle,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.5.sp,
                    color = SecondaryText
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(PendingCapsuleBg)
                    .border(1.dp, PendingCapsuleBorder, RoundedCornerShape(16.dp))
                    .padding(horizontal = 9.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = PendingCapsuleText,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = task.statusText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = PendingCapsuleText
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
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = task.iconRes),
                contentDescription = task.title,
                modifier = Modifier
                    .width(56.dp)
                    .height(50.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.5.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = task.description,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.5.sp,
                    color = SecondaryText
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(brush = GreenButtonGradient)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = PureWhite,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = task.paidBadgeText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.5.sp,
                    color = PureWhite
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
            .height(98.dp)
            .clickable { onSelect() }
            .testTag("withdrawal_method_${method.id}")
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(14.dp),
            color = PureWhite,
            border = BorderStroke(
                width = if (isSelected) 1.8.dp else 1.dp,
                color = if (isSelected) PrimaryGreen else CardBorderColor
            ),
            shadowElevation = 1.dp
        ) {
            when (method) {
                WithdrawalMethod.UPI -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_upi_logos),
                            contentDescription = "UPI",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            contentScale = ContentScale.Fit
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(PrimaryGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Instant UPI",
                                fontFamily = CashIndiaFontFamily,
                                fontWeight = FontWeight.SemiBold,
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
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_google_play),
                            contentDescription = "Google Play Code",
                            modifier = Modifier.size(38.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Google Play\nCode",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
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
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_amazon_pay),
                            contentDescription = "Amazon Pay",
                            modifier = Modifier
                                .width(60.dp)
                                .height(42.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Amazon Pay",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = PrimaryNavy,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 3.dp, y = (-4).dp)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen)
                    .border(1.5.dp, PureWhite, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Selected",
                    tint = PureWhite,
                    modifier = Modifier.size(12.dp)
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
                        .border(1.dp, CardBorderColor, RoundedCornerShape(19.dp))
                }
            )
            .clickable { onSelect() }
            .testTag("amount_btn_$amount"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "₹$amount",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.5.sp,
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
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (transaction.isUpiCheckIcon) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(PrimaryGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = PureWhite,
                        modifier = Modifier.size(19.dp)
                    )
                }
            } else if (transaction.iconRes != null) {
                Image(
                    painter = painterResource(id = transaction.iconRes),
                    contentDescription = transaction.title,
                    modifier = Modifier.size(32.dp),
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
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = transaction.subtitle,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.5.sp,
                    color = SecondaryText
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(SuccessCapsuleLightBg)
                    .padding(horizontal = 11.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = transaction.statusText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
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
        cornerRadius = 16.dp,
        topTricolorStrip = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(52.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val stroke = 2.dp.toPx()
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
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(PrimaryNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SG",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = PureWhite
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
                    .background(DividerColor)
            )

            Spacer(modifier = Modifier.width(12.dp))

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
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.5.sp,
                        color = PrimaryNavy
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShieldVerifiedIcon(size = 17.dp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Active",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.5.sp,
                            color = PrimaryGreen
                        )
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "UPI: sahid@paytm • Member since Oct 2026",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.5.sp,
                    color = SecondaryText
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
        0.0f to Color(0xFFFF881B),
        0.45f to Color(0xFFE07A10),
        1.0f to Color(0xFF138808)
    )
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(brush = bannerGradient)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_gift_box),
                contentDescription = "Referral Gift Box",
                modifier = Modifier
                    .width(86.dp)
                    .height(94.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
                    .background(Color(0x66FFFFFF))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Refer & Earn ₹10",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    color = PureWhite
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Earn from your referrals",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = PureWhite
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(PureWhite)
                        .clickable { onInviteClick() }
                        .testTag("whatsapp_invite_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        WhatsAppCircleIcon(size = 18.dp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Invite Now (WhatsApp)",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.5.sp,
                            color = Color(0xFFD96B00)
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
        modifier = modifier
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .testTag(testTag),
        cornerRadius = 15.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(30.dp),
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
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.5.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.5.sp,
                    color = SecondaryText
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
                        uncheckedTrackColor = Color(0xFFCBD5E1)
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
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(PrimaryGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = PrimaryNavy,
                        modifier = Modifier.size(22.dp)
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
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(30.dp),
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
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.5.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.5.sp,
                    color = SecondaryText
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
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
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = buttonText,
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.SemiBold,
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
                        .border(1.2.dp, PrimaryNavy, RoundedCornerShape(17.dp))
                        .clickable { onButtonClick() }
                        .padding(horizontal = 15.dp)
                        .testTag(testTag),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonText,
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.5.sp,
                        color = PrimaryNavy
                    )
                }
            }
        }
    }
}
