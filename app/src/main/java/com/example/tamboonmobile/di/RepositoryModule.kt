package com.example.tamboonmobile.di

import com.example.tamboonmobile.repository.TamboonRepository
import org.koin.dsl.module.module

internal val repositoryModule = module {

    single { TamboonRepository(apiService = get()) }
}