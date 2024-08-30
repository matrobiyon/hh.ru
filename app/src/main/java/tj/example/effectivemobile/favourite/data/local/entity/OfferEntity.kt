package tj.example.effectivemobile.favourite.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import tj.example.effectivemobile.search.data.remote.models.Button

@Entity("offer")
data class OfferEntity(
    val button: Button?,
    val id: String?,
    val link: String,
    val title: String,
    @PrimaryKey(autoGenerate = true)
    val myId : Int? = null
)