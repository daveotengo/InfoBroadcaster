package com.dave.infobroadcaster.data.datasource

import com.dave.infobroadcaster.data.api.ApiService
import com.dave.infobroadcaster.data.entity.BroadcasterResponse
import retrofit2.Response
import javax.inject.Inject

class BroadcasterDatasourceImpl @Inject constructor(
    private val apiService: ApiService
) : BroadcasterDatasource {
    override suspend fun getNewsHeadline(country: String): Response<BroadcasterResponse> {
        return apiService.getNewsHeadline(country)
    }

}