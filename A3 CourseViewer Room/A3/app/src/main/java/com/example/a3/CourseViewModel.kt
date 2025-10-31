package com.example.a3

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a3.room.CourseData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
//import java.util.Date


class CourseViewModel(private val repository: Repository) : ViewModel() {


    // Repo list variable all courses
    val allCourses: StateFlow<List<CourseData>> = repository.allCourses.stateIn(
        scope = repository.scope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = listOf() //start with an empty list
    )

    fun addClass(dep: String, num: String, loc: String){
        Log.e("VM", "Adding Class $dep $num")
        repository.addCourse(dep, num, loc)
    }

    fun deleteClass(courseId: Int){
        Log.e("VM", "Deleting Class $courseId")
        repository.deleteCourse(courseId)
    }

    fun updateClass(id: Int, dep: String, num: String, loc: String){
        Log.e("VM", "Adding Class $dep $num")
        repository.updateCourse(id, dep, num, loc)
    }



}

//new version using the "view model factory DSL"
object WeatherViewModelProvider {
    val Factory = viewModelFactory {
        // if you had different VM classes, you could add an initializer block for each one here
        initializer {
            CourseViewModel(
                //fetches the application singleton
                (this[AndroidViewModelFactory.APPLICATION_KEY]
                        //and then extracts the repository in it
                        as CourseApp).repository
            )
        }
    }
}