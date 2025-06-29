package com.alpha.admoresdk.data.source.local.collector

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.alpha.admoresdk.data.source.local.model.DeviceInfoKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Collector for advertising ID.
 */
class AdvertisingIdCollector(
    context: Context
) : BaseCollector(context) {

    private var cachedAdId: String? = null

    override suspend fun collect(): Map<String, Any> {
        val adId = getAdvertisingId() ?: "unknown"
        return mapOf(DeviceInfoKeys.ADVERTISING_ID.toKey() to adId)
    }

    /**
     * Gets the advertising ID.
     * @return The advertising ID, or null if not available
     */
    suspend fun getAdvertisingId(): String? {
        // Return cached value if available
        if (!cachedAdId.isNullOrEmpty()) {
            return cachedAdId
        }

        return try {
            withContext(Dispatchers.IO) {

                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                cachedAdId = adInfo.id
                cachedAdId
            }
        } catch (e: Exception) {
            null
        }
    }
}