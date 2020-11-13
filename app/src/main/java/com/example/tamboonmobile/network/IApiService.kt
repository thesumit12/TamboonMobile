package com.example.tamboonmobile.network

import com.example.tamboonmobile.model.Donation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IApiService {

    @GET("/charities")
    suspend fun getCharities(): CharityResponse

    @POST("/donations")
    suspend fun giveDonation(@Body data: Donation): DonationResponse
}