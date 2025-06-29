package com.alpha.admoresdk.data.repository

import android.util.Log
import com.alpha.admoresdk.core.encryption.DataEncryptor
import com.alpha.admoresdk.core.network.ApiService
import com.alpha.admoresdk.core.network.NetworkMonitor
import com.alpha.admoresdk.core.storage.EventCache
import com.alpha.admoresdk.data.model.EventRequest
import com.alpha.admoresdk.domain.model.Event
import com.alpha.admoresdk.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Implementation of EventRepository.
 */
class EventRepositoryImpl(
    private val apiService: ApiService,
    private val eventCache: EventCache,
    private val networkMonitor: NetworkMonitor,
    private val dataEncryptor: DataEncryptor
) : EventRepository {

    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var uniqueKey: String? = null

    override suspend fun initialize(uniqueKey: String) {
        this.uniqueKey = uniqueKey

        // Start network monitoring
        repositoryScope.launch {
            networkMonitor.isConnected.collectLatest { isConnected ->
                if (isConnected) {
                    // Send cached events when network is available
                    sendCachedEvents()
                }
            }
        }
    }

    override suspend fun sendEvent(eventName: String, eventData: Map<String, Any>) {
        if (networkMonitor.isNetworkAvailable()) {
            try {
                // Encrypt data before sending
                val encryptedData = dataEncryptor.encrypt(eventData)

                // Create request object
                val request = EventRequest(
                    data = encryptedData,
                )

                // Send to API
                val response = apiService.sendEvent(request)

                // Check if success
                if (!response.isSuccessful) {
                    // Cache event for retry
                    eventCache.addEvent(request)
                }
            } catch (e: Exception) {
                val encryptedData = dataEncryptor.encrypt(eventData)

                // Create request object
                val request = EventRequest(
                    data = encryptedData,
                )
                // Cache event on error
                eventCache.addEvent(request)
            }
        } else {
            val encryptedData = dataEncryptor.encrypt(eventData)

            // Create request object
            val request = EventRequest(
                data = encryptedData,
            )
            // Cache event when offline
            eventCache.addEvent(request)
        }
    }



    private suspend fun sendCachedEvents() {
        // Get all cached events
        val events = eventCache.getEvents()

        for (event in events) {
            try {
                // Create request
                val request = EventRequest(
                    data = event.data,
                )

                // Send to API
                val response = apiService.sendEvent(request)

                // Remove from cache if successful
                if (response.isSuccessful) {
                    eventCache.removeEvent(event)
                }
            } catch (e: Exception) {
                // Keep in cache to retry later
                continue
            }
        }
    }
}
