package com.example.a4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a4.room.FunFactData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class FactViewModel(private val repository: Repository) : ViewModel() {

    // Repo list variable all facts
    val allFacts: StateFlow<List<FunFactData>> = repository.allFacts.stateIn(
        scope = repository.scope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = listOf() //start with an empty list
    )

    fun addFact(){
        repository.addFact()
    }
}

//new version using the "view model factory DSL"
object WeatherViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            FactViewModel(
                //fetches the application singleton
                (this[AndroidViewModelFactory.APPLICATION_KEY]
                        //and then extracts the repository in it
                        as FactApp).repository
            )
        }
    }
}