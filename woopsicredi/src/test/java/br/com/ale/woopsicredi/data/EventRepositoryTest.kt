package br.com.ale.woopsicredi.data

import br.com.ale.woopsicredi.SDKBaseTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(MockitoJUnitRunner::class)
class EventRepositoryTest: SDKBaseTest() {

    @get:Rule
    val mockWebServer = MockWebServer()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Test
    fun `getEventData - status 200 returned`()  {

        val service: WebService = retrofit.create(WebService::class.java)

        setUpMockWebServer(mockWebServer, JSON_EVENT, STATUS_SUCCESS)

        val currentResponse = service.getEventData().execute()

        assert(currentResponse.code() == STATUS_SUCCESS)
    }

    @Test
    fun `getEventData - check status 400 returned`(){
        val service: WebService = retrofit.create(WebService::class.java)

        setUpMockWebServer(mockWebServer, JSON_EVENT, STATUS_FAILED)

        val currentResponse = service.getEventData().execute()

        assert(currentResponse.code() == STATUS_FAILED)
    }

    @Test
    fun `createPost - check status 200 returned`(){
        val service: WebService = retrofit.create(WebService::class.java)

        setUpMockWebServer(mockWebServer, JSON_USER, STATUS_SUCCESS)

        val currentResponse = service.createPost(USER_TEST)?.execute()

        assert(currentResponse?.code() == STATUS_SUCCESS)
    }

    @Test
    fun `createPost - check status 400 returned`(){
        val service: WebService = retrofit.create(WebService::class.java)

        setUpMockWebServer(mockWebServer, JSON_USER, STATUS_FAILED)

        val currentResponse = service.createPost(USER_TEST)?.execute()

        assert(currentResponse?.code() == STATUS_FAILED)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
