package com.example.a3.room

import androidx.room.Entity
import androidx.room.PrimaryKey

//Defines a SQLITE table, basically
@Entity(tableName="courses")
data class CourseData(
    var department: String,
    var number: String,
    var location: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 // integer primary key for the DB

    fun displayName(): String {
        return "${department.trim()} ${number.trim()}"
    }
}
