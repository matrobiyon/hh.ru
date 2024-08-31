package tj.example.effectivemobile.search.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tj.example.effectivemobile.search.data.remote.models.Vacancy
import tj.example.effectivemobile.search.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    var vacancy = MutableLiveData<Vacancy>()
        private set

    fun getDataById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.getDataById(id)
            withContext(Dispatchers.Main){
                if (res.isNotEmpty()) vacancy.value = res.first()
            }
        }

    }

}