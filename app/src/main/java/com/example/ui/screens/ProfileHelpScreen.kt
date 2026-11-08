package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActiveSheet
import com.example.ui.components.BlueGlobeIcon
import com.example.ui.components.CoinBalanceHeader
import com.example.ui.components.NavyHeadsetIcon
import com.example.ui.components.NavyTicketIcon
import com.example.ui.components.ProfileHeaderCard
import com.example.ui.components.ReferralBanner
import com.example.ui.components.SettingsItemCard
import com.example.ui.components.ShieldVerifiedIcon
import com.example.ui.components.SupportCard
import com.example.ui.theme.CashIndiaFontFamily
import com.example.ui.theme.MainScreenBackground
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SecondaryText
import com.example.viewmodel.CashIndiaUiState

@Composable
fun ProfileHelpScreen(
    uiState: CashIndiaUiState,
    onTogglePushNotifications: (Boolean) -> Unit,
    onOpenSheet: (ActiveSheet) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MainScreenBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("profile_help_screen")
    ) {
        // A. Header
        CoinBalanceHeader(
            isEarnHomeHeader = false,
            title = "My Profile & Help",
            subtitle = "Account Settings",
            badgeText = "₹${String.format("%.2f", uiState.walletBalanceRupees)}"
        )

        Spacer(modifier = Modifier.height(10.dp))

        // B. User Profile Card
        ProfileHeaderCard()

        Spacer(modifier = Modifier.height(14.dp))

        // C. Referral Banner
        ReferralBanner(
            onInviteClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Join me on Cash India and earn ₹10 + instant UPI rewards! Referral ID: SAHID10"
                    )
                }
                context.startActivity(Intent.createChooser(shareIntent, "Invite via WhatsApp"))
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // D. App Settings
        Text(
            text = "App Settings",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Setting 1 — App Language
            SettingsItemCard(
                title = "App Language",
                subtitle = "English • বাংলা • हिंदी",
                leadingIcon = { BlueGlobeIcon(size = 24.dp) },
                onClick = { onOpenSheet(ActiveSheet.LanguagePicker) },
                testTag = "setting_app_language"
            )

            // Setting 2 — Security & VPN
            SettingsItemCard(
                title = "Security & VPN",
                subtitle = "Secure reward verification",
                leadingIcon = { ShieldVerifiedIcon(size = 25.dp) },
                showGreenStatusDot = true,
                onClick = { onOpenSheet(ActiveSheet.SecurityVpnPolicy) },
                testTag = "setting_security_vpn"
            )

            // Setting 3 — Push Notifications
            SettingsItemCard(
                title = "Push Notifications",
                subtitle = "Get new task alerts",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = null,
                        tint = PrimaryNavy,
                        modifier = Modifier.size(25.dp)
                    )
                },
                isToggleSetting = true,
                toggleChecked = uiState.pushNotificationsEnabled,
                onToggleChange = onTogglePushNotifications,
                testTag = "setting_push_notifications"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // E. Need Help Section
        Text(
            text = "Need Help?",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.5.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Support Card 1 — Missing Reward Ticket
            SupportCard(
                title = "Missing Reward Ticket",
                description = "Report missing rewards",
                leadingIcon = { NavyTicketIcon(size = 26.dp) },
                buttonText = "Submit",
                isGreenChatButton = false,
                onButtonClick = { onOpenSheet(ActiveSheet.MissingRewardTicket) },
                testTag = "support_submit_ticket"
            )

            // Support Card 2 — 24/7 Chat Support
            SupportCard(
                title = "24/7 Chat Support",
                description = "Get help with withdrawals",
                leadingIcon = { NavyHeadsetIcon(size = 24.dp) },
                buttonText = "Chat",
                isGreenChatButton = true,
                onButtonClick = { onOpenSheet(ActiveSheet.ChatSupport) },
                testTag = "support_chat_button"
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // F. Footer
        Text(
            text = "Cash India v1.0.0 • Terms of Service & Privacy Policy",
            fontFamily = CashIndiaFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = SecondaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenSheet(ActiveSheet.TermsAndPrivacy) }
                .padding(vertical = 4.dp)
                .testTag("footer_terms_privacy")
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}
