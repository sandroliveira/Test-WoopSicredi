package br.com.ale.woopsicredi.data

import java.io.Serializable

data class Event (
    val people: List<Any>,
    val date: Long,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: Double,
    val title: String,
    val id: String
) : Serializable