package com.alpha.admoresdk.domain.usecase

import com.alpha.admoresdk.domain.repository.DeviceDataRepository
import com.alpha.admoresdk.domain.repository.EventRepository

/**
 * Use case for initializing the SDK.
 */
class InitializeSDKUseCase(
    private val eventRepository: EventRepository,
    private val deviceDataRepository: DeviceDataRepository
) {
    /**
     * Executes the use case.
     * @param uniqueKey The unique key for identifying the app
     */
    suspend fun execute(uniqueKey: String) {
        eventRepository.initialize(uniqueKey)
        deviceDataRepository.initialize()
    }
}