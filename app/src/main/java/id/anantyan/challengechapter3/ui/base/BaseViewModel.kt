package id.anantyan.challengechapter3.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class BaseViewModel : ViewModel() {
    private val _title = MutableSharedFlow<String>()

    val title: SharedFlow<String> = _title

    fun title(newTitle: String) = viewModelScope.launch {
        _title.emit(newTitle)
    }
}