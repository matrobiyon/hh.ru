package tj.example.effectivemobile.favourite.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tj.example.effectivemobile.favourite.data.local.entity.OfferEntity
import tj.example.effectivemobile.search.data.remote.models.Offer

@Dao
interface OfferDao {

    @Query("SELECT * FROM offer")
    suspend fun getData(): List<Offer>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = OfferEntity::class)
    suspend fun saveOffers(offer: List<Offer>)
}