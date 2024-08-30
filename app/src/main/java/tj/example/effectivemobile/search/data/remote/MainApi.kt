package tj.example.effectivemobile.search.data.remote.models

import retrofit2.Response
import retrofit2.http.GET


interface MainApi {

    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r")
    suspend fun getData() : Response<Result>

}