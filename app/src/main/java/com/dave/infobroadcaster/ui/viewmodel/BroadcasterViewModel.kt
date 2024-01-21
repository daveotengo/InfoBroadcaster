package com.dave.infobroadcaster.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dave.infobroadcaster.data.AppConstants
import com.dave.infobroadcaster.data.entity.BroadcasterResponse
import com.dave.infobroadcaster.ui.repository.BroadcasterRepository
import com.dave.utilities.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BroadcasterViewModel @Inject constructor(
    private val broadcasterRepository : BroadcasterRepository
) : ViewModel() {

    val value = "value"

    init{
        Log.d(TAG,"Init block of BroadcasterViewModel")
        getBroadcasterInfo(AppConstants.COUNTRY)
    }

    private val _broadcaster : MutableStateFlow<ResourceState<BroadcasterResponse?>> =
        MutableStateFlow(ResourceState.Loading())

    val broadcaster : StateFlow<ResourceState<BroadcasterResponse?>> = _broadcaster
   // val _broadcaster = MutableLiveData<ResourceState<BroadcasterResponse?>>()


    fun getBroadcasterInfo(country: String){
        viewModelScope.launch(Dispatchers.IO){
            broadcasterRepository.getBroadcasterHeadline(country)
                .collectLatest {
                    broadcasterResponse ->
                    _broadcaster.value = broadcasterResponse
//                    val nonNullableResponse = resourceState.data ?: getDefaultBroadcasterResponse()
//                    _broadcaster.value = resourceState.copy(data = nonNullableResponse)
                }
        }
    }

    companion object{
        const val TAG = "BroadcasterViewModel"
    }

}