package com.example.tamboonmobile.network

import com.example.tamboonmobile.model.Charity

class CharityResponse(
    val total: Int,
    val data: List<Charity>
): BaseResponse()