package id.anantyan.challengechapter3.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.challengechapter3.model.AlphabetModel
import id.anantyan.challengechapter3.repository.BaseRepository
import id.anantyan.challengechapter3.repository.BaseRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository: BaseRepository by lazy { BaseRepositoryImpl() }
    private var _getAll = MutableStateFlow(listOf<AlphabetModel>())
    private var _toggleGrid = MutableStateFlow(false)
    private var _toggleSort = MutableStateFlow(false)

    val getAll: StateFlow<List<AlphabetModel>> = _getAll.asStateFlow()
    val toggleGrid: StateFlow<Boolean> = _toggleGrid.asStateFlow()
    val toggleSort: StateFlow<Boolean> = _toggleSort.asStateFlow()

    fun getAll(sort: Boolean) {
        if (sort) {
            _getAll.value = repository.listAlphabet().sortedByDescending { it.key }
        } else {
            _getAll.value = repository.listAlphabet().sortedBy { it.key }
        }
    }

    fun toggleGrid() {
        _toggleGrid.value = !_toggleGrid.value
    }

    fun toggleSort() {
        _toggleSort.value = !_toggleSort.value
    }
}