package com.alpha.admoresdk.presentation.callback

/**
 * Callback interface for event sending.
 */
interface EventCallback {
    /**
     * Called when event sending is successful.
     */
    fun onSuccess()
    
    /**
     * Called when event sending fails.
     * @param error The error that occurred
     */
    fun onError(error: Throwable)
}