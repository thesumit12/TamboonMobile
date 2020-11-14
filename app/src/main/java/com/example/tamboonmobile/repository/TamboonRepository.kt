package com.example.tamboonmobile.repository

import com.example.tamboonmobile.model.Donation
import com.example.tamboonmobile.network.CharityResponse
import com.example.tamboonmobile.network.DonationResponse
import com.example.tamboonmobile.network.IApiService
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection

class TamboonRepository(private val apiService: IApiService) {

    suspend fun getCharityList(): CharityResponse {
        return try {
            val response = apiService.getCharities()
            response.statusCode = HttpsURLConnection.HTTP_OK
            response
        }catch (ex: HttpException) {
            val response = CharityResponse(0, arrayListOf())
            response.statusCode = ex.code()
            response.errorMsg = ex.message()
            response
        }catch (ex: Exception) {
            val response = CharityResponse(0, arrayListOf())
            response.statusCode = HttpsURLConnection.HTTP_INTERNAL_ERROR
            response.errorMsg = ex.localizedMessage ?: "Unknown error"
            response
        }

    }

    suspend fun makeDonation(donation: Donation): DonationResponse {
        return try {
            val response = apiService.giveDonation(donation)
            response.statusCode = HttpsURLConnection.HTTP_OK
            response
        }catch (ex: java.lang.Exception) {
            val response = DonationResponse(false, "-1", "-1")
            if (ex is HttpException) {
                response.statusCode = ex.code()
                response.errorMsg = ex.message()
            }else {
                response.statusCode = HttpsURLConnection.HTTP_INTERNAL_ERROR
                response.errorMsg = ex.localizedMessage ?: "Unknown error"
            }
            response
        }
    }
}