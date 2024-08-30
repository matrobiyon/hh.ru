package tj.example.effectivemobile.search.data.repository

import kotlinx.coroutines.flow.Flow
import tj.example.effectivemobile.core.Resource
import tj.example.effectivemobile.core.callGenericRequest
import tj.example.effectivemobile.favourite.data.local.OfferDao
import tj.example.effectivemobile.favourite.data.local.VacancyDao
import tj.example.effectivemobile.search.data.remote.models.MainApi
import tj.example.effectivemobile.search.data.remote.models.Offer
import tj.example.effectivemobile.search.data.remote.models.Result
import tj.example.effectivemobile.search.data.remote.models.Vacancy
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: MainApi,
    private val offerDao: OfferDao,
    private val vacancyDao: VacancyDao
) {

    suspend fun getDataRemotely(): Flow<Resource<Result?>> =
        callGenericRequest {
            api.getData()
        }

    suspend fun getOffersLocally(): List<Offer> {
        return offerDao.getData()
    }

    suspend fun getVacanciesLocally() : List<Vacancy>{
        return vacancyDao.getVacancies()
    }

    suspend fun saveOffersLocally(list: List<Offer?>) {
        if (list.isNotEmpty()) offerDao.saveOffers(list.map { it!! })
    }

    suspend fun saveVacancyLocally(list: List<Vacancy?>) {
        if (list.isNotEmpty()) vacancyDao.saveVacancies(list.map { it!! })
    }

}