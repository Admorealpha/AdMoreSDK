package com.alpha.admoresdk.core.network

import com.alpha.admoresdk.data.model.ApiResponse
import com.alpha.admoresdk.data.model.EventRequest
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for API service.
 */
interface ApiService {
    /**
     * Sends an event to the API.
     * @param eventRequest The event request
     * @return ApiResponse with success/failure information
     */
    @POST("datalake/records/encrypted")
    suspend fun sendEvent(@Body eventRequest: EventRequest): Response<JsonObject>
}