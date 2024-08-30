package tj.example.effectivemobile.favourite.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tj.example.effectivemobile.favourite.data.local.entity.OfferEntity
import tj.example.effectivemobile.favourite.data.local.entity.VacancyEntity
import tj.example.effectivemobile.search.data.remote.models.Vacancy

@Database(entities = [OfferEntity::class, VacancyEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MainDB : RoomDatabase() {

    abstract fun getOfferDao() : OfferDao

    abstract fun getVacancyDai() : VacancyDao
}