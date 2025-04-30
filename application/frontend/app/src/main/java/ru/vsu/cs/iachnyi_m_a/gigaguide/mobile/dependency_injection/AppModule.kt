package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dependency_injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.AuthAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.UserAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.AuthRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoriteSightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.RouteRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightThumbnailRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.UserRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock.FavoriteSightsRepositoryMock
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock.RouteRepositoryMock
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock.SightRepositoryMock
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock.SightThumbnailRepositoryMock
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit.AuthRepositoryRetrofit
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit.UserRepositoryRetrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSightThumbnailRepository(): SightThumbnailRepository {
        return SightThumbnailRepositoryMock()
    }

    @Provides
    @Singleton
    fun provideSightRepository(): SightRepository {
        return SightRepositoryMock()
    }

    @Provides
    @Singleton
    fun provideFavoriteSightRepository(): FavoriteSightRepository {
        return FavoriteSightsRepositoryMock()
    }

    @Provides
    @Singleton
    fun provideSightRouteRepository(): RouteRepository {
        return RouteRepositoryMock()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryRetrofit(provideAuthAPI())
    }

    @Provides
    @Singleton
    fun provideAuthAPI(): AuthAPI {
        var retrofit =
            Retrofit.Builder().baseUrl("http://192.168.1.84:8081/api/auth/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
        return retrofit.create(AuthAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideUserAPI(): UserAPI {
        var retrofit = Retrofit.Builder().baseUrl("http://192.168.1.84:8084/api/user/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryRetrofit(provideUserAPI())
    }

    @Provides
    @Singleton
    fun provideDatastoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}