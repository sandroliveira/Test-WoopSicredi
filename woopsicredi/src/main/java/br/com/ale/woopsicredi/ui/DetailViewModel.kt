package br.com.ale.woopsicredi.ui

import android.widget.Toast
import androidx.lifecycle.ViewModel
import br.com.ale.woopsicredi.data.User
import br.com.ale.woopsicredi.data.WebService
import br.com.ale.woopsicredi.utils.ActivityApplicationInstance
import br.com.ale.woopsicredi.utils.Constants
import br.com.ale.woopsicredi.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val appContext = ActivityApplicationInstance

    fun sendPost(user: User) {
        val webService = WebService.getInstance()
        val call = webService.createPost(user)
        call?.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.isSuccessful) {
                    Toast.makeText(appContext.getCurrentActivity(), Constants.MSG_SUCCESSFUL, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(appContext.getCurrentActivity(), response.errorBody().toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                if (!Utils().checkNetwork()) {
                    Toast.makeText(appContext.getCurrentActivity(), Constants.LOG_MSG_NO_NETWORK, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(appContext.getCurrentActivity(), t.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}