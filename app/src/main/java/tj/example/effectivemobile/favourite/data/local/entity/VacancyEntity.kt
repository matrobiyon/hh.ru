package tj.example.effectivemobile.favourite.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import tj.example.effectivemobile.search.data.remote.models.Address
import tj.example.effectivemobile.search.data.remote.models.Experience
import tj.example.effectivemobile.search.data.remote.models.Salary

@Entity("vacancy")
data class VacancyEntity(
    val address: Address,
    val appliedNumber: Int,
    val company: String,
    val description: String?,
    val experience: Experience,
    @PrimaryKey
    val id: String,
    val isFavorite: Boolean = false,
    val lookingNumber: Int,
    val publishedDate: String,
    val questions: List<String>,
    val responsibilities: String,
    val salary: Salary,
    val schedules: List<String>,
    val title: String,
)