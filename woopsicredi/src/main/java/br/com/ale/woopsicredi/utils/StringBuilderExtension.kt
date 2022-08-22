package br.com.ale.woopsicredi.utils

import br.com.ale.woopsicredi.data.Event

fun StringBuilder.createTextDescription(event: Event): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append(event.title)
    stringBuilder.appendLine()
    stringBuilder.appendLine()
    stringBuilder.append(event.description)
    stringBuilder.appendLine()
    stringBuilder.append(event.date)
    stringBuilder.appendLine()
    stringBuilder.append(event.price)
    stringBuilder.appendLine()
    stringBuilder.append(event.longitude)
    stringBuilder.appendLine()
    stringBuilder.append(event.latitude)
    return stringBuilder.toString()
}