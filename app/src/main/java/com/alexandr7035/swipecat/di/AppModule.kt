package com.alexandr7035.swipecat.di

import android.app.Application
import androidx.room.Room
import com.alexandr7035.swipecat.data.CatRemoteToLocalMapper
import com.alexandr7035.swipecat.data.Repository
import com.alexandr7035.swipecat.data.RepositoryImpl
import com.alexandr7035.swipecat.data.local.CatsDB
import com.alexandr7035.swipecat.data.local.LikedCatsDao
import com.alexandr7035.swipecat.data.remote.RandomCatProvider
import com.alexandr7035.swipecat.data.remote.RandomCatProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val DB_NAME = "swipe_cats.db"

    @Provides
    @Singleton
    fun provideRandomCatProvider(): RandomCatProvider {
        return RandomCatProviderImpl()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): CatsDB {
        return Room
            .databaseBuilder(application, CatsDB::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLikedCatsDao(db: CatsDB): LikedCatsDao {
        return db.getLikedCatsDao()
    }

    @Provides
    @Singleton
    fun provideMapper(): CatRemoteToLocalMapper {
        return CatRemoteToLocalMapper()
    }

    @Provides
    @Singleton
    fun provideRepository(
        catsProvider: RandomCatProvider,
        dao: LikedCatsDao,
        mapper: CatRemoteToLocalMapper): Repository {

        return RepositoryImpl(catsProvider, dao, mapper)
    }

}