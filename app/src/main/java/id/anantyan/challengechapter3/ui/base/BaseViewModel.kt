package id.anantyan.challengechapter3.ui.base

import androidx.lifecycle.ViewModel
import id.anantyan.challengechapter3.model.WordsModel
import id.anantyan.challengechapter3.repository.BaseRepository
import id.anantyan.challengechapter3.repository.BaseRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BaseViewModel : ViewModel() {
    private val repository: BaseRepository by lazy { BaseRepositoryImpl() }
    private var _getAll = MutableStateFlow(listOf<WordsModel>())

    val getAll: StateFlow<List<WordsModel>> = _getAll.asStateFlow()

    fun getAll(key:String?, sort: Boolean) {
        if (sort) {
            _getAll.value = repository.listWord(key ?: "").sortedByDescending { it.word }
        } else {
            _getAll.value = repository.listWord(key ?: "").sortedBy { it.word }
        }
    }
}