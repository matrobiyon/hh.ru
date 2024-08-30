package tj.example.effectivemobile.search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tj.example.effectivemobile.favourite.data.local.MainDB
import tj.example.effectivemobile.search.data.remote.models.MainApi
import tj.example.effectivemobile.search.data.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SearchModule {

    private val BASE_URL = "https://drive.usercontent.google.com/"


    @Inject
    lateinit var db: MainDB

    @Provides
    @Singleton
    fun provideApi(): MainApi {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: MainApi, db: MainDB): MainRepository =
        MainRepository(api, db.getOfferDao(), db.getVacancyDai())

}