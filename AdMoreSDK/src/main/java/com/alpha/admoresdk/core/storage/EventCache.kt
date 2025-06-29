package com.alpha.admoresdk.core.storage

import com.alpha.admoresdk.data.model.EventRequest
import com.alpha.admoresdk.domain.model.Event
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * In-memory cache for events.
 */

class EventCache {
    private val cachedEvents = mutableListOf<EventRequest>()
    private val mutex = Mutex()

    /**
     * Adds an event to the cache.
     * @param event The event to add
     */
    suspend fun addEvent(event: EventRequest) {
        mutex.withLock {
            cachedEvents.add(event)
        }
    }

    /**
     * Removes an event from the cache.
     * @param event The event to remove
     */
    suspend fun removeEvent(event: EventRequest) {
        mutex.withLock {
            cachedEvents.remove(event)
        }
    }

    /**
     * Gets all events in the cache.
     * @return List of cached events
     */
    suspend fun getEvents(): List<EventRequest> {
        return mutex.withLock {
            cachedEvents.toList()
        }
    }

    /**
     * Clears all events from the cache.
     */
    suspend fun clearEvents() {
        mutex.withLock {
            cachedEvents.clear()
        }
    }
}
