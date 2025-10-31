package com.example.a4

import android.os.Bundle
import android.util.Log
import com.example.a4.room.CourseDAO
import com.example.a4.room.FunFactData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class Repository(val scope: CoroutineScope,
                 private val dao: CourseDAO,
                 private val client:HttpClient
) {

    //updated with the DB is modified
    val allFacts = dao.allFacts()

    // Add fact to the dao
    fun addFact (){
        scope.launch {
            try{
                val responseText: FunFactData = client.get("https://uselessfacts.jsph.pl//api/v2/facts/random").body()
                Log.e("REPO", "Adding fun fact: $responseText to funFacts")
                dao.addFunFact(responseText)
            }
            catch (e: Exception)
            {
                Log.e("FunFact Activity", "Error fetching", e)
            }
        }
    }

}