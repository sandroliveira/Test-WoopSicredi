package br.com.ale.woopsicredi

import br.com.ale.woopsicredi.data.User
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.IOException
import kotlin.jvm.Throws

open class SDKBaseTest {


   companion object {
       const val STATUS_SUCCESS = 200
       const val STATUS_FAILED = 400
       const val JSON_EVENT = "event_response"
       const val JSON_USER = "user_response"
       val USER_TEST = User("1", "ale", "ale@email.com")
   }

    @Throws(IOException::class)
   fun readFile(path: String?): String {
        val classLoader = this.javaClass.classLoader
        return classLoader.getResourceAsStream(path).bufferedReader().use { it.readText() }
   }

    fun setUpMockWebServer(mockWebServer: MockWebServer, fileName: String, statusCode: Int) {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(readFile(fileName))
                .setResponseCode(statusCode)
        )
    }
}