package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cashindia.app.engines.PlaytimeEngine
import com.cashindia.app.engines.PubScaleEngine
import com.example.model.CashIndiaTab
import com.example.ui.components.CashIndiaBottomNavigation
import com.example.ui.screens.CashIndiaInteractiveSheet
import com.example.ui.screens.EarnScreen
import com.example.ui.screens.MyTasksScreen
import com.example.ui.screens.MyWalletScreen
import com.example.ui.screens.ProfileHelpScreen
import com.example.ui.theme.CashIndiaTheme
import com.example.ui.theme.MainScreenBackground
import com.example.viewmodel.CashIndiaViewModel

class MainActivity : ComponentActivity() {

    companion object {
        // TODO: Connect to Firebase Remote Config / Firestore
        const val PLAYTIME_SDK_HASH = "PENDING_HASH"
        const val CURRENT_USER_ID = "test_user_01"
        const val IS_PLAYTIME_ENABLED = false

        // TODO: Connect to Firebase Remote Config
        const val PUBSCALE_APP_ID = PubScaleEngine.DEFAULT_APP_ID // "81716375"
        const val IS_PUBSCALE_SANDBOX = true
        const val IS_PUBSCALE_ENABLED = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Step 1: Initialize adjoe Playtime Engine in safe fallback/sandbox mode
        // TODO: Connect to Firebase Remote Config / Firestore
        PlaytimeEngine.initEngine(
            context = applicationContext,
            sdkHash = PLAYTIME_SDK_HASH,
            userId = CURRENT_USER_ID,
            isEnabled = IS_PLAYTIME_ENABLED
        )

        // Step 2: Initialize PubScale Offerwall Engine in Sandbox/Test mode
        // TODO: Connect to Firebase Remote Config
        PubScaleEngine.initEngine(
            context = this,
            appId = PUBSCALE_APP_ID,
            userId = CURRENT_USER_ID,
            isSandbox = IS_PUBSCALE_SANDBOX,
            isEnabled = IS_PUBSCALE_ENABLED
        )

        enableEdgeToEdge()
        setContent {
            CashIndiaTheme {
                CashIndiaApp(currentUserId = CURRENT_USER_ID)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Re-verify / initialize Playtime Engine when returning from Usage Access Settings
        // TODO: Connect to Firebase Remote Config / Firestore
        PlaytimeEngine.initEngine(
            context = applicationContext,
            sdkHash = PLAYTIME_SDK_HASH,
            userId = CURRENT_USER_ID,
            isEnabled = IS_PLAYTIME_ENABLED
        )
    }
}

@Composable
fun CashIndiaApp(
    currentUserId: String = MainActivity.CURRENT_USER_ID,
    viewModel: CashIndiaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: CashIndiaTab.EARN.route
    val currentTab = CashIndiaTab.entries.find { it.route == currentRoute } ?: CashIndiaTab.EARN

    val navigateToTab: (CashIndiaTab) -> Unit = { targetTab ->
        if (currentRoute != targetTab.route) {
            viewModel.selectTab(targetTab)
            navController.navigate(targetTab.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MainScreenBackground,
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            CashIndiaBottomNavigation(
                selectedTab = currentTab,
                onSelectTab = navigateToTab
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CashIndiaTab.EARN.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            composable(CashIndiaTab.EARN.route) {
                EarnScreen(
                    uiState = uiState,
                    onSelectFilter = viewModel::selectTaskFilter,
                    onOpenSheet = viewModel::openSheet,
                    currentUserId = currentUserId
                )
            }
            composable(CashIndiaTab.MY_TASKS.route) {
                BackHandler { navigateToTab(CashIndiaTab.EARN) }
                MyTasksScreen(
                    uiState = uiState,
                    onSelectSegment = viewModel::selectMyTasksSegment,
                    onOpenSheet = viewModel::openSheet
                )
            }
            composable(CashIndiaTab.WALLET.route) {
                BackHandler { navigateToTab(CashIndiaTab.EARN) }
                MyWalletScreen(
                    uiState = uiState,
                    onSelectMethod = viewModel::selectWithdrawalMethod,
                    onUpiIdChange = viewModel::updateUpiId,
                    onSelectAmount = viewModel::selectWithdrawalAmount,
                    onWithdrawClick = viewModel::submitWithdrawal
                )
            }
            composable(CashIndiaTab.PROFILE.route) {
                BackHandler { navigateToTab(CashIndiaTab.EARN) }
                ProfileHelpScreen(
                    uiState = uiState,
                    onTogglePushNotifications = viewModel::togglePushNotifications,
                    onOpenSheet = viewModel::openSheet
                )
            }
        }

        val activeSheet = uiState.activeSheet
        if (activeSheet != null) {
            CashIndiaInteractiveSheet(
                sheet = activeSheet,
                selectedLanguage = uiState.selectedLanguage,
                onSelectLanguage = viewModel::selectLanguage,
                onSubmitTicket = viewModel::submitSupportTicket,
                onDismiss = viewModel::dismissSheet
            )
        }
    }
}
