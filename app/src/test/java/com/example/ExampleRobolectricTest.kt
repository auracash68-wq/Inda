package com.example

import com.example.model.CashIndiaTab
import com.example.model.MyTasksSegment
import com.example.model.WithdrawalMethod
import com.example.viewmodel.CashIndiaViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleRobolectricTest {

    @Test
    fun verifyCashIndiaCleanZeroStateAndCoreInteractions() {
        val viewModel = CashIndiaViewModel()

        // 1. Initial Earn Screen state (Clean 0-balance state)
        val initial = viewModel.uiState.value
        assertEquals(CashIndiaTab.EARN, initial.selectedTab)
        assertEquals(0, initial.earnHeaderCoins)
        assertEquals("0 Coins | ₹0.00", initial.earnHeaderBalanceText)
        assertEquals(0.00, initial.walletBalanceRupees, 0.01)
        assertEquals(0, initial.walletBalanceCoins)
        assertEquals("₹0.00 (0 Coins)", initial.todaysCreditedText)
        assertEquals("₹0.00 (0 Tasks)", initial.pendingVerificationText)
        assertEquals(0, initial.ongoingTasks.size)
        assertEquals(0, initial.completedTasks.size)
        assertEquals(0, initial.recentWithdrawals.size)

        // 2. Real reward crediting callback
        viewModel.creditRealCoins(1600, "Partner Offerwall Task")
        assertEquals(1600, viewModel.uiState.value.earnHeaderCoins)
        assertEquals("1,600 Coins | ₹16.00", viewModel.uiState.value.earnHeaderBalanceText)
        assertEquals(1600, viewModel.uiState.value.walletBalanceCoins)
        assertEquals(16.00, viewModel.uiState.value.walletBalanceRupees, 0.01)
        assertEquals(1, viewModel.uiState.value.completedTasks.size)

        // 3. My Tasks Screen state
        viewModel.selectTab(CashIndiaTab.MY_TASKS)
        assertEquals(CashIndiaTab.MY_TASKS, viewModel.uiState.value.selectedTab)
        assertEquals(MyTasksSegment.ONGOING_PENDING, viewModel.uiState.value.selectedMyTasksSegment)
        viewModel.selectMyTasksSegment(MyTasksSegment.COMPLETED_PAID)
        assertEquals(MyTasksSegment.COMPLETED_PAID, viewModel.uiState.value.selectedMyTasksSegment)

        // 4. My Wallet Screen & Real Withdrawal
        viewModel.selectTab(CashIndiaTab.WALLET)
        assertEquals(WithdrawalMethod.UPI, viewModel.uiState.value.selectedWithdrawalMethod)
        assertEquals(10, viewModel.uiState.value.selectedWithdrawalAmount)
        assertEquals(listOf(10, 20, 50, 100), viewModel.uiState.value.withdrawalAmounts)

        // Select ₹10 withdrawal via UPI
        viewModel.selectWithdrawalMethod(WithdrawalMethod.UPI)
        viewModel.selectWithdrawalAmount(10)
        viewModel.updateUpiId("sahid@okaxis")
        assertTrue(viewModel.uiState.value.isUpiVerified)

        viewModel.submitWithdrawal()
        assertNotNull(viewModel.uiState.value.statusMessage)
        assertEquals(6.00, viewModel.uiState.value.walletBalanceRupees, 0.01)
        assertEquals(600, viewModel.uiState.value.walletBalanceCoins)
        assertEquals(600, viewModel.uiState.value.earnHeaderCoins)
        assertEquals("600 Coins | ₹6.00", viewModel.uiState.value.earnHeaderBalanceText)
        assertEquals(1, viewModel.uiState.value.recentWithdrawals.size)

        // 5. Profile & Help Screen state
        viewModel.selectTab(CashIndiaTab.PROFILE)
        assertTrue(viewModel.uiState.value.pushNotificationsEnabled)
        viewModel.togglePushNotifications(false)
        assertFalse(viewModel.uiState.value.pushNotificationsEnabled)
    }
}
