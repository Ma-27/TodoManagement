package com.example.todomanagement.util

data class DateTimeHolder(
        var date: Long = System.currentTimeMillis(),
        var hour: Int = 0,
        var minute: Int = 0
)
