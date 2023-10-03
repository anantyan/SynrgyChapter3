package id.anantyan.challengechapter3.ui.detail

import androidx.lifecycle.ViewModel
import id.anantyan.challengechapter3.model.WordsModel
import id.anantyan.challengechapter3.repository.BaseRepository
import id.anantyan.challengechapter3.repository.BaseRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel : ViewModel() {
    private val repository: BaseRepository by lazy { BaseRepositoryImpl() }
    private var _getAll = MutableStateFlow(listOf<WordsModel>())
    private var _toggleGrid = MutableStateFlow(false)
    private var _toggleSort = MutableStateFlow(false)

    val getAll: StateFlow<List<WordsModel>> = _getAll.asStateFlow()
    val toggleGrid: StateFlow<Boolean> = _toggleGrid.asStateFlow()
    val toggleSort: StateFlow<Boolean> = _toggleSort.asStateFlow()

    fun getAll(key:String?, sort: Boolean) {
        if (sort) {
            _getAll.value = repository.listWord(key ?: "").sortedByDescending { it.word }
        } else {
            _getAll.value = repository.listWord(key ?: "").sortedBy { it.word }
        }
    }

    fun toggleGrid() {
        _toggleGrid.value = !_toggleGrid.value
    }

    fun toggleSort() {
        _toggleSort.value = !_toggleSort.value
    }
}