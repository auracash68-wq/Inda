package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.R
import com.example.model.ActiveSheet
import com.example.model.AppTaskItem
import com.example.model.CashIndiaTab
import com.example.model.CompletedTaskItem
import com.example.model.MyTasksSegment
import com.example.model.OngoingTaskItem
import com.example.model.SurveyOffer
import com.example.model.TaskFilter
import com.example.model.WithdrawalMethod
import com.example.model.WithdrawalTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CashIndiaUiState(
    val selectedTab: CashIndiaTab = CashIndiaTab.EARN,
    val earnHeaderBalanceText: String = "2,500 Coins | ₹25.00",
    val walletBalanceRupees: Double = 45.00,
    val walletBalanceCoins: Int = 4500,
    val selectedTaskFilter: TaskFilter = TaskFilter.ALL_TASKS,
    val surveys: List<SurveyOffer> = listOf(
        SurveyOffer(
            id = "cpx_5min",
            providerName = "CPX Research",
            durationText = "5 Mins",
            rewardText = "₹15 (1,500 Coins)",
            rewardRupees = 15,
            rewardCoins = 1500
        ),
        SurveyOffer(
            id = "cpx_8min",
            providerName = "CPX Research",
            durationText = "8 Mins",
            rewardText = "₹25 (2,500 Coins)",
            rewardRupees = 25,
            rewardCoins = 2500
        )
    ),
    val allAppTasks: List<AppTaskItem> = listOf(
        AppTaskItem(
            id = "ludo_supreme",
            title = "Ludo Supreme",
            description = "Install & Play",
            rewardText = "Earn ₹10",
            rewardRupees = 10,
            iconRes = R.drawable.img_ludo_icon,
            isEasyInstall = true
        ),
        AppTaskItem(
            id = "paytm_money",
            title = "Paytm Money",
            description = "Install & sign up free",
            rewardText = "Earn ₹20",
            rewardRupees = 20,
            iconRes = R.drawable.img_paytm_icon,
            isEasyInstall = true
        ),
        AppTaskItem(
            id = "flipkart_shop",
            title = "Flipkart Shop",
            description = "Explore Deals",
            rewardText = "Earn ₹15",
            rewardRupees = 15,
            iconRes = R.drawable.img_flipkart_icon,
            isEasyInstall = false
        )
    ),
    val selectedMyTasksSegment: MyTasksSegment = MyTasksSegment.ONGOING_PENDING,
    val todaysCreditedText: String = "₹45.00 (4,500 Coins)",
    val pendingVerificationText: String = "₹30.00 (2 Tasks)",
    val ongoingTasks: List<OngoingTaskItem> = listOf(
        OngoingTaskItem(
            id = "royal_match",
            title = "Royal Match Puzzle",
            subtitle = "12 / 15 Mins Played",
            statusText = "In Progress • ₹10",
            progressFraction = 0.80f,
            progressLabel = "80%",
            iconRes = R.drawable.img_royal_match
        ),
        OngoingTaskItem(
            id = "paytm_signup",
            title = "Paytm Money Sign-Up",
            subtitle = "Verification Pending",
            statusText = "Verifying • ₹20",
            progressFraction = null,
            progressLabel = null,
            iconRes = R.drawable.img_paytm_pubscale
        )
    ),
    val completedTasks: List<CompletedTaskItem> = listOf(
        CompletedTaskItem(
            id = "cpx_daily_survey",
            title = "5-Min Daily Survey",
            description = "Added to wallet • Today, 2:30 PM",
            paidBadgeText = "Paid +₹15",
            iconRes = R.drawable.img_cpx_survey_completed
        ),
        CompletedTaskItem(
            id = "ludo_install",
            title = "Ludo Supreme Install",
            description = "App opened for 2 mins • Today, 1:15 PM",
            paidBadgeText = "Paid +₹10",
            iconRes = R.drawable.img_ludo_torox
        )
    ),
    val selectedWithdrawalMethod: WithdrawalMethod = WithdrawalMethod.UPI,
    val upiIdInput: String = "",
    val isUpiVerified: Boolean = true,
    val withdrawalAmounts: List<Int> = listOf(10, 20, 50, 100),
    val selectedWithdrawalAmount: Int = 10,
    val recentWithdrawals: List<WithdrawalTransaction> = listOf(
        WithdrawalTransaction(
            id = "tx_upi_20",
            title = "UPI Payout • ₹20.00",
            subtitle = "UTR: 315812345678 • Today, 11:20 AM",
            statusText = "Success",
            isUpiCheckIcon = true
        ),
        WithdrawalTransaction(
            id = "tx_gplay_10",
            title = "Play Recharge Code • ₹10.00",
            subtitle = "Code Delivered • Yesterday",
            statusText = "Success",
            isUpiCheckIcon = false,
            iconRes = R.drawable.img_google_play
        )
    ),
    val pushNotificationsEnabled: Boolean = true,
    val selectedLanguage: String = "English",
    val activeSheet: ActiveSheet? = null,
    val statusMessage: String? = null
)

class CashIndiaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CashIndiaUiState())
    val uiState: StateFlow<CashIndiaUiState> = _uiState.asStateFlow()

    fun selectTab(tab: CashIndiaTab) {
        if (_uiState.value.selectedTab != tab) {
            _uiState.update { it.copy(selectedTab = tab, statusMessage = null) }
        }
    }

    fun selectTaskFilter(filter: TaskFilter) {
        _uiState.update { it.copy(selectedTaskFilter = filter) }
    }

    fun selectMyTasksSegment(segment: MyTasksSegment) {
        _uiState.update { it.copy(selectedMyTasksSegment = segment) }
    }

    fun selectWithdrawalMethod(method: WithdrawalMethod) {
        _uiState.update { it.copy(selectedWithdrawalMethod = method, statusMessage = null) }
    }

    fun updateUpiId(input: String) {
        val trimmed = input.trim()
        val verified = trimmed.isEmpty() || (trimmed.contains("@") && trimmed.length >= 5)
        _uiState.update {
            it.copy(
                upiIdInput = input,
                isUpiVerified = verified,
                statusMessage = null
            )
        }
    }

    fun selectWithdrawalAmount(amount: Int) {
        _uiState.update { it.copy(selectedWithdrawalAmount = amount, statusMessage = null) }
    }

    fun submitWithdrawal() {
        val state = _uiState.value
        val amount = state.selectedWithdrawalAmount
        val effectiveUpi = state.upiIdInput.ifBlank { "example@okaxis" }
        if (state.selectedWithdrawalMethod == WithdrawalMethod.UPI && !effectiveUpi.contains("@")) {
            _uiState.update {
                it.copy(statusMessage = "Please enter a valid UPI ID (e.g., example@okaxis).")
            }
            return
        }
        if (amount > state.walletBalanceRupees) {
            _uiState.update {
                it.copy(
                    statusMessage = "Insufficient balance (₹${String.format("%.2f", state.walletBalanceRupees)} available). Complete more tasks to withdraw ₹$amount."
                )
            }
            return
        }
        _uiState.update {
            it.copy(
                statusMessage = "Withdrawal request for ₹$amount via ${state.selectedWithdrawalMethod.title.replace("\n", " ")} ($effectiveUpi) submitted for bank verification."
            )
        }
    }

    fun togglePushNotifications(enabled: Boolean) {
        _uiState.update { it.copy(pushNotificationsEnabled = enabled) }
    }

    fun selectLanguage(language: String) {
        _uiState.update {
            it.copy(
                selectedLanguage = language,
                activeSheet = null,
                statusMessage = "App language set to $language"
            )
        }
    }

    fun openSheet(sheet: ActiveSheet) {
        _uiState.update { it.copy(activeSheet = sheet) }
    }

    fun dismissSheet() {
        _uiState.update { it.copy(activeSheet = null) }
    }

    fun submitSupportTicket(issueText: String) {
        _uiState.update {
            it.copy(
                activeSheet = null,
                statusMessage = "Support ticket submitted for provider verification."
            )
        }
    }

    fun clearStatusMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }
}
