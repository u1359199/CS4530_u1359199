package com.example.a3.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDAO {

    //marked as suspend so the thread can yield in case the DB update is slow
    // Inserts a new course record into the "courses" table.
    @Insert
    suspend fun addCourse(data: CourseData)

    // Deletes a course from the "courses" table where the 'id' matches the given courseId.
    @Query("DELETE FROM courses WHERE id = :courseId")
    suspend fun deleteCourse(courseId: Int)

    // Retrieves a single course record by its unique ID.
    // Returns a Flow<CourseData?> so you can observe changes to this specific record in real time.
    @Query("SELECT * FROM courses WHERE id= :ID LIMIT 1")
    fun currCourse(ID: Int) : Flow<CourseData?>

    // Retrieves *all* courses from the "courses" table, ordered by descending id (newest first).
    @Query("SELECT * from courses ORDER BY id DESC")
    fun allCourses() : Flow<List<CourseData>>

    // Updates a specific course’s department, number, and location fields where the id matches courseID.
    @Query("UPDATE courses SET department = :dep, number = :num, location = :loc WHERE id = :courseID")
    suspend fun updateCourse(courseID: Int, dep: String, num: String, loc: String)
}