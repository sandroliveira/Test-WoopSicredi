package br.com.ale.woopsicredi.ui

import androidx.lifecycle.ViewModel
import br.com.ale.woopsicredi.data.EventRepository

class MainViewModel: ViewModel() {

    val eventData = EventRepository().eventData

}