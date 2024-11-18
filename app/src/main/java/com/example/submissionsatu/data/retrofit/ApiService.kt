package com.example.submissionsatu.data.retrofit

import com.example.submissionsatu.data.response.EventFinishedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int
    ): Call<EventFinishedResponse>
}