package br.com.ale.woopsicredi.data

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import br.com.ale.woopsicredi.utils.ActivityApplicationInstance
import br.com.ale.woopsicredi.utils.Constants.Companion.LOG_MSG
import br.com.ale.woopsicredi.utils.Constants.Companion.LOG_MSG_NO_NETWORK
import br.com.ale.woopsicredi.utils.Constants.Companion.MSG_SUCCESSFUL
import br.com.ale.woopsicredi.utils.Constants.Companion.WEB_SERVICE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception

class EventRepository {

    val eventData = MutableLiveData<List<Event>>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getData()
        }
    }


    private fun callWebService(): Retrofit? {
        return try {
            Retrofit.Builder()
                .baseUrl(WEB_SERVICE)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        } catch (e: Exception) {
            if (!checkNetwork()){
                ActivityApplicationInstance.setToast(LOG_MSG_NO_NETWORK)
            } else {
                Log.e(LOG_MSG, e.toString())
            }
            null
        }
    }

    @WorkerThread
    suspend fun getData() {
        try {
            val retrofit = callWebService()
            val service = retrofit?.create(EventService::class.java)
            val serviceData = service?.getEventData()?.body() ?: emptyList()
            eventData.postValue(serviceData)
        } catch (e: Exception) {
            if (!checkNetwork()){
                ActivityApplicationInstance.setToast(LOG_MSG_NO_NETWORK)
            } else {
                Log.e(LOG_MSG, e.toString())
            }
        }
    }


      fun postData(context: Context, user: User) {
          try {
              val retrofit = callWebService()
              val eventService: EventService? = retrofit?.create(EventService::class.java)
              val call: Call<User>? = eventService?.createPost(user)

              call?.enqueue(object : Callback<User?> {
                  override fun onResponse(call: Call<User?>, response: Response<User?>) {
                      if (response.isSuccessful) {
                          Toast.makeText(context, MSG_SUCCESSFUL, Toast.LENGTH_LONG).show()
                      } else {
                          Toast.makeText(
                              context,
                              response.errorBody().toString(),
                              Toast.LENGTH_LONG
                          ).show()
                      }
                  }

                  override fun onFailure(call: Call<User?>, t: Throwable) {
                      Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                  }

              })
          } catch (e: Exception) {
              if (!checkNetwork()){
                  Toast.makeText(context, LOG_MSG_NO_NETWORK, Toast.LENGTH_LONG).show()
              } else {
                  Log.e(LOG_MSG, e.toString())
              }
          }
    }

    private fun checkNetwork(): Boolean {
        val connectivityManager = ActivityApplicationInstance
            .getApplicationContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }


}