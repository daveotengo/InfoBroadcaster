package com.dave.infobroadcaster.data.datasource

import com.dave.infobroadcaster.data.entity.BroadcasterResponse
import retrofit2.Response
import retrofit2.http.Query

interface BroadcasterDatasource {
    suspend fun getNewsHeadline(country :String) : Response<BroadcasterResponse>
}