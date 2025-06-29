package com.alpha.admoresdk.data.source.local.collector

import android.content.Context
import android.content.SharedPreferences

class CollectorTimeManager(
    private val context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("collector_timestamps", Context.MODE_PRIVATE)

    companion object {
        private const val WIFI_LAST_COLLECTION = "wifi_last_collection"
        private const val BLUETOOTH_LAST_COLLECTION = "bluetooth_last_collection"
        private const val FOUR_HOURS_MILLIS = 4 * 60 * 60 * 1000L // 4 hours
    }

    fun shouldCollectWiFi(): Boolean {
        return shouldCollect(WIFI_LAST_COLLECTION)
    }

    fun shouldCollectBh(): Boolean {
        return shouldCollect(BLUETOOTH_LAST_COLLECTION)
    }

    fun updateWiCTime() {
        updateCollectionTime(WIFI_LAST_COLLECTION)
    }

    fun updateBhCTime() {
        updateCollectionTime(BLUETOOTH_LAST_COLLECTION)
    }

    private fun shouldCollect(key: String): Boolean {
        val lastCollectionTime = prefs.getLong(key, 0L)
        val currentTime = System.currentTimeMillis()
        return currentTime - lastCollectionTime >= FOUR_HOURS_MILLIS
    }

    private fun updateCollectionTime(key: String) {
        prefs.edit().putLong(key, System.currentTimeMillis()).apply()
    }
} 