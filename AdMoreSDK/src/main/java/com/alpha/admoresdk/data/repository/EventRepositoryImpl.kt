package com.alpha.admoresdk.data.repository

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
        val event = Event(eventName, eventData)
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
                    eventCache.addEvent(event)
                }
            } catch (e: Exception) {
                // Cache event on error
                eventCache.addEvent(event)
            }
        } else {
            // Cache event when offline
            eventCache.addEvent(event)
        }
    }

    private suspend fun sendCachedEvents() {
        // Get all cached events
        val events = eventCache.getEvents()

        for (event in events) {
            try {
                // Encrypt data
                val encryptedData = dataEncryptor.encrypt(event.data)

                // Create request
                val request = EventRequest(
                    data = encryptedData,
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
