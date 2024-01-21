package com.dave.infobroadcaster.di

import com.dave.infobroadcaster.data.AppConstants
import com.dave.infobroadcaster.data.api.ApiService
import com.dave.infobroadcaster.data.datasource.BroadcasterDatasource
import com.dave.infobroadcaster.data.datasource.BroadcasterDatasourceImpl
import com.dave.infobroadcaster.ui.repository.BroadcasterRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
    @InstallIn(SingletonComponent::class)
    class AppModule {

        @Provides
        @Singleton
        fun providesRetrofit () : Retrofit {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val httpClient = OkHttpClient().newBuilder().apply {
                addInterceptor(httpLoggingInterceptor)
            }

            httpClient.apply {
                readTimeout(60, TimeUnit.SECONDS)
            }

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory()).build()

            return Retrofit.Builder()
                .baseUrl(AppConstants.APP_BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        @Provides
        @Singleton
        fun providesApiService(retrofit: Retrofit) : ApiService {
            return retrofit.create(ApiService::class.java)
        }

        @Provides
        @Singleton
        fun providesBroadcasterDatasource(apiService: ApiService) : BroadcasterDatasource{
            return BroadcasterDatasourceImpl(apiService)
        }

        @Provides
        @Singleton
        fun providesBroadcasterRepository(broadcasterDatasource: BroadcasterDatasource) : BroadcasterRepository{
            return BroadcasterRepository(broadcasterDatasource)
        }

    }

