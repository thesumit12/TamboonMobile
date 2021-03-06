package com.example.tamboonmobile.di

import com.example.tamboonmobile.ui.charity.MainViewModel
import com.example.tamboonmobile.ui.donation.DonationViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

internal val viewModelModule = module {

    viewModel { MainViewModel(repository = get() ) }
    viewModel { DonationViewModel(repository = get() ) }
}