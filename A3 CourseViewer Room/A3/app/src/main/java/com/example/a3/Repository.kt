package com.example.a3

import android.util.Log
import com.example.a3.room.CourseDAO
import com.example.a3.room.CourseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Repository(val scope: CoroutineScope,
                        private val dao: CourseDAO
) {

    //updated with the DB is modified
    val allCourses = dao.allCourses()

    //Make a "web request", then insert it into the DB, triggering the above to update
    fun addCourse(department: String, number: String, location: String){
        scope.launch {
            Log.e("REPO", "Adding Class $department $number to courses")
            //delay(1000) // pretend this is a slow network call
            val curCourse = CourseData(department, number, location)

            //add course to the database
            dao.addCourse(curCourse)
            Log.e("REPO", "told the DAO")
        }
    }

    fun deleteCourse(courseId: Int) {
        scope.launch {
            Log.e("REPO", "Removing course $courseId from courses")

            //Delete course with given id from the database table
            dao.deleteCourse(courseId)
            Log.e("REPO", "told the DAO")
        }
    }

    fun updateCourse(id: Int, department: String, number: String, location: String){
        scope.launch {
            Log.e("REPO", "Updating Class $department $number")

            dao.updateCourse(id, department, number, location)
            Log.e("REPO", "told the DAO")
        }
    }

}