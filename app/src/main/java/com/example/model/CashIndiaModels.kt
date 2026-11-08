package com.example.model

import androidx.annotation.DrawableRes
import com.example.R

enum class CashIndiaTab(val route: String, val label: String) {
    EARN("earn", "Earn"),
    MY_TASKS("my_tasks", "My Tasks"),
    WALLET("wallet", "Wallet"),
    PROFILE("profile", "Profile")
}

enum class TaskFilter {
    ALL_TASKS,
    EASY_INSTALL
}

enum class MyTasksSegment {
    ONGOING_PENDING,
    COMPLETED_PAID
}

enum class WithdrawalMethod(val id: String, val title: String) {
    UPI("upi", "Instant UPI"),
    GOOGLE_PLAY("google_play", "Google Play\nCode"),
    AMAZON_PAY("amazon_pay", "Amazon Pay")
}

data class SurveyOffer(
    val id: String,
    val providerName: String,
    val durationText: String,
    val rewardText: String,
    val rewardRupees: Int,
    val rewardCoins: Int
)

data class AppTaskItem(
    val id: String,
    val title: String,
    val description: String,
    val rewardText: String,
    val rewardRupees: Int,
    @param:DrawableRes val iconRes: Int,
    val isEasyInstall: Boolean
)

data class OngoingTaskItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val statusText: String,
    val progressFraction: Float?, // null if verifying without progress bar
    val progressLabel: String?,
    @param:DrawableRes val iconRes: Int
)

data class CompletedTaskItem(
    val id: String,
    val title: String,
    val description: String,
    val paidBadgeText: String,
    @param:DrawableRes val iconRes: Int
)

data class WithdrawalTransaction(
    val id: String,
    val title: String,
    val subtitle: String,
    val statusText: String,
    val isUpiCheckIcon: Boolean,
    @param:DrawableRes val iconRes: Int? = null
)

sealed class ActiveSheet {
    data class OfferLaunch(
        val title: String,
        val provider: String,
        val rewardText: String,
        val instructions: String
    ) : ActiveSheet()

    data object LanguagePicker : ActiveSheet()
    data object SecurityVpnPolicy : ActiveSheet()
    data object MissingRewardTicket : ActiveSheet()
    data object ChatSupport : ActiveSheet()
    data object TermsAndPrivacy : ActiveSheet()
}
