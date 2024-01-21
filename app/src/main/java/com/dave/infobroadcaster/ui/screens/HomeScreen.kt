package com.dave.infobroadcaster.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dave.infobroadcaster.ui.components.EmptyStateComponent
import com.dave.infobroadcaster.ui.components.Loader
import com.dave.infobroadcaster.ui.components.NewsList
import com.dave.infobroadcaster.ui.components.NewsRowComponent
import com.dave.infobroadcaster.ui.viewmodel.BroadcasterViewModel
import com.dave.utilities.ResourceState

const val TAG = "HomeScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    broadcasterViewModel: BroadcasterViewModel = hiltViewModel()
){

    val broadcasterInfoRes by broadcasterViewModel.broadcaster.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    )



    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        pageSize = PageSize.Fill,
        pageSpacing = 8.dp,
        pageCount = 100,
        pageContent = { page: Int ->



                when (broadcasterInfoRes) {
                    is ResourceState.Loading -> {
                        Log.d(TAG, "Inside_Loading")
                        Loader()
                    }

                    is ResourceState.Success -> {
                        Log.d(TAG, "Inside_Success")

                        val response = (broadcasterInfoRes as ResourceState.Success).data
//                        if (response != null) {
//                            Log.d(TAG, "Inside_Success ${response.status} = ${response.totalResults}")
//                            NewsList(response,page)
//                        }

                        if (response != null&&response.articles.isNotEmpty()) {
                            NewsRowComponent(page, response.articles[page])
                        }else{
                            EmptyStateComponent()
                        }
                    }

                    is ResourceState.Error -> {
                        val error = (broadcasterInfoRes as ResourceState.Error)
                        print("printing error")
                        print(error)
                        Log.d(TAG, "Inside_Error ${error}")
                    }
                }


        }
    )



}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}