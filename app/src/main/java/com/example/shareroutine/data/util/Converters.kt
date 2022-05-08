package com.example.shareroutine.data.util

import androidx.room.TypeConverter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class Converters {
    @TypeConverter
    fun timestampToDate(value: Long?): ZonedDateTime? {
        return value?.let { Instant.ofEpochMilli(it).atZone(ZoneId.of("Asia/Seoul")) }
    }

    @TypeConverter
    fun dateToTimestamp(zonedDateTime: ZonedDateTime?): Long? {
        return zonedDateTime?.toInstant()?.toEpochMilli()
    }
}