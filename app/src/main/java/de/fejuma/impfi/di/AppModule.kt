package de.fejuma.impfi.di


import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.fejuma.impfi.data.data_source.HighscoreDatabase
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.data.repository.RepositoryImpl
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideHighscoreDatabase(app: Application): HighscoreDatabase {
        return Room.databaseBuilder(
            app,
            HighscoreDatabase::class.java,
            HighscoreDatabase.DATABASE_NAME
        ).build()
    }


    @Singleton
    @Provides
    fun provideRepository(
        application: Application,
        highscoreDatabase: HighscoreDatabase
    ): Repository {
        return RepositoryImpl(application as Context, highscoreDatabase.highScoreDao)
    }


}