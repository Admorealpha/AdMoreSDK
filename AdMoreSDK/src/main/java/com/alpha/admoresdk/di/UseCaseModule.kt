package com.alpha.admoresdk.di

import com.alpha.admoresdk.domain.usecase.CollectDeviceDataUseCase
import com.alpha.admoresdk.domain.usecase.InitializeSDKUseCase
import com.alpha.admoresdk.domain.usecase.SendEventUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single {
        InitializeSDKUseCase(get(), get())
    }

    single {
        SendEventUseCase(get(), get(), get())
    }

    single {
        CollectDeviceDataUseCase(get(), get())
    }
}