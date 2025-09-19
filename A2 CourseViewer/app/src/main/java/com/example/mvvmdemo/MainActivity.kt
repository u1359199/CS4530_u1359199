package com.example.mvvmdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.compose.AppTheme

// Course with department, number and location.
// Has display name function and ID so courses are easier to work with
data class Course( val id: Int,
              val department: String,
              val number: String,
              val location: String) {
    fun displayName(): String {
        return "${department.trim()} ${number.trim()}"
    }
}

// Course List. Makes adding, removing, and updating courses easy.
// Simple as adding a course to a list
class CourseCatalog {
    private val _courses = mutableStateListOf<Course>()
    val courses: List<Course> get() = _courses
    private var prevID = 0
    private fun nextID() = ++prevID

    // Add course. Fill in course data
    fun addCourse(department: String, number: String, location: String): Course {
        val course = Course(id = nextID(), department = department, number = number, location = location)
        _courses.add(course)
        return course
    }

    // Edit course data
    fun editCourse(course: Course) {
        val idx = _courses.indexOfFirst {it.id == course.id}
        if (idx >= 0) _courses[idx] = course
    }

    // Delete a course from the list by id
    fun deleteCourse(id: Int) {
        _courses.removeIf {it.id == id}
    }
}

// The view model class that takes in a course list
// Contains view model course list functions
class CourseViewModel(private val catalog: CourseCatalog) : ViewModel()
{
    // model which is my data
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses
    //val tasksReadOnly : StateFlow<List<String>> = taskMutable

    // my methods that will update the model
    fun addCourse(dept: String, num: String, loc: String) {
        catalog.addCourse(dept, num, loc)
        _courses.value = catalog.courses
    }

    fun editCourse(course: Course) {
        catalog.editCourse(course)
        _courses.value = catalog.courses
    }

    fun deleteCourse(id: Int) {
        catalog.deleteCourse(id)
        _courses.value = catalog.courses
    }
}

class CourseViewModelFactory(private val catalog: CourseCatalog) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            @Suppress("UncheckedCat")
            return CourseViewModel(catalog) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

// Main activity that initializes the main screen
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    private val catalog = CourseCatalog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
               val vm:CourseViewModel = viewModel(factory = CourseViewModelFactory(catalog))
               MainScreen(vm)
            }
        }
    }
}

// Composable function that builds a simple screen with text fields and a lazy column list
@ExperimentalMaterial3Api
@Composable
fun MainScreen(viewModel: CourseViewModel) {
    var department by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var editingId by remember { mutableStateOf<Int?>(null) }
    var expandedCourseId by remember { mutableStateOf<Int?>(null) }

    val courses by viewModel.courses.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Edit the course list with Text fields
        Text(
            if (editingId == null) "Add Course" else "Edit Course",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = department,
            onValueChange = { department = it },
            label = { Text("Department") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Course Number") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Button(onClick = {
                // Cancel editing field
                department = ""
                number = ""
                location = ""
                editingId = null
            }, modifier = Modifier.weight(1f)) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = {
                if (department.isBlank() || number.isBlank()) return@Button
                if (editingId == null) {
                    viewModel.addCourse(department.trim(), number.trim(), location.trim())
                } else {
                    viewModel.editCourse(
                        Course(editingId!!, department.trim(), number.trim(), location.trim())
                    )
                }
                department = ""
                number = ""
                location = ""
                editingId = null
            }, modifier = Modifier.weight(1f)) {
                Text(if (editingId == null) "Add Course" else "Save Changes")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        // Course catalog list
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(courses, key = { it.id }) { course ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color(0xFFF0F0F0))
                    .padding(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(course.displayName(), fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Row {
                            // Expanded details on course
                            Button(onClick = {
                                expandedCourseId = if (expandedCourseId == course.id) null else course.id
                            }) { Text("View") }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                editingId = course.id
                                department = course.department
                                number = course.number
                                location = course.location
                            }) { Text("Edit") }
                        }
                    }

                    if (expandedCourseId == course.id) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Department: ${course.department}")
                        Text("Number: ${course.number}")
                        Text("Location: ${course.location}")
                        Spacer(modifier = Modifier.height(4.dp))
                        // Delete course button inside of view course details.
                        Button(onClick = { viewModel.deleteCourse(course.id) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}