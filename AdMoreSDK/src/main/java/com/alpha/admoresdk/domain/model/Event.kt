package com.alpha.admoresdk.domain.model

/**
 * Data class representing an event.
 */
data class Event(
    val name: String,
    val data: Map<String, Any>,
    val timestamp: Long = System.currentTimeMillis()
)
