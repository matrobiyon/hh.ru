package tj.example.effectivemobile.search.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tj.example.effectivemobile.core.Resource
import tj.example.effectivemobile.search.data.remote.models.Offer
import tj.example.effectivemobile.search.data.remote.models.Vacancy
import tj.example.effectivemobile.search.data.repository.MainRepository
import tj.example.effectivemobile.search.presentation.adapter.VacanciesAdapter
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    var offers = MutableLiveData<List<Offer>>()
        private set

    var vacancies = MutableLiveData<List<Vacancy>>()
        private set

    var isLoading = MutableLiveData<Boolean>(false)
        private set

    var isExpanded = MutableLiveData<Boolean?>(null)

    init {
        getDataLocally()
    }

    fun getDataLocally() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.getOffersLocally()
            val resVacancy = repository.getVacanciesLocally()
            if (res.isEmpty()) {
                getDataRemotely()
            } else {
                withContext(Dispatchers.Main) {
                    offers.value = res
                    vacancies.value = resVacancy
                }
            }
        }
    }

    fun getDataRemotely() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getDataRemotely().collect { res ->
                when (res) {
                    is Resource.Success -> {
                        if (res.data?.offers != null && res.data.offers.isNotEmpty()) {
                            repository.saveOffersLocally(res.data.offers)
                            offers.value = res.data.offers.map { it!! }
                        }
                        if (res.data?.vacancies != null) {
                            repository.saveVacancyLocally(res.data.vacancies)
                            vacancies.value = res.data.vacancies.map { it!! }
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

     fun changeStatus(prevStatus : Boolean, id : String,) {
         viewModelScope.launch(Dispatchers.IO) {
            repository.changeStatus(prevStatus,id)

         }
    }
}