package com.example.a5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.a5.ui.theme.GyroscopeDemoTheme
import androidx.compose.runtime.LaunchedEffect

// Create view model and run the Marble Screen composable
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GyroscopeDemoTheme {
                val myVM: GravityViewModel = viewModel(factory = GravityViewModel.Factory)
                MarbleScreen(myVM)
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MarbleScreen(viewModel: GravityViewModel) {
    val x by viewModel.posXReadOnly.collectAsStateWithLifecycle()
    val y by viewModel.posYReadOnly.collectAsStateWithLifecycle()

    BoxWithConstraints(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        // Set screen boundaries with boxWithConstraints max width and height
        LaunchedEffect(maxWidth, maxHeight) {
            viewModel.screenBoundaries(maxWidth.value, maxHeight.value)
        }

        // The marble
        Box(
            modifier = Modifier.offset(x.dp, y.dp)
                               .size(40.dp).background(Color.Blue, CircleShape)
        )
    }
}
