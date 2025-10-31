package com.example.a3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.a3.ui.theme.ComposeDEMOTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.a3.room.CourseData

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val vm:CourseViewModel by viewModels{ WeatherViewModelProvider.Factory}

        setContent {
            ComposeDEMOTheme {
               CourseScreen (vm)
            }
        }
    }
}

@Composable
fun CourseScreen(myVM: CourseViewModel) {
    Column(
        Modifier.fillMaxWidth().padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        val courses by myVM.allCourses.collectAsState(listOf())

        var depText by remember { mutableStateOf("") }
        var numText by remember { mutableStateOf("") }
        var locText by remember { mutableStateOf("") }
        var editingId by remember { mutableStateOf<Int?>(null) }
        var expandedCourseId by remember { mutableStateOf<Int?>(null) }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            Text(
                if (editingId == null) "Add Course" else "Edit Course",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Department text field
            OutlinedTextField(
                value = depText,
                onValueChange = { depText = it },
                label = { Text("Department") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Number text field
            OutlinedTextField(
                value = numText,
                onValueChange = { numText = it },
                label = { Text("Course Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Location text field
            OutlinedTextField(
                value = locText,
                onValueChange = { locText = it },
                label = { Text("Location") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Buttons
            Row {
                // Cancel Button
                Button(onClick = {
                    depText = ""
                    numText = ""
                    locText = ""
                    editingId = null
                }, modifier = Modifier.weight(1f)) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(12.dp))

                // Add Course Button
                Button(onClick = {
                    if (depText.isBlank() || numText.isBlank()) return@Button
                    if (editingId == null) {
                        myVM.addClass(depText.trim(), numText.trim(), locText.trim())
                    } else {
                        myVM.updateClass(
                            editingId!!,
                            depText.trim(),
                            numText.trim(),
                            locText.trim()
                        )
                    }
                    depText = ""
                    numText = ""
                    locText = ""
                    editingId = null
                }, modifier = Modifier.weight(1f)) {
                    Text(if (editingId == null) "Add Course" else "Save Changes")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider()

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                for (data in courses) {
                    item {
                        CourseDisplay(
                            data = data,
                            expandedId = expandedCourseId,
                            onExpand = { id:Int -> expandedCourseId = if (expandedCourseId == id) null else id },
                            onEdit = { course: CourseData ->
                                editingId = course.id
                                depText = course.department
                                numText = course.number
                                locText = course.location
                            },
                            onDelete = { id: Int -> myVM.deleteClass(id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CourseDisplay(
    data: CourseData?,
    expandedId: Int?,
    onExpand: (Int) -> Unit,
    onEdit: (CourseData) -> Unit,
    onDelete: (Int) -> Unit
) {

    Column(
        modifier = Modifier
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
            Text(
                data?.displayName().orEmpty(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Row {
                // Expanded details on course
                Button(onClick = {
                    data?.id?.let(onExpand)
                }) { Text("View") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    data?.let(onEdit)
                }) { Text("Edit") }
            }
        }

        if (expandedId == data?.id) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Department: ${data?.department}")
            Text("Number: ${data?.number}")
            Text("Location: ${data?.location}")
            Spacer(modifier = Modifier.height(4.dp))
            // Delete course button inside of view course details.
            Button(onClick = { data?.id?.let(onDelete) }) {
                Text("Delete")
            }
        }
    }
}
