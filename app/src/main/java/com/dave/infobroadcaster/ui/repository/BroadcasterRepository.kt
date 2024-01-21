package com.dave.infobroadcaster.ui.repository

import com.dave.infobroadcaster.data.datasource.BroadcasterDatasource
import com.dave.infobroadcaster.data.entity.BroadcasterResponse
import com.dave.utilities.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class BroadcasterRepository @Inject constructor(
    private val broadcasterDatasource: BroadcasterDatasource
){
//    suspend fun getBroadcasterHeadline(country: String) : Response<BroadcasterResponse>{
//        return broadcasterDatasource.getNewsHeadline(country)
//    }

    suspend fun getBroadcasterHeadline(country: String): Flow<ResourceState<BroadcasterResponse?>> {

        return flow {

            emit(ResourceState.Loading())

            val response = broadcasterDatasource.getNewsHeadline(country)

            if(response.isSuccessful && response.body() != null){
                emit(ResourceState.Success(response.body()))
            }else{
                emit(ResourceState.Error("Error fetching news data"))
            }
        }.catch { e->
            emit(ResourceState.Error(e?.localizedMessage ?: "Some error in flow"))
        }

    }
}