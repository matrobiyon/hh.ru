package tj.example.effectivemobile.search.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tj.example.effectivemobile.core.Resource
import tj.example.effectivemobile.search.data.remote.models.Offer
import tj.example.effectivemobile.search.data.remote.models.Vacancy
import tj.example.effectivemobile.search.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    var isLoading = MutableLiveData(false)
        private set

    var vacancies: List<Vacancy> = listOf()
        private set

    var isExpanded = MutableLiveData<Boolean>(null)

    fun getVacancies(): LiveData<List<Vacancy>> = repository.getVacanciesLocally()

    fun getOffer(): LiveData<List<Offer>> = repository.getOffersLocally()

    fun getDataRemotely() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getDataRemotely().collect { res ->
                when (res) {
                    is Resource.Success -> {
                        if (res.data?.offers != null && res.data.offers.isNotEmpty()) {
                            repository.saveOffersLocally(res.data.offers)
                        }
                        if (res.data?.vacancies != null) {
                            repository.saveVacancyLocally(res.data.vacancies)
                        }
                        isLoading.value = false
                    }

                    is Resource.Loading -> {
                        isLoading.value = true
                    }

                    is Resource.Error -> {
                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun changeStatus(prevStatus: Boolean, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStatus(prevStatus, id)
        }
    }

    fun saveVacanciesInViewModel(res: List<Vacancy>?) {
        if (res != null) vacancies = res
    }
}