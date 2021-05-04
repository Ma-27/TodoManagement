package com.example.todomanagement.util

class Converter {
    companion object {
        fun convertDateTimeToMillSec(date: Long, hour: Int, minute: Int): Long {
            //TODO debug发现东八区已自动算在内，故hour - 8，适配国内的时间规范
            //TODO 在current时就已经+8,对应8点
            val minuteSum: Long = (hour - 8).toLong() * 60 + minute.toLong()
            val secondsSum: Long = minuteSum * 60
            val millSec: Long = secondsSum * 1000
            return millSec + date
        }
    }
}