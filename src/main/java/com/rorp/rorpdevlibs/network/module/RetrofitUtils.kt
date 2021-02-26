package com.rorp.rorpdevlibs.network.module

import com.rorp.rorpdevlibs.network.sample.ApiService
import com.rorp.rorpdevlibs.util.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitUtils{
    companion object{
        private const val TIME_OUT = 30L

        private fun createOkHttpClient(): OkHttpClient {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            return OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        private fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .client(createOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create()).build()
        }

        fun createService(): ApiService {
            return createRetrofit().create(ApiService::class.java)
        }

    }
}