package br.com.ale.woopsicredi.data

import br.com.ale.woopsicredi.utils.Constants.Companion.WEB_SERVICE
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface WebService {

    @GET("/api/events")
    fun getEventData(): Call<List<Event>>

    @POST("/api/checkin")
    fun createPost(@Body user: User): Call<User>?

    companion object {
        private var webService: WebService? = null
        fun getInstance() : WebService {
            if (webService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(WEB_SERVICE)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                webService = retrofit.create(WebService::class.java)
            }
            return webService!!
        }
    }
}