package com.example.a4

import android.app.Application
import androidx.room.Room
import com.example.a4.room.FunFactDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json

class FactApp: Application() {
val scope =CoroutineScope(SupervisorJob())

    // Fact Database
    val db by lazy {
        Room.databaseBuilder(
                applicationContext,
            FunFactDatabase::class.java,
            "myDB"
                ).build()
    }

    // http client
    val client by lazy {
        HttpClient(Android)
        {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    // repo
    val repository by lazy { Repository(scope, db.funFactDao(), client) }

    // Database deletion
//    override fun onCreate() {
//        super.onCreate()
//        deleteDatabase("myDB")
//    }
}