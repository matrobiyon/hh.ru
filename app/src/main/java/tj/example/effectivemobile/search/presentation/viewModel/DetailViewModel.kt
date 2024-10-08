package tj.example.effectivemobile.search.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tj.example.effectivemobile.search.data.remote.models.Vacancy
import tj.example.effectivemobile.search.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getDataById(id: String): LiveData<List<Vacancy>> {
        return repository.getDataById(id)
    }

    fun changeStatus(status: Boolean, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStatus(prevStatus = status, id = id)
        }
    }

}