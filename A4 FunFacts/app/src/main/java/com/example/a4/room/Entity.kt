package com.example.a4.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

//Defines a SQLITE table, basically
@Entity(tableName="funFacts")
@Serializable
data class FunFactData(
    var text: String,
    var source_url:String?=null){
    @PrimaryKey(autoGenerate = true)
    @Transient var id: Int = 0 // integer primary key for the DB
}
