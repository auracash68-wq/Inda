package com.cashindia.app.engines

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.pubscale.sdkone.offerwall.OfferWall
import com.pubscale.sdkone.offerwall.OfferWallConfig
import com.pubscale.sdkone.offerwall.models.OfferWallInitListener
import com.pubscale.sdkone.offerwall.models.OfferWallListener
import com.pubscale.sdkone.offerwall.models.Reward
import com.pubscale.sdkone.offerwall.models.errors.InitError

/**
 * Modular PubScale Offerwall Engine for Cash India.
 * Configured with official App ID "81716375" and Sandbox/Test mode support.
 */
object PubScaleEngine {

    private const val TAG = "PubScaleEngine"
    const val DEFAULT_APP_ID = "81716375"

    @Volatile
    var isEngineReady: Boolean = false
        private set

    @Volatile
    var isSandboxMode: Boolean = true
        private set

    private var lastAppId: String = DEFAULT_APP_ID
    private var lastUserId: String = "test_user_01"
    private var lastEnabled: Boolean = true

    /**
     * Initializes the PubScale Offerwall SDK safely.
     *
     * @param context Application or Activity context
     * @param appId Official PubScale App ID ("81716375")
     * @param userId Unique user identifier
     * @param isSandbox Enables sandbox/test mode for pre-Play Store testing
     * @param isEnabled Remote feature flag to enable or disable the engine
     */
    @JvmStatic
    @Synchronized
    fun initEngine(
        context: Context,
        appId: String?,
        userId: String?,
        isSandbox: Boolean,
        isEnabled: Boolean
    ) {
        if (!isEnabled || appId.isNullOrBlank()) {
            isEngineReady = false
            Log.d(TAG, "PubScaleEngine is disabled or appId is empty.")
            return
        }

        isSandboxMode = isSandbox
        lastAppId = appId
        lastUserId = userId?.takeIf { it.isNotBlank() } ?: "test_user_01"
        lastEnabled = isEnabled

        if (isEngineReady && OfferWall.isSDKInitialized) {
            return
        }

        try {
            val builder = OfferWallConfig.Builder(context.applicationContext, appId)
                .setUniqueId(lastUserId)
                .setFullscreenEnabled(false)

            // Invoke setSandboxEnabled(isSandbox) dynamically if supported by the loaded SDK artifact
            try {
                val sandboxMethod = builder.javaClass.getMethod(
                    "setSandboxEnabled",
                    Boolean::class.javaPrimitiveType
                )
                sandboxMethod.invoke(builder, isSandbox)
            } catch (_: NoSuchMethodException) {
                Log.d(TAG, "Using default sandbox configuration for OfferWallConfig.Builder (isSandbox=$isSandbox)")
            }

            val config = builder.build()

            OfferWall.init(
                config,
                object : OfferWallInitListener {
                    override fun onInitSuccess() {
                        isEngineReady = true
                        Log.d(TAG, "PubScale Offerwall initialized (appId=$appId, sandbox=$isSandbox).")
                    }

                    override fun onInitFailed(error: InitError) {
                        isEngineReady = false
                        Log.w(TAG, "PubScale Offerwall init failed: ${error.message}")
                    }
                }
            )
        } catch (e: Exception) {
            isEngineReady = false
            Log.w(TAG, "Safe fallback triggered during PubScale OfferWall.init: ${e.message}")
        }
    }

    /**
     * Launches the PubScale Offerwall screen with reward callback handling.
     */
    @JvmStatic
    fun openOfferwall(activity: Activity, userId: String) {
        if (activity.isFinishing || activity.isDestroyed) return

        if (!isEngineReady && !OfferWall.isSDKInitialized) {
            Toast.makeText(
                activity,
                "Offerwall tasks are loading, please try again in a moment.",
                Toast.LENGTH_SHORT
            ).show()
            // Re-attempt initialization in the background if enabled
            if (lastEnabled) {
                initEngine(
                    context = activity.applicationContext,
                    appId = lastAppId,
                    userId = userId.ifBlank { lastUserId },
                    isSandbox = isSandboxMode,
                    isEnabled = true
                )
            }
            return
        }

        try {
            OfferWall.launch(
                activity,
                object : OfferWallListener {
                    override fun onOfferWallShowed() {
                        Log.d(TAG, "PubScale Offerwall displayed.")
                    }

                    override fun onOfferWallClosed() {
                        Log.d(TAG, "PubScale Offerwall closed.")
                    }

                    override fun onRewardClaimed(reward: Reward) {
                        val modeLabel = if (isSandboxMode) "Test Reward" else "Reward"
                        Toast.makeText(
                            activity,
                            "$modeLabel claimed: ${reward.amount.toInt()} ${reward.currency}",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailed(message: String) {
                        Log.w(TAG, "PubScale Offerwall launch failed: $message")
                        Toast.makeText(
                            activity,
                            "Offerwall tasks are loading, please try again in a moment.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        } catch (e: Exception) {
            Log.w(TAG, "Error launching PubScale Offerwall: ${e.message}")
            Toast.makeText(
                activity,
                "Offerwall tasks are loading, please try again in a moment.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
