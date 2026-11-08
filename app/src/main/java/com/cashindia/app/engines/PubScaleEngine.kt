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
 * Direct integration with official PubScale Offerwall SDK (App ID: 81716375).
 */
object PubScaleEngine {

    private const val TAG = "PubScaleEngine"
    const val DEFAULT_APP_ID = "81716375"

    @Volatile
    var isEngineReady: Boolean = false
        private set

    @Volatile
    var isSandboxMode: Boolean = false
        private set

    private var lastAppId: String = DEFAULT_APP_ID
    private var lastUserId: String = ""
    private var lastEnabled: Boolean = true

    private var rewardCreditListener: ((Int, String) -> Unit)? = null

    @JvmStatic
    fun setRewardCreditListener(listener: ((Int, String) -> Unit)?) {
        rewardCreditListener = listener
    }

    /**
     * Initializes the real PubScale Offerwall SDK.
     *
     * @param context Application or Activity context
     * @param appId Official PubScale App ID ("81716375")
     * @param userId Unique user identifier
     * @param isSandbox Sandbox test flag
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
        lastUserId = userId?.takeIf { it.isNotBlank() } ?: com.cashindia.app.UserManager.getUserId(context)
        lastEnabled = isEnabled

        if (OfferWall.isSDKInitialized) {
            isEngineReady = true
            return
        }

        try {
            val builder = OfferWallConfig.Builder(context.applicationContext, appId)
                .setUniqueId(lastUserId)
                .setFullscreenEnabled(false)

            try {
                val sandboxMethod = builder.javaClass.getMethod(
                    "setSandboxEnabled",
                    Boolean::class.javaPrimitiveType
                )
                sandboxMethod.invoke(builder, isSandbox)
            } catch (_: NoSuchMethodException) {
                Log.d(TAG, "OfferWallConfig default builder setup (isSandbox=$isSandbox)")
            }

            val config = builder.build()

            OfferWall.init(
                config,
                object : OfferWallInitListener {
                    override fun onInitSuccess() {
                        isEngineReady = true
                        Log.d(TAG, "PubScale Offerwall SDK initialized successfully (appId=$appId).")
                    }

                    override fun onInitFailed(error: InitError) {
                        isEngineReady = false
                        Log.w(TAG, "PubScale Offerwall init failed: ${error.message}")
                    }
                }
            )
        } catch (e: Exception) {
            isEngineReady = false
            Log.w(TAG, "Exception during PubScale OfferWall.init: ${e.message}")
        }
    }

    /**
     * Launches the real PubScale Offerwall screen.
     * If launch fails or is not ready, shows the exact real error in a Toast.
     */
    @JvmStatic
    fun openOfferwall(activity: Activity, userId: String) {
        if (activity.isFinishing || activity.isDestroyed) return

        val resolvedUserId = userId.ifBlank {
            lastUserId.ifBlank { com.cashindia.app.UserManager.getUserId(activity) }
        }
        lastUserId = resolvedUserId

        if (!OfferWall.isSDKInitialized) {
            // Re-attempt init if needed and inform user of real status
            if (lastEnabled) {
                initEngine(
                    context = activity.applicationContext,
                    appId = lastAppId,
                    userId = lastUserId,
                    isSandbox = isSandboxMode,
                    isEnabled = true
                )
            }
            val statusMsg = "PubScale Offerwall is initializing. (Status: Pending or Connecting). Please try again in a few seconds."
            Toast.makeText(activity, statusMsg, Toast.LENGTH_LONG).show()
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
                        val coins = reward.amount.toInt().coerceAtLeast(1)
                        val title = "PubScale: ${reward.currency.ifBlank { "Offerwall Task" }}"
                        rewardCreditListener?.invoke(coins, title)
                        Toast.makeText(
                            activity,
                            "+$coins Coins credited from PubScale Offerwall!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailed(message: String) {
                        Log.w(TAG, "PubScale OfferWall.launch onFailed: $message")
                        Toast.makeText(
                            activity,
                            "PubScale Offerwall: $message",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
        } catch (e: Exception) {
            Log.w(TAG, "PubScale launch exception: ${e.message}")
            Toast.makeText(
                activity,
                "PubScale Offerwall Error: ${e.localizedMessage ?: e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
