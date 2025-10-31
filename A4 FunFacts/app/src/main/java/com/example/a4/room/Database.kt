package com.example.a4.room

import androidx.room.Database
import androidx.room.RoomDatabase

//this is a DB, we have 1 entity (so we'll get 1 table in SQLite)
//the version stuff is for managing DB migrations
@Database(entities= [FunFactData::class], version = 1, exportSchema = false)
//This lets use have an entity with a "Date" in it which Room won't natively support
abstract class FunFactDatabase : RoomDatabase(){
    abstract fun funFactDao(): CourseDAO
}
