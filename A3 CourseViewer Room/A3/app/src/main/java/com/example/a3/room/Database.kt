package com.example.a3.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.a3.Converters

//this is a DB, we have 1 entity (so we'll get 1 table in SQLite)
//the version stuff is for managing DB migrations
@Database(entities= [CourseData::class], version = 1, exportSchema = false)
//This lets use have an entity with a "Date" in it which Room won't natively support
@TypeConverters(Converters::class)
abstract class CourseDatabase : RoomDatabase(){
    abstract fun courseDao(): CourseDAO
}
