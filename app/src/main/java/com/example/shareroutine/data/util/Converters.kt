package com.example.shareroutine.data.util

import androidx.room.TypeConverter
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField

class Converters {
    @TypeConverter
    fun timestampToDate(value: Long?): ZonedDateTime? {
        return value?.let { Instant.ofEpochMilli(it).atZone(ZoneId.of("Asia/Seoul")) }
    }

    @TypeConverter
    fun dateToTimestamp(zonedDateTime: ZonedDateTime?): Long? {
        return zonedDateTime?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun intToDayOfWeek(value: Int?): DayOfWeek? {
        return value?.let { DayOfWeek.of(it) }
    }

    @TypeConverter
    fun dayOfWeekToInt(dayOfWeek: DayOfWeek?): Int? {
        return dayOfWeek?.value
    }

    @TypeConverter
    fun timestampToLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(value) }
    }

    @TypeConverter
    fun localTimeToTimestamp(localTime: LocalTime?): String? {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return localTime?.format(formatter)
    }

    @TypeConverter
    fun intToMonth(value: Int?): Month? {
        return value?.let { Month.of(value) }
    }

    @TypeConverter
    fun monthToInt(month: Month?): Int? {
        return month?.value
    }
}