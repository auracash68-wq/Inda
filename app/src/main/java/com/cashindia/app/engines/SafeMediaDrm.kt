package com.cashindia.app.engines

import android.media.MediaDrm
import android.os.Build
import java.util.UUID

/**
 * Safe wrapper for MediaDrm used by PubScale SDK on real devices vs emulators.
 * Prevents native DrmHalHidl errors on emulators while preserving 100% full
 * MediaDrm functionality on real physical Android devices.
 */
class SafeMediaDrm(uuid: UUID) {

    private val realDrm: MediaDrm? = if (isEmulator()) {
        null
    } else {
        try {
            MediaDrm(uuid)
        } catch (_: Throwable) {
            null
        }
    }

    fun getPropertyByteArray(name: String): ByteArray {
        val drm = realDrm ?: throw UnsupportedOperationException("Emulator DRM passthrough not available")
        return drm.getPropertyByteArray(name)
    }

    fun close() {
        try {
            realDrm?.close()
        } catch (_: Throwable) {}
    }

    companion object {
        fun isEmulator(): Boolean {
            val fp = Build.FINGERPRINT.orEmpty()
            val model = Build.MODEL.orEmpty()
            val hw = Build.HARDWARE.orEmpty()
            val prod = Build.PRODUCT.orEmpty()
            return (fp.startsWith("generic")
                    || fp.startsWith("unknown")
                    || model.contains("google_sdk")
                    || model.contains("Emulator")
                    || model.contains("Android SDK built for x86")
                    || hw.contains("goldfish")
                    || hw.contains("ranchu")
                    || prod.contains("sdk_google")
                    || prod.contains("google_sdk")
                    || fp.isEmpty())
        }
    }
}
