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
import androidx.compose.ui.draw.shadow
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
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("my_wallet_screen")
    ) {
        // A. Header
        CoinBalanceHeader(
            isEarnHomeHeader = false,
            title = "My Wallet",
            subtitle = "24/7 Instant UPI Cashout",
            badgeText = "100% Secure",
            isSecurityBadge = true
        )

        Spacer(modifier = Modifier.height(6.dp))

        // B. Available Balance Card
        CashIndiaCardSurface(
            cornerRadius = 18.dp,
            topTricolorStrip = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Available Balance",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.5.sp,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "₹${String.format("%.2f", uiState.walletBalanceRupees)}",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 33.sp,
                        lineHeight = 36.sp,
                        color = PrimaryGreen
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .padding(vertical = 3.dp)
                            .background(DividerColor)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    // Yellow/gold rounded coin capsule
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(GoldCapsuleStart, GoldCapsuleEnd)
                                )
                            )
                            .padding(horizontal = 13.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GoldCoinStackIcon(size = 24.dp)
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text = "4,500 Coins",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = PrimaryNavy
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(DividerColor)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "100 Coins = ₹1.00 • Instant Transfer",
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = PrimaryNavy
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // C. Select Withdrawal Method
        Text(
            text = "Select Withdrawal Method",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 17.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(9.dp))

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

        Spacer(modifier = Modifier.height(12.dp))

        // D. Enter UPI ID
        Text(
            text = "Enter UPI ID",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Wide rounded input field with placeholder example@okaxis and right-side Verified indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .shadow(1.5.dp, RoundedCornerShape(22.dp))
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
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            color = MutedText
                        )
                    }
                    BasicTextField(
                        value = uiState.upiIdInput,
                        onValueChange = onUpiIdChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
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
                        ShieldVerifiedIcon(size = 18.dp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Verified",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.5.sp,
                            color = PrimaryGreen
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

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

        Spacer(modifier = Modifier.height(11.dp))

        // F. Main Withdrawal Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .shadow(3.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(brush = GreenButtonGradient)
                .clickable { onWithdrawClick() }
                .testTag("withdraw_submit_button"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Withdraw ₹${uiState.selectedWithdrawalAmount} Instantly",
                fontFamily = CashIndiaFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = PureWhite
            )
        }

        if (uiState.statusMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(InfoBannerBlueBg)
                    .padding(horizontal = 12.dp, vertical = 7.dp)
            ) {
                Text(
                    text = uiState.statusMessage,
                    fontFamily = CashIndiaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = PrimaryNavy
                )
            }
        }

        Spacer(modifier = Modifier.height(13.dp))

        // G. Recent Withdrawals (Bank Proof)
        Text(
            text = "Recent Withdrawals (Bank Proof)",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 17.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(9.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            uiState.recentWithdrawals.forEach { tx ->
                TransactionCard(transaction = tx)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}
