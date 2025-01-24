package tawuniya.challenge.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tawuniya.challenge.data.dataSource.local.LocalStorageImpl
import tawuniya.challenge.data.dataSource.remote.ApiService
import tawuniya.challenge.data.mappers.UserDataMapper
import tawuniya.challenge.data.repository.UserRepositoryImpl
import tawuniya.challenge.domain.repository.UserRepository
import tawuniya.challenge.domain.repository.UserStorage
import tawuniya.challenge.domain.useCase.FetchUserDataUseCase
import tawuniya.challenge.domain.useCase.LikeUserUseCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideUserStorage(@ApplicationContext context: Context): UserStorage {
        return LocalStorageImpl(context)
    }

    @Provides
    fun provideUserMapper(): UserDataMapper {
        return UserDataMapper()
    }

    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        userStorage: UserStorage,
        userDataMapper: UserDataMapper
    ): UserRepository {
        return UserRepositoryImpl(apiService, userStorage, userDataMapper)
    }

    @Provides
    fun provideFetchUserDataUseCase(userRepository: UserRepository): FetchUserDataUseCase {
        return FetchUserDataUseCase(userRepository)
    }

    @Provides
    fun provideLikeUserUseCase(userRepository: UserRepository): LikeUserUseCase {
        return LikeUserUseCase(userRepository)
    }
}
