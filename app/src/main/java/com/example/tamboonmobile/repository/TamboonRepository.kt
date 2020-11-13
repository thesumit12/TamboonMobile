package com.example.tamboonmobile.repository

import com.example.tamboonmobile.model.Charity
import com.example.tamboonmobile.model.Donation
import com.example.tamboonmobile.network.DonationResponse
import com.example.tamboonmobile.network.IApiService

class TamboonRepository(private val apiService: IApiService) {

    suspend fun getCharityList(): List<Charity> {
        val response = apiService.getCharities()
        return response.data
    }

    suspend fun makeDonation(donation: Donation): DonationResponse {
        return apiService.giveDonation(donation)
    }
}