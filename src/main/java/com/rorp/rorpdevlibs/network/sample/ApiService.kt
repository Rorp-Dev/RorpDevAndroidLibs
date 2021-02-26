package com.rorp.rorpdevlibs.network.sample

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    suspend fun getPosts(): Response<String>
}