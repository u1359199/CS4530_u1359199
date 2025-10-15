package com.example.a3

import android.app.Application
import androidx.room.Room
import com.example.a3.room.CourseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CourseApp: Application() {
val scope =CoroutineScope(SupervisorJob())
    val db by lazy {
        Room.databaseBuilder(
                applicationContext,
            CourseDatabase::class.java,
            "myDB"
                ).build()
    }

    val repository by lazy { Repository(scope, db.courseDao()) }

}