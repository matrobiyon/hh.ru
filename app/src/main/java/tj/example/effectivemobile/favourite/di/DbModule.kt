package tj.example.effectivemobile.favourite.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.example.effectivemobile.favourite.data.local.Converter
import tj.example.effectivemobile.favourite.data.local.MainDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDb(app: Application): MainDB =
        Room.databaseBuilder(app, MainDB::class.java, "main_db")
            .addTypeConverter(Converter())
            .build()

}