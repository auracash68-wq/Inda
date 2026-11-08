package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CashIndiaTab
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorderColor
import com.example.ui.theme.CashIndiaFontFamily
import com.example.ui.theme.DividerColor
import com.example.ui.theme.GreenButtonGradient
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SecondaryText
import com.example.ui.theme.TricolorTopBarBrush

@Composable
fun CoinBalanceHeader(
    isEarnHomeHeader: Boolean,
    title: String,
    subtitle: String = "",
    badgeText: String,
    isSecurityBadge: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEarnHomeHeader) {
            Image(
                painter = painterResource(id = R.drawable.cash_india_app_icon),
                contentDescription = "Cash India",
                modifier = Modifier
                    .size(72.dp)
                    .testTag("earn_header_app_icon"),
                contentScale = ContentScale.Fit
            )
        } else {
            Column(
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Text(
                    text = title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    color = PrimaryNavy
                )
                if (subtitle.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        lineHeight = 17.sp,
                        color = SecondaryText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Right: Clean rounded balance/security capsule
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(22.dp))
                .background(brush = GreenButtonGradient)
                .padding(horizontal = 13.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (isSecurityBadge) {
                    ShieldVerifiedIcon(
                        size = 18.dp,
                        shieldColor = PureWhite,
                        checkColor = PureWhite,
                        outlinedShield = true
                    )
                } else {
                    GoldCoinStackIcon(size = 20.dp)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = badgeText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = PureWhite,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun CashIndiaCardSurface(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    borderColor: Color = CardBorderColor,
    borderWidth: Dp = 1.dp,
    leftTricolorStrip: Boolean = false,
    leftSolidStripColor: Color? = null,
    topTricolorStrip: Boolean = false,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cornerRadius),
        color = CardBackground,
        border = BorderStroke(borderWidth, borderColor),
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (topTricolorStrip) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.5.dp)
                        .background(brush = TricolorTopBarBrush)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                if (leftTricolorStrip) {
                    Column(
                        modifier = Modifier
                            .width(4.dp)
                            .fillMaxHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(SaffronOrange)
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.3f)
                                .fillMaxWidth()
                                .background(Color(0xFFF8FAFC))
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(PrimaryGreen)
                        )
                    }
                } else if (leftSolidStripColor != null) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .fillMaxHeight()
                            .background(leftSolidStripColor)
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    content()
                }
            }
        }
    }
}

@Composable
fun CashIndiaBottomNavigation(
    selectedTab: CashIndiaTab,
    onSelectTab: (CashIndiaTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = PureWhite,
        border = BorderStroke(1.dp, CardBorderColor),
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .height(64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = CashIndiaTab.entries
            tabs.forEachIndexed { index, tab ->
                val isSelected = tab == selectedTab
                val itemColor = if (isSelected) SaffronOrange else PrimaryNavy

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onSelectTab(tab) }
                        .testTag("bottom_nav_${tab.route}"),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (tab) {
                        CashIndiaTab.EARN -> BottomNavEarnIcon(color = itemColor, size = 23.dp)
                        CashIndiaTab.MY_TASKS -> BottomNavTasksIcon(color = itemColor, size = 23.dp)
                        CashIndiaTab.WALLET -> BottomNavWalletIcon(color = itemColor, size = 23.dp)
                        CashIndiaTab.PROFILE -> BottomNavProfileIcon(color = itemColor, size = 23.dp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tab.label,
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = itemColor,
                        maxLines = 1
                    )
                }

                if (index < tabs.lastIndex) {
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(32.dp)
                            .background(DividerColor)
                    )
                }
            }
        }
    }
}
