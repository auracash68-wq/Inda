package com.example

import com.example.model.CashIndiaTab
import com.example.model.MyTasksSegment
import com.example.model.TaskFilter
import com.example.model.WithdrawalMethod
import com.example.viewmodel.CashIndiaViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleRobolectricTest {

    @Test
    fun verifyCashIndiaStateAndInteractions() {
        val viewModel = CashIndiaViewModel()

        // 1. Initial Earn Screen state
        val initial = viewModel.uiState.value
        assertEquals(CashIndiaTab.EARN, initial.selectedTab)
        assertEquals("2,500 Coins | ₹25.00", initial.earnHeaderBalanceText)
        assertEquals(2, initial.surveys.size)
        assertEquals(3, initial.allAppTasks.size)
        assertEquals(TaskFilter.ALL_TASKS, initial.selectedTaskFilter)

        // Filter tasks
        viewModel.selectTaskFilter(TaskFilter.EASY_INSTALL)
        assertEquals(TaskFilter.EASY_INSTALL, viewModel.uiState.value.selectedTaskFilter)

        // 2. My Tasks Screen state
        viewModel.selectTab(CashIndiaTab.MY_TASKS)
        assertEquals(CashIndiaTab.MY_TASKS, viewModel.uiState.value.selectedTab)
        assertEquals("₹45.00 (4,500 Coins)", viewModel.uiState.value.todaysCreditedText)
        assertEquals("₹30.00 (2 Tasks)", viewModel.uiState.value.pendingVerificationText)
        assertEquals(2, viewModel.uiState.value.ongoingTasks.size)
        assertEquals(2, viewModel.uiState.value.completedTasks.size)

        viewModel.selectMyTasksSegment(MyTasksSegment.COMPLETED_PAID)
        assertEquals(MyTasksSegment.COMPLETED_PAID, viewModel.uiState.value.selectedMyTasksSegment)

        // 3. My Wallet Screen state
        viewModel.selectTab(CashIndiaTab.WALLET)
        assertEquals(WithdrawalMethod.UPI, viewModel.uiState.value.selectedWithdrawalMethod)
        assertEquals(10, viewModel.uiState.value.selectedWithdrawalAmount)
        assertEquals(listOf(10, 20, 50, 100), viewModel.uiState.value.withdrawalAmounts)
        assertEquals(2, viewModel.uiState.value.recentWithdrawals.size)

        // Select amount and withdrawal method
        viewModel.selectWithdrawalAmount(20)
        assertEquals(20, viewModel.uiState.value.selectedWithdrawalAmount)
        viewModel.selectWithdrawalMethod(WithdrawalMethod.GOOGLE_PLAY)
        assertEquals(WithdrawalMethod.GOOGLE_PLAY, viewModel.uiState.value.selectedWithdrawalMethod)

        // Validate UPI ID & withdrawal submission
        viewModel.selectWithdrawalMethod(WithdrawalMethod.UPI)
        viewModel.updateUpiId("sahid@okaxis")
        assertTrue(viewModel.uiState.value.isUpiVerified)
        viewModel.submitWithdrawal()
        assertNotNull(viewModel.uiState.value.statusMessage)

        // 4. Profile & Help Screen state
        viewModel.selectTab(CashIndiaTab.PROFILE)
        assertTrue(viewModel.uiState.value.pushNotificationsEnabled)
        viewModel.togglePushNotifications(false)
        assertFalse(viewModel.uiState.value.pushNotificationsEnabled)
    }
}
