package com.alpha.admoresdk.di

import com.alpha.admoresdk.data.repository.DeviceDataRepositoryImpl
import com.alpha.admoresdk.data.repository.EventRepositoryImpl
import com.alpha.admoresdk.data.repository.PermissionRepositoryImpl
import com.alpha.admoresdk.domain.repository.DeviceDataRepository
import com.alpha.admoresdk.domain.repository.EventRepository
import com.alpha.admoresdk.domain.repository.PermissionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single<DeviceDataRepository> {
        DeviceDataRepositoryImpl(get())
    }

    single<EventRepository> {
        EventRepositoryImpl(get(), get(), get(), get())
    }

    single<PermissionRepository> {
        PermissionRepositoryImpl(androidContext(), get())
    }
}