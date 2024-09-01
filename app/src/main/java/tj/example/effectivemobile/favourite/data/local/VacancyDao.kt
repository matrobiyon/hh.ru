package tj.example.effectivemobile.favourite.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tj.example.effectivemobile.favourite.data.local.entity.VacancyEntity
import tj.example.effectivemobile.search.data.remote.models.Vacancy
import tj.example.effectivemobile.search.data.remote.models.VacancyModel

@Dao
interface VacancyDao {

    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVacancies(list: List<Vacancy>)

    @Query("select * from vacancy")
    suspend fun getVacancies() : List<Vacancy>

    @Query("SELECT * FROM vacancy where id = :id")
    suspend fun getVacanciesById(id : String): List<Vacancy>

    @Query("UPDATE vacancy SET isFavorite =:prevStatus WHERE id = :id")
    fun changeStatus(prevStatus: Boolean, id: String)

}