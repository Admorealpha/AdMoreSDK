package com.alpha.admoresdk.di

import com.alpha.admoresdk.core.encryption.DataEncryptor
import com.alpha.admoresdk.core.encryption.X25519Encryptor
import org.koin.dsl.module

val encryptionModule = module {
    single<DataEncryptor> { X25519Encryptor() }
}