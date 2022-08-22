package br.com.ale.woopsicredi.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.ale.woopsicredi.data.EventRepository
import br.com.ale.woopsicredi.data.User

class DetailViewModel: ViewModel() {

    fun sendPost(context: Context, user: User){
        EventRepository().postData(context, user)
    }
}