package id.anantyan.challengechapter3.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.challengechapter3.common.Resource
import id.anantyan.challengechapter3.data.alphabet.AlphabetModel
import id.anantyan.challengechapter3.domain.base.BaseRepository
import id.anantyan.challengechapter3.domain.base.BaseRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository: BaseRepository by lazy { BaseRepositoryImpl() }

    private var _getAll = MutableLiveData<Resource<List<AlphabetModel>>>(Resource.Loading())
    private var _toggleGrid = MutableLiveData(false)
    private var _toggleSort = MutableLiveData(false)

    val getAll: LiveData<Resource<List<AlphabetModel>>> = _getAll
    val toggleGrid: LiveData<Boolean> = _toggleGrid
    val toggleSort: LiveData<Boolean> = _toggleSort

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
        _toggleGrid.value = !_toggleGrid.value!!
    }

    fun toggleSort() {
        _toggleSort.value = !_toggleSort.value!!
    }
}