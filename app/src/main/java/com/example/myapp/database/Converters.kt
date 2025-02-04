package com.example.myapp.database

import androidx.room.TypeConverter
import com.example.myapp.Priority
import java.util.Date
import java.util.UUID

class Converters {
    @TypeConverter
    fun fromUUID(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun toUUID(string: String): UUID = UUID.fromString(string)

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(time: Long): Date = Date(time)

    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(value: String): Priority = enumValueOf(value)
}