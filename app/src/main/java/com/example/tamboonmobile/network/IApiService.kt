package com.example.tamboonmobile.network

import com.example.tamboonmobile.model.Donation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface IApiService {

    @Headers("Content-Type: application/json")
    @GET("charities")
    suspend fun getCharities(): CharityResponse

    @Headers("Content-Type: application/json")
    @POST("donations")
    suspend fun giveDonation(@Body data: Donation): DonationResponse
}