package com.example.labo_2

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PersonFormUiState (
    val person: Person? = null,
)

class PersonFormViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(PersonFormUiState())
    val uiState: StateFlow<PersonFormUiState> = _uiState.asStateFlow()

    fun registerPerson(person: Person) {
        _uiState.value = PersonFormUiState(person)
    }

}