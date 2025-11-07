package com.example.a5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GravityViewModel(private val repository: GravityRepository) : ViewModel() {

    // Marble position variables x and y positions
    private val posXMutable = MutableStateFlow(0f)
    val posXReadOnly : StateFlow<Float> = posXMutable

    private val posYMutable = MutableStateFlow(0f)
    val posYReadOnly : StateFlow<Float> = posYMutable

    // Marble velocity variables
    private var velX = 0f
    private var velY = 0f

    // Screen boundaries variables
    private var maxX = 0f
    private var maxY = 0f

    // time variable
    private var prevTime = System.nanoTime() / 1_000_000_000f

    // Reading initialization and collection
    val gravityReading = repository.getGravityFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GravityReading(0f, 0f, 0f)
        )

    init {
        viewModelScope.launch {
            gravityReading.collect { g-> updatePos() }
        }
    }

    // Set screen boundaries
    fun screenBoundaries(maxx: Float, maxy: Float) {
        maxX = maxx
        maxY = maxy
    }

    // Update the position of the model using the gravity reading, time, velocity, and friction
    // Keep the position of the marble within the bounds of the screen
    fun updatePos() {
        val time = System.nanoTime() / 1_000_000_000f
        val dt = time - prevTime
        prevTime = time

        // velocity calculation
        val scale = 30f
        velX += dt * scale * -gravityReading.value.x
        velY += dt * scale * gravityReading.value.y

        // account for friction (marble slows)
        val friction = .9f
        velX *= friction
        velY *= friction

        // Change marble position based on time and velocity with a scale factor
        posXMutable.value += dt* scale * velX
        posYMutable.value += dt * scale * velY

        // Keep the marble within the bounds of the screen using coerceInHelper
        val radius = 20f
        if (maxX <= 0f || maxY <= 0f) return
        posXMutable.value = posXMutable.value.coerceIn((-maxX/2) + radius, (maxX/2) - radius)
        posYMutable.value = posYMutable.value.coerceIn((-maxY/2) + radius, (maxY/2) - radius)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GravityApp)
                GravityViewModel(application.gravityRepository)
            }
        }
    }
}