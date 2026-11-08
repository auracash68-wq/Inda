package com.cashindia.app.engines

import android.app.Activity
import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import io.adjoe.sdk.Playtime
import io.adjoe.sdk.PlaytimeException
import io.adjoe.sdk.PlaytimeInitialisationListener
import io.adjoe.sdk.PlaytimeNotInitializedException
import io.adjoe.sdk.PlaytimeOptions
import io.adjoe.sdk.PlaytimeParams

/**
 * Step 1: Modular "adjoe Playtime" Time-Based Gaming Engine.
 * Supports a safe sandbox/fallback state ("PENDING_HASH") for unpublished builds
 * and Google Play Store compliant Prominent Disclosure for PACKAGE_USAGE_STATS.
 */
object PlaytimeEngine {

    private const val TAG = "PlaytimeEngine"
    const val PENDING_HASH = "PENDING_HASH"

    @Volatile
    var isEngineReady: Boolean = false
        private set

    /**
     * Initializes the adjoe Playtime SDK safely.
     * If disabled or configured with "PENDING_HASH", enters safe fallback mode without crashing.
     */
    @JvmStatic
    @Synchronized
    fun initEngine(
        context: Context,
        sdkHash: String?,
        userId: String?,
        isEnabled: Boolean
    ) {
        if (!isEnabled || sdkHash.isNullOrBlank() || sdkHash.equals(PENDING_HASH, ignoreCase = true)) {
            isEngineReady = false
            Log.d(TAG, "PlaytimeEngine is in sandbox/fallback mode (isEnabled=$isEnabled, sdkHash=$sdkHash).")
            return
        }

        if (isEngineReady && Playtime.isInitialized()) {
            return
        }

        try {
            val options = PlaytimeOptions()
            if (!userId.isNullOrBlank()) {
                options.setUserId(userId)
            }

            Playtime.init(
                context.applicationContext,
                sdkHash,
                options,
                object : PlaytimeInitialisationListener {
                    override fun onInitialisationFinished() {
                        isEngineReady = true
                        Log.d(TAG, "Playtime SDK initialized successfully.")
                    }

                    override fun onInitialisationError(exception: Exception?) {
                        isEngineReady = false
                        Log.w(TAG, "Playtime SDK initialization error: ${exception?.message}")
                    }
                }
            )
        } catch (e: Exception) {
            isEngineReady = false
            Log.w(TAG, "Safe fallback triggered during Playtime.init: ${e.message}")
        }
    }

    /**
     * Verifies whether the user has granted Usage Access (AppOpsManager.OPSTR_GET_USAGE_STATS).
     */
    @JvmStatic
    fun hasUsageStatsPermission(context: Context): Boolean {
        return try {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as? AppOpsManager
                ?: return false
            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOps.unsafeCheckOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    Process.myUid(),
                    context.packageName
                )
            } else {
                @Suppress("DEPRECATION")
                appOps.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    Process.myUid(),
                    context.packageName
                )
            }
            mode == AppOpsManager.MODE_ALLOWED
        } catch (e: Exception) {
            Log.w(TAG, "Unable to check usage stats permission: ${e.message}")
            false
        }
    }

    /**
     * Displays a Google Play User Data Policy compliant Prominent Disclosure dialog
     * before redirecting the user to Android's Usage Access Settings screen.
     */
    @JvmStatic
    fun showProminentDisclosureDialog(activity: Activity) {
        if (activity.isFinishing || activity.isDestroyed) return

        AlertDialog.Builder(activity)
            .setTitle("Enable Playtime Rewards")
            .setMessage(
                "To reward you with coins for every minute you play games, Cash India needs permission to detect which game you are playing and for how long (App Usage Stats). Please tap 'Allow Access' and turn on the switch for Cash India."
            )
            .setCancelable(true)
            .setPositiveButton("Allow Access") { dialog, _ ->
                dialog.dismiss()
                try {
                    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    activity.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        activity,
                        "Unable to open Usage Access Settings on this device.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Not Now") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    /**
     * Launches the adjoe Playtime offerwall catalog after checking Usage Stats permission
     * and engine readiness.
     */
    @JvmStatic
    fun openPlaytimeCatalog(activity: Activity, userId: String) {
        // Step A: Verify Usage Stats permission first
        if (!hasUsageStatsPermission(activity)) {
            showProminentDisclosureDialog(activity)
            return
        }

        // Step B: Verify engine readiness (safe fallback when in PENDING_HASH / sandbox mode)
        if (!isEngineReady) {
            Toast.makeText(
                activity,
                "Playtime Games are updating! Please try Daily Surveys or App Tasks.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        // Step C: Launch Playtime Catalog safely
        try {
            val playIntent = Playtime.getCatalogIntent(activity, null as PlaytimeParams?)
            activity.startActivity(playIntent)
        } catch (e: PlaytimeNotInitializedException) {
            isEngineReady = false
            Toast.makeText(
                activity,
                "Playtime Games are updating! Please try Daily Surveys or App Tasks.",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: PlaytimeException) {
            Toast.makeText(
                activity,
                "Playtime Games are updating! Please try Daily Surveys or App Tasks.",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Log.w(TAG, "Error launching Playtime catalog: ${e.message}")
            Toast.makeText(
                activity,
                "Playtime Games are updating! Please try Daily Surveys or App Tasks.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
