package br.com.ale.woopsicredi.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface EventService {

    @GET("/api/events")
    suspend fun getEventData(): Response<List<Event>>

    @POST("/api/checkin")
    fun createPost(@Body user: User): Call<User>?
}