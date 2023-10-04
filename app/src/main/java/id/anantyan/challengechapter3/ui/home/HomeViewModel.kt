package id.anantyan.challengechapter3.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.challengechapter3.common.Resource
import id.anantyan.challengechapter3.model.AlphabetModel
import id.anantyan.challengechapter3.repository.BaseRepository
import id.anantyan.challengechapter3.repository.BaseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val repository: BaseRepository by lazy { BaseRepositoryImpl() }
    private var _getAll = MutableStateFlow<Resource<List<AlphabetModel>>>(Resource.Loading())
    private var _toggleGrid = MutableStateFlow(false)
    private var _toggleSort = MutableStateFlow(false)

    val getAll: StateFlow<Resource<List<AlphabetModel>>> = _getAll.asStateFlow()
    val toggleGrid: StateFlow<Boolean> = _toggleGrid.asStateFlow()
    val toggleSort: StateFlow<Boolean> = _toggleSort.asStateFlow()

    fun getAll(sort: Boolean) = viewModelScope.launch {
        _getAll.value = Resource.Loading()
        delay(600)

        val result = if (sort) {
            Resource.Success(repository.listAlphabet().sortedByDescending { it.key })
        } else {
            Resource.Success(repository.listAlphabet().sortedBy { it.key })
        }

        _getAll.value = result
    }

    fun toggleGrid() {
        _toggleGrid.value = !_toggleGrid.value
    }

    fun toggleSort() {
        _toggleSort.value = !_toggleSort.value
    }
}