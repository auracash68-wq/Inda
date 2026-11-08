package com.example.ui.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.WithdrawalMethod
import com.example.ui.components.AmountSelectionButton
import com.example.ui.components.CashIndiaCardSurface
import com.example.ui.components.CoinBalanceHeader
import com.example.ui.components.GoldCoinStackIcon
import com.example.ui.components.ShieldVerifiedIcon
import com.example.ui.components.TransactionCard
import com.example.ui.components.WithdrawalMethodCard
import com.example.ui.theme.CardBorderColor
import com.example.ui.theme.CashIndiaFontFamily
import com.example.ui.theme.DividerColor
import com.example.ui.theme.GoldCapsuleEnd
import com.example.ui.theme.GoldCapsuleStart
import com.example.ui.theme.GreenButtonGradient
import com.example.ui.theme.InfoBannerBlueBg
import com.example.ui.theme.MainScreenBackground
import com.example.ui.theme.MutedText
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SecondaryText
import com.example.viewmodel.CashIndiaUiState

@Composable
fun MyWalletScreen(
    uiState: CashIndiaUiState,
    onSelectMethod: (WithdrawalMethod) -> Unit,
    onUpiIdChange: (String) -> Unit,
    onSelectAmount: (Int) -> Unit,
    onWithdrawClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MainScreenBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("my_wallet_screen")
    ) {
        // A. Header
        CoinBalanceHeader(
            isEarnHomeHeader = false,
            title = "My Wallet",
            subtitle = "Fast & Easy Withdrawals",
            badgeText = "100% Secure",
            isSecurityBadge = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        // B. Available Balance Card
        CashIndiaCardSurface(
            cornerRadius = 16.dp,
            topTricolorStrip = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Available Balance",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.5.sp,
                    color = SecondaryText
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "₹${String.format("%.2f", uiState.walletBalanceRupees)}",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        lineHeight = 32.sp,
                        color = PrimaryGreen
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .padding(vertical = 3.dp)
                            .background(DividerColor)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(GoldCapsuleStart, GoldCapsuleEnd)
                                )
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GoldCoinStackIcon(size = 20.dp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(uiState.walletBalanceCoins)} Coins",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = PrimaryNavy
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(DividerColor)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "100 Coins = ₹1",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = SecondaryText
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // C. Withdrawal Method
        Text(
            text = "Withdrawal Method",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            WithdrawalMethod.entries.forEach { method ->
                WithdrawalMethodCard(
                    method = method,
                    isSelected = uiState.selectedWithdrawalMethod == method,
                    onSelect = { onSelectMethod(method) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // D. Enter UPI ID
        Text(
            text = "Enter UPI ID",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(PureWhite)
                .border(1.dp, CardBorderColor, RoundedCornerShape(22.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (uiState.upiIdInput.isEmpty()) {
                        Text(
                            text = "example@okaxis",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.5.sp,
                            color = MutedText
                        )
                    }
                    BasicTextField(
                        value = uiState.upiIdInput,
                        onValueChange = onUpiIdChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.5.sp,
                            color = PrimaryNavy
                        ),
                        cursorBrush = SolidColor(PrimaryNavy),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("upi_id_input")
                    )
                }

                if (uiState.isUpiVerified) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShieldVerifiedIcon(size = 17.dp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Verified",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.5.sp,
                            color = PrimaryGreen
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // E. Withdrawal Amount Selection (₹10 | ₹20 | ₹50 | ₹100)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.withdrawalAmounts.forEach { amount ->
                AmountSelectionButton(
                    amount = amount,
                    isSelected = uiState.selectedWithdrawalAmount == amount,
                    onSelect = { onSelectAmount(amount) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // F. Main Withdrawal Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .clip(RoundedCornerShape(23.dp))
                .background(brush = GreenButtonGradient)
                .clickable { onWithdrawClick() }
                .testTag("withdraw_submit_button"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Withdraw ₹${uiState.selectedWithdrawalAmount}",
                fontFamily = CashIndiaFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.5.sp,
                color = PureWhite
            )
        }

        if (uiState.statusMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(InfoBannerBlueBg)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = uiState.statusMessage,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.5.sp,
                    color = PrimaryNavy
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // G. Recent Withdrawals
        Text(
            text = "Recent Withdrawals",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (uiState.recentWithdrawals.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No withdrawal requests yet. Complete tasks to request your first payout!",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.5.sp,
                    color = SecondaryText,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                uiState.recentWithdrawals.forEach { tx ->
                    TransactionCard(transaction = tx)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
