package com.cashindia.app.engines

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.cashindia.app.UserManager

/**
 * Modular CPX Research Survey Engine for Cash India.
 * Direct integration with official CPX Research Survey Web Portal.
 * Launches real survey sessions keyed by dynamic User ID.
 */
object CpxResearchEngine {

    private const val TAG = "CpxResearchEngine"

    // Official CPX Research App ID (Configurable / Firebase Remote Config fallback)
    const val DEFAULT_APP_ID = "21568"

    @Volatile
    var currentAppId: String = DEFAULT_APP_ID

    private var rewardCreditListener: ((Int, String) -> Unit)? = null

    @JvmStatic
    fun setRewardCreditListener(listener: ((Int, String) -> Unit)?) {
        rewardCreditListener = listener
    }

    /**
     * Builds the official CPX Research Survey URL for the user.
     * URL Format: https://offer.cpx-research.com/index.php?app_id=[APP_ID]&ext_user_id=[USER_ID]
     */
    @JvmStatic
    fun buildSurveyUrl(userId: String, appId: String = currentAppId): String {
        val cleanUserId = java.net.URLEncoder.encode(userId.ifBlank { "guest_user" }, "UTF-8")
        val cleanAppId = java.net.URLEncoder.encode(appId.ifBlank { DEFAULT_APP_ID }, "UTF-8")
        return "https://offer.cpx-research.com/index.php?app_id=$cleanAppId&ext_user_id=$cleanUserId&subid_1=cashindia"
    }

    /**
     * Launches the real CPX Research Survey portal via CustomTab or Browser intent.
     */
    @JvmStatic
    fun openSurveys(activity: Activity, userId: String, appId: String = currentAppId) {
        if (activity.isFinishing || activity.isDestroyed) return

        val resolvedUserId = userId.ifBlank { UserManager.getUserId(activity) }
        val targetUrl = buildSurveyUrl(resolvedUserId, appId)

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                // Hint for Custom Tabs in Chrome/browser
                putExtra("android.support.customtabs.extra.TITLE_VISIBILITY", 1)
            }
            activity.startActivity(intent)
            Toast.makeText(
                activity,
                "Opening CPX Research Surveys...",
                Toast.LENGTH_SHORT
            ).show()
            Log.d(TAG, "Launched CPX Surveys for user: $resolvedUserId with url: $targetUrl")
        } catch (e: Exception) {
            Log.w(TAG, "Failed to launch CPX Survey intent: ${e.message}")
            Toast.makeText(
                activity,
                "Unable to open browser for CPX Surveys.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Helper to manually or via postback credit completed survey coins to user wallet.
     */
    @JvmStatic
    fun creditSurveyReward(coins: Int, title: String = "CPX Research Survey") {
        if (coins > 0) {
            rewardCreditListener?.invoke(coins, title)
        }
    }
}
