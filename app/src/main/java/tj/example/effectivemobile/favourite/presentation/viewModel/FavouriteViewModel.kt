package tj.example.effectivemobile.favourite.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tj.example.effectivemobile.search.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getFavourites() = repository.getVacanciesLocally()

    fun changeLike(status: Boolean, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStatus(prevStatus = status, id = id)
        }
    }
}