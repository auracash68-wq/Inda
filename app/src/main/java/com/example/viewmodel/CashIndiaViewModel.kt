package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
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
import org.json.JSONArray
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CashIndiaUiState(
    val selectedTab: CashIndiaTab = CashIndiaTab.EARN,
    val earnHeaderCoins: Int = 0,
    val earnHeaderBalanceText: String = "0 Coins | ₹0.00",
    val walletBalanceRupees: Double = 0.00,
    val walletBalanceCoins: Int = 0,
    val selectedTaskFilter: TaskFilter = TaskFilter.ALL_TASKS,
    val surveys: List<SurveyOffer> = emptyList(),
    val allAppTasks: List<AppTaskItem> = emptyList(),
    val selectedMyTasksSegment: MyTasksSegment = MyTasksSegment.ONGOING_PENDING,
    val todaysCreditedText: String = "₹0.00 (0 Coins)",
    val pendingVerificationText: String = "₹0.00 (0 Tasks)",
    val ongoingTasks: List<OngoingTaskItem> = emptyList(),
    val completedTasks: List<CompletedTaskItem> = emptyList(),
    val selectedWithdrawalMethod: WithdrawalMethod = WithdrawalMethod.UPI,
    val upiIdInput: String = "",
    val isUpiVerified: Boolean = true,
    val withdrawalAmounts: List<Int> = listOf(10, 20, 50, 100),
    val selectedWithdrawalAmount: Int = 10,
    val recentWithdrawals: List<WithdrawalTransaction> = emptyList(),
    val pushNotificationsEnabled: Boolean = true,
    val selectedLanguage: String = "English",
    val activeSheet: ActiveSheet? = null,
    val statusMessage: String? = null
)

class CashIndiaViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs: android.content.SharedPreferences? = try {
        application.getSharedPreferences("cash_india_user_data", Context.MODE_PRIVATE)
    } catch (_: Exception) {
        null
    }

    constructor() : this(Application())

    private val _uiState = MutableStateFlow(loadInitialState())
    val uiState: StateFlow<CashIndiaUiState> = _uiState.asStateFlow()

    private fun loadInitialState(): CashIndiaUiState {
        // Absolute zero state for clean integration from scratch
        return CashIndiaUiState(
            earnHeaderCoins = 0,
            earnHeaderBalanceText = "0 Coins | ₹0.00",
            walletBalanceCoins = 0,
            walletBalanceRupees = 0.00,
            todaysCreditedText = "₹0.00 (0 Coins)",
            pendingVerificationText = "₹0.00 (0 Tasks)",
            ongoingTasks = emptyList(),
            completedTasks = emptyList(),
            recentWithdrawals = emptyList(),
            surveys = emptyList(),
            allAppTasks = emptyList()
        )
    }

    private fun saveBalanceToPrefs(coins: Int) {
        prefs?.edit()?.putInt("real_wallet_coins", coins)?.apply()
    }

    private fun saveCompletedTasksToPrefs(tasks: List<CompletedTaskItem>) {
        val encoded = tasks.joinToString("\n") { item ->
            listOf(item.id, item.title, item.description, item.paidBadgeText)
                .joinToString("\t") { it.replace("\t", " ").replace("\n", " ") }
        }
        prefs?.edit()?.putString("saved_completed_tasks", encoded)?.apply()
    }

    private fun loadCompletedTasksFromPrefs(): List<CompletedTaskItem> {
        val raw = prefs?.getString("saved_completed_tasks", null) ?: return emptyList()
        return try {
            raw.split("\n").mapNotNull { line ->
                val parts = line.split("\t")
                if (parts.size >= 4) {
                    CompletedTaskItem(
                        id = parts[0],
                        title = parts[1],
                        description = parts[2],
                        paidBadgeText = parts[3],
                        iconRes = R.drawable.img_cpx_survey_completed
                    )
                } else null
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun saveWithdrawalsToPrefs(list: List<WithdrawalTransaction>) {
        val encoded = list.joinToString("\n") { tx ->
            listOf(tx.id, tx.title, tx.subtitle, tx.statusText, tx.isUpiCheckIcon.toString())
                .joinToString("\t") { it.replace("\t", " ").replace("\n", " ") }
        }
        prefs?.edit()?.putString("saved_withdrawals", encoded)?.apply()
    }

    private fun loadWithdrawalsFromPrefs(): List<WithdrawalTransaction> {
        val raw = prefs?.getString("saved_withdrawals", null) ?: return emptyList()
        return try {
            raw.split("\n").mapNotNull { line ->
                val parts = line.split("\t")
                if (parts.size >= 5) {
                    val isUpi = parts[4].toBoolean()
                    WithdrawalTransaction(
                        id = parts[0],
                        title = parts[1],
                        subtitle = parts[2],
                        statusText = parts[3],
                        isUpiCheckIcon = isUpi,
                        iconRes = if (!isUpi) R.drawable.img_google_play else null
                    )
                } else null
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

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

    /**
     * Real reward credit callback triggered when real SDK rewards complete.
     */
    fun creditRealCoins(coins: Int, taskTitle: String = "Offerwall Task") {
        if (coins <= 0) return
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        val timeStr = SimpleDateFormat("h:mm a", Locale.US).format(Date())

        val newCompletedTask = CompletedTaskItem(
            id = "task_${System.currentTimeMillis()}",
            title = taskTitle,
            description = "Verified & added to wallet • Today, $timeStr",
            paidBadgeText = "+$coins Coins",
            iconRes = R.drawable.img_cpx_survey_completed
        )

        _uiState.update { state ->
            val newHeaderCoins = state.earnHeaderCoins + coins
            val newHeaderRupees = newHeaderCoins / 100.0
            val newHeaderText =
                "${numberFormat.format(newHeaderCoins)} Coins | ₹${String.format(Locale.US, "%.2f", newHeaderRupees)}"

            val newWalletCoins = state.walletBalanceCoins + coins
            val newWalletRupees = state.walletBalanceRupees + (coins / 100.0)
            val newTodaysCreditedText =
                "₹${String.format(Locale.US, "%.2f", newWalletRupees)} (${numberFormat.format(newWalletCoins)} Coins)"

            val updatedCompletedTasks = listOf(newCompletedTask) + state.completedTasks

            saveBalanceToPrefs(newWalletCoins)
            saveCompletedTasksToPrefs(updatedCompletedTasks)

            state.copy(
                earnHeaderCoins = newHeaderCoins,
                earnHeaderBalanceText = newHeaderText,
                walletBalanceCoins = newWalletCoins,
                walletBalanceRupees = newWalletRupees,
                todaysCreditedText = newTodaysCreditedText,
                completedTasks = updatedCompletedTasks,
                statusMessage = "Earned $coins Coins ($taskTitle)!"
            )
        }
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
                    statusMessage = "Insufficient balance (₹${String.format(Locale.US, "%.2f", state.walletBalanceRupees)} available). Complete tasks to withdraw ₹$amount."
                )
            }
            return
        }

        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        val coinsToDeduct = amount * 100
        val remainingWalletRupees = (state.walletBalanceRupees - amount).coerceAtLeast(0.0)
        val remainingWalletCoins = (state.walletBalanceCoins - coinsToDeduct).coerceAtLeast(0)
        val remainingHeaderCoins = (state.earnHeaderCoins - coinsToDeduct).coerceAtLeast(0)
        val remainingHeaderRupees = remainingHeaderCoins / 100.0
        val newHeaderText =
            "${numberFormat.format(remainingHeaderCoins)} Coins | ₹${String.format(Locale.US, "%.2f", remainingHeaderRupees)}"

        val isUpi = state.selectedWithdrawalMethod == WithdrawalMethod.UPI
        val titleText = when (state.selectedWithdrawalMethod) {
            WithdrawalMethod.UPI -> "UPI Payout • ₹${String.format(Locale.US, "%.2f", amount.toDouble())}"
            WithdrawalMethod.GOOGLE_PLAY -> "Play Recharge Code • ₹${String.format(Locale.US, "%.2f", amount.toDouble())}"
            WithdrawalMethod.AMAZON_PAY -> "Amazon Pay Gift Card • ₹${String.format(Locale.US, "%.2f", amount.toDouble())}"
        }
        val subtitleText = when (state.selectedWithdrawalMethod) {
            WithdrawalMethod.UPI -> "To: $effectiveUpi • Just now"
            WithdrawalMethod.GOOGLE_PLAY -> "Code Generated • Just now"
            WithdrawalMethod.AMAZON_PAY -> "Voucher Sent • Just now"
        }
        val iconRes = when (state.selectedWithdrawalMethod) {
            WithdrawalMethod.UPI -> null
            WithdrawalMethod.GOOGLE_PLAY -> R.drawable.img_google_play
            WithdrawalMethod.AMAZON_PAY -> R.drawable.img_amazon_pay
        }

        val newTransaction = WithdrawalTransaction(
            id = "tx_${System.currentTimeMillis()}",
            title = titleText,
            subtitle = subtitleText,
            statusText = "Success",
            isUpiCheckIcon = isUpi,
            iconRes = iconRes
        )

        val updatedWithdrawals = listOf(newTransaction) + state.recentWithdrawals
        saveBalanceToPrefs(remainingWalletCoins)
        saveWithdrawalsToPrefs(updatedWithdrawals)

        _uiState.update {
            it.copy(
                earnHeaderCoins = remainingHeaderCoins,
                earnHeaderBalanceText = newHeaderText,
                walletBalanceRupees = remainingWalletRupees,
                walletBalanceCoins = remainingWalletCoins,
                recentWithdrawals = updatedWithdrawals,
                statusMessage = "Withdrawal request for ₹$amount via ${state.selectedWithdrawalMethod.title.replace("\n", " ")} ($effectiveUpi) submitted successfully."
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

    fun setDynamicSurveys(surveys: List<SurveyOffer>) {
        _uiState.update { it.copy(surveys = surveys) }
    }

    fun setDynamicAppTasks(tasks: List<AppTaskItem>) {
        _uiState.update { it.copy(allAppTasks = tasks) }
    }

    fun clearStatusMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }
}
