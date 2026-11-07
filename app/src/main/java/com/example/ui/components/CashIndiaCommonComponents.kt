package com.example.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .padding(top = 6.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEarnHomeHeader) {
            // Left: Ashoka Chakra + stacked Cash / India
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AshokaChakraIcon(size = 48.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Cash",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp,
                        lineHeight = 26.sp,
                        color = PrimaryNavy
                    )
                    Text(
                        text = "India",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp,
                        lineHeight = 26.sp,
                        color = SaffronOrange,
                        modifier = Modifier.offset(y = (-3).dp)
                    )
                }
            }
        } else {
            // Left: Screen title + subtitle (My Tasks, My Wallet, My Profile & Help)
            Column(
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Text(
                    text = title,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    lineHeight = 30.sp,
                    color = PrimaryNavy
                )
                if (subtitle.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = subtitle,
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp,
                        lineHeight = 17.sp,
                        color = SecondaryText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Right: Green rounded capsule
        Box(
            modifier = Modifier
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
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
                        size = 20.dp,
                        shieldColor = PureWhite,
                        checkColor = PureWhite,
                        outlinedShield = true
                    )
                } else {
                    GoldCoinStackIcon(size = 22.dp)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = badgeText,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp,
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
    cornerRadius: Dp = 18.dp,
    borderColor: Color = CardBorderColor,
    borderWidth: Dp = 1.dp,
    leftTricolorStrip: Boolean = false,
    leftSolidStripColor: Color? = null,
    topTricolorStrip: Boolean = false,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color(0x2207145F),
                spotColor = Color(0x2607145F)
            ),
        shape = RoundedCornerShape(cornerRadius),
        color = CardBackground,
        border = BorderStroke(borderWidth, borderColor)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (topTricolorStrip) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
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
                            .width(5.dp)
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
                                .weight(0.35f)
                                .fillMaxWidth()
                                .background(Color(0xFFF3F6FA))
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
                            .width(5.dp)
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
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                ambientColor = Color(0x3307145F),
                spotColor = Color(0x3307145F)
            ),
        color = PureWhite,
        border = BorderStroke(1.dp, CardBorderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .height(68.dp),
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
                        CashIndiaTab.EARN -> BottomNavEarnIcon(color = itemColor, size = 25.dp)
                        CashIndiaTab.MY_TASKS -> BottomNavTasksIcon(color = itemColor, size = 25.dp)
                        CashIndiaTab.WALLET -> BottomNavWalletIcon(color = itemColor, size = 25.dp)
                        CashIndiaTab.PROFILE -> BottomNavProfileIcon(color = itemColor, size = 25.dp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tab.label,
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                        color = itemColor,
                        maxLines = 1
                    )
                }

                // Subtle vertical separator between navigation items
                if (index < tabs.lastIndex) {
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(38.dp)
                            .background(DividerColor)
                    )
                }
            }
        }
    }
}
