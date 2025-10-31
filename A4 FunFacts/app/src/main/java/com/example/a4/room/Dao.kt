package com.example.a4.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDAO {

    //marked as suspend so the thread can yield in case the DB update is slow
    // Inserts a new fact into the "facts" table.
    @Insert
    suspend fun addFunFact(data: FunFactData)

    // Retrieves *all* facts from the "facts" table, ordered by ascending id (newest last).
    @Query("SELECT * from funfacts ORDER BY id ASC")
    fun allFacts() : Flow<List<FunFactData>>

}