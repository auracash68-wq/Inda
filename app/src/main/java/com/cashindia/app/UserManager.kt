package com.cashindia.app

import android.content.Context
import android.provider.Settings
import java.util.UUID

/**
 * Session & User Manager for Cash India.
 * Generates and permanently stores a real dynamic User ID in SharedPreferences,
 * ensuring seamless user tracking across app restarts and partner S2S postbacks.
 */
object UserManager {

    private const val PREFS_NAME = "cash_india_user_session"
    private const val KEY_USER_ID = "dynamic_user_id"

    @Volatile
    private var cachedUserId: String? = null

    /**
     * Retrieves the persistent unique User ID for this device.
     * Generates a new ID on first launch and caches it permanently.
     */
    @JvmStatic
    @Synchronized
    fun getUserId(context: Context): String {
        cachedUserId?.let { return it }

        val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val existingId = prefs.getString(KEY_USER_ID, null)
        if (!existingId.isNullOrBlank()) {
            cachedUserId = existingId
            return existingId
        }

        // Generate real unique user ID using Android ID or UUID
        val androidId = try {
            Settings.Secure.getString(
                context.applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )?.takeIf { it.isNotBlank() && it != "9774d56d682e549c" }
        } catch (_: Exception) {
            null
        }

        val generatedId = if (!androidId.isNullOrBlank()) {
            "cashindia_$androidId"
        } else {
            "cashindia_${UUID.randomUUID().toString().replace("-", "").take(16)}"
        }

        prefs.edit().putString(KEY_USER_ID, generatedId).apply()
        cachedUserId = generatedId
        return generatedId
    }
}
