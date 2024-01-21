package com.dave.infobroadcaster.data.api

import com.dave.infobroadcaster.data.entity.BroadcasterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getNewsHeadline(
        @Query("country") country :String,
        @Query("apiKey") apiKey:String = "c952321138d8486c9ac371b05fc9c4ae"
    ) : Response<BroadcasterResponse>

}