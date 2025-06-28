package com.example.weatherself.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Double.toCelsius(): Int = this.toInt()

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }
}