package com.daftmobile.listtap.presentation.maincontent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daftmobile.listtap.data.ElementRepository
import com.daftmobile.listtap.data.model.Element
import com.daftmobile.listtap.presentation.Action
import com.daftmobile.listtap.presentation.maincontent.model.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainContentViewModel(
    private val itemRepository: ElementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun submitAction(action: Action) {
        viewModelScope.launch {
            when (action) {
                is Action.Start -> start()

                is Action.IncrementItemOnClick -> incrementItemOnClick(
                    item = action.item,
                    index = action.index
                )

                is Action.AppendElement -> viewModelScope.launch {
                    appendElement()
                }

                is Action.IncrementRandomElement -> incrementRandomElement()

                is Action.ResetRandomElementCounter -> resetRandomElementCounter()

                is Action.DeleteRandomElement -> deleteRandomElement()

                is Action.AddValueOfAnElementBefore -> addValueOfAnElementBefore()
            }
        }
    }

    private suspend fun start() {
        if (_uiState.value.isRunning) {
            _uiState.update {
                it.copy(isRunning = false)
            }
        } else {
            _uiState.update {
                it.copy(isRunning = true)
            }
            while (_uiState.value.isRunning) {
                tickTimer()
                delay(1000)
            }
        }
    }

    private fun tickTimer() {
        if (_uiState.value.elements.size < 5) {
            submitAction(Action.AppendElement)
        } else {
            val chance = (1..100).random()
            when (chance) {
                in 1..50 -> {
                    submitAction(Action.IncrementRandomElement)
                }

                in 51..80 -> {
                    submitAction(Action.ResetRandomElementCounter)
                }

                in 81..90 -> {
                    submitAction(Action.DeleteRandomElement)
                }

                in 91..100 -> {
                    submitAction(Action.AddValueOfAnElementBefore)
                }
            }
        }
    }

    private fun incrementItemOnClick(item: Element, index: Int) {
        // Question: what should happen if user clicks on the first item?
        // For now, let's just return (do nothing)

        if (index < 1) return

        val prevItem = _uiState.value.elements[index - 1]

        val newItem = item.copy(value = item.value + prevItem.value)

        val newItems = _uiState.value.elements.toMutableList()
        newItems[index] = newItem

        _uiState.update {
            it.copy(elements = newItems)
        }
    }

    private suspend fun appendElement() {
        showHint("appendElement")
        val item = itemRepository.getRandomElement()
        val newItems = _uiState.value.elements.toMutableList()
        newItems.add(item)

        _uiState.update { it.copy(elements = newItems) }
    }

    private suspend fun incrementRandomElement() {
        val maxIndex = _uiState.value.elements.size - 1
        val randomIndex = (0..maxIndex).random()
        showHint("incrementRandomElement(index = $randomIndex)")

        val item = _uiState.value.elements[randomIndex]
        val newItem = item.copy(value = item.value + 1)

        val newItems = _uiState.value.elements.toMutableList()
        newItems[randomIndex] = newItem

        _uiState.update {
            it.copy(elements = newItems)
        }
    }

    private suspend fun resetRandomElementCounter() {
        val maxIndex = _uiState.value.elements.size - 1
        val randomIndex = (0..maxIndex).random()
        showHint("resetRandomElementCounter(index = $randomIndex)")

        val item = _uiState.value.elements[randomIndex]
        val newItem = item.copy(value = 0)

        val newItems = _uiState.value.elements.toMutableList()
        newItems[randomIndex] = newItem

        _uiState.update {
            it.copy(elements = newItems)
        }
    }

    private suspend fun addValueOfAnElementBefore() {
        // Question: what should happen if randomIndex is 0?
        // For now, let's just make range from 1 to maxIndex
        val maxIndex = _uiState.value.elements.size - 1
        val randomIndex = (1..maxIndex).random()
        showHint("addValueOfAnElementBefore(index = $randomIndex)")

        val item = _uiState.value.elements[randomIndex]

        incrementItemOnClick(item, randomIndex)
    }

    private suspend fun deleteRandomElement() {
        val maxIndex = _uiState.value.elements.size - 1
        val randomIndex = (0..maxIndex).random()
        showHint("deleteRandomElement(index = $randomIndex)")

        val newItems = _uiState.value.elements.toMutableList()
        newItems.removeAt(randomIndex)

        _uiState.update {
            it.copy(elements = newItems)
        }
    }

    private suspend fun showHint(hint: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(currentAction = hint)
            }
            delay(700)
            _uiState.update {
                it.copy(currentAction = null)
            }
        }
    }

}