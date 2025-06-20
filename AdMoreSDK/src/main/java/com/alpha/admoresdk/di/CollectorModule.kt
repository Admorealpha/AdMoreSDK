package com.alpha.admoresdk.di

import com.alpha.admoresdk.core.storage.EventCache
import com.alpha.admoresdk.data.source.local.PermissionChecker
import com.alpha.admoresdk.data.source.local.collector.*
import com.alpha.admoresdk.data.source.local.factory.CollectorFactory
import com.alpha.admoresdk.data.source.local.factory.CollectorFactoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val collectorModule = module {

    // Core dependencies
    single { CollectorTimeManager(androidContext()) }
    single { PermissionChecker(androidContext()) }
    single { EventCache() }

    // Base Collectors (individual instances)
    single<DeviceInfoCollector> { DeviceInfoCollector(androidContext()) }
    single<AdvertisingIdCollector> { AdvertisingIdCollector(androidContext()) }
    single<NetworkInfoCollector> { NetworkInfoCollector(androidContext()) }

    // Permission Required Collectors (individual instances)
    single<LocationCollector> { LocationCollector(androidContext()) }
    single<BluetoothCollector> { BluetoothCollector(androidContext(), get()) }
    single<WifiCollector> { WifiCollector(androidContext(), get()) }
    single<ContactCollector> { ContactCollector(androidContext()) }
    single<CalendarCollector> { CalendarCollector(androidContext()) }
    single<SmsCollector> { SmsCollector(androidContext()) }

    // Direct CollectorFactory with explicit dependencies
    single<CollectorFactory> {
        CollectorFactoryImpl(
            baseCollectors = listOf(
                get<DeviceInfoCollector>(),
                get<AdvertisingIdCollector>(),
                get<NetworkInfoCollector>()
            ),
            permissionRequiredCollectors = listOf(
                get<LocationCollector>(),
                get<BluetoothCollector>(),
                get<WifiCollector>(),
                get<ContactCollector>(),
                get<CalendarCollector>(),
                get<SmsCollector>()
            )
        )
    }
}