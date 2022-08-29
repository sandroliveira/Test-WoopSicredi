package br.com.ale.woopsicredi.ui

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ale.woopsicredi.data.Event
import br.com.ale.woopsicredi.data.WebService
import br.com.ale.woopsicredi.utils.ActivityApplicationInstance
import br.com.ale.woopsicredi.utils.Constants
import br.com.ale.woopsicredi.utils.Utils
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val appContext = ActivityApplicationInstance
    val eventData = MutableLiveData<List<Event>>()

    fun getEvent() {
        val webService = WebService.getInstance()
        val response = webService.getEventData()
        response.enqueue(object : retrofit2.Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                eventData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                if (!Utils().checkNetwork()) {
                    Toast.makeText(appContext.getCurrentActivity(), Constants.LOG_MSG_NO_NETWORK, Toast.LENGTH_LONG).show()
                } else {
                    t.message?.let { ActivityApplicationInstance.setToast(it) }
                }
            }
        })
    }

}