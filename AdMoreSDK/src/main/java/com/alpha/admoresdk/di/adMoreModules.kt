package com.alpha.admoresdk.di

import org.koin.dsl.module

/**
 * Main Koin module that combines all other modules
 */
val adMoreModules = listOf(
    networkModule,
    repositoryModule,
    useCaseModule,
    collectorModule,
    encryptionModule,
    module {
        single { com.alpha.admoresdk.core.logger.Logger() }
    }
)

/**
 * Core module for basic dependencies like Logger
 */
