package com.alpha.admoresdk.domain.usecase

import com.alpha.admoresdk.domain.repository.DeviceDataRepository
import com.alpha.admoresdk.domain.repository.EventRepository
import com.alpha.admoresdk.domain.repository.PermissionRepository

/**
 * Use case for sending events with data.
 */
class SendEventUseCase(
    private val eventRepository: EventRepository,
    private val deviceDataRepository: DeviceDataRepository,
    private val permissionRepository: PermissionRepository
) {
    /**
     * Executes the use case.
     * @param eventName Name of the event
     * @param eventData Map of event data
     * @param uniqueKey The unique key for identifying the app
     */
    suspend fun execute(eventName: String, eventData: Map<String, Any>, uniqueKey: String) {
        // Create mutable map to store all collected data
        val combinedData = mutableMapOf<String, Any>()

        // Add event data first
        combinedData.putAll(eventData)
        combinedData["unique_key"] = uniqueKey

        // Get advertising ID first
        val adId = deviceDataRepository.getAdvertisingId()
        combinedData["advertising_id"] = adId ?: ""

        // Get all granted permissions
        val grantedPermissions = permissionRepository.getGrantedPermissions()

        // Collect data for each permission only once per collector type
        grantedPermissions.forEach { permission ->
            val permissionData = deviceDataRepository.collectDataForPermission(permission)
            combinedData.putAll(permissionData)

        }
        // Send the event
        eventRepository.sendEvent(eventName, combinedData)
    }
}
