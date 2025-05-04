package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dependency_injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.AuthAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.MapAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.MomentAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.SightAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.UserAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.Sight
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.AuthRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoriteSightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MapRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MomentRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.UserRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock.FavoriteSightsRepositoryMock
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock.MapRepositoryMock
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock.MomentRepositoryMock
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock.SightRepositoryMock
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit.AuthRepositoryRetrofit
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit.MapRepositoryRetrofit
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit.MomentRepositoryRetrofit
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit.SightRepositoryRetrofit
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit.UserRepositoryRetrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val RETROFIT_BASE_URL = "http://192.168.1.84:8080"
    //<--------------REPOSITORIES------------->
    @Provides
    @Singleton
    fun provideSightRepository(): SightRepository {
        return SightRepositoryRetrofit(provideSightAPI())
    }

    @Provides
    @Singleton
    fun provideFavoriteSightRepository(): FavoriteSightRepository {
        return FavoriteSightsRepositoryMock()
    }


    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryRetrofit(provideAuthAPI())
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryRetrofit(provideUserAPI())
    }

    @Provides
    @Singleton
    fun provideMomentRepository(): MomentRepository {
        return MomentRepositoryRetrofit(provideMomentAPI())
    }

    @Provides
    @Singleton
    fun MapRepository(): MapRepository {
        return MapRepositoryRetrofit(provideMapAPI())
    }
    //<----------------APIs--------------->

    @Provides
    @Singleton
    fun provideAuthAPI(): AuthAPI {
        var retrofit =
            Retrofit.Builder().baseUrl("$RETROFIT_BASE_URL/api/auth/")
                .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
        return retrofit.create(AuthAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideUserAPI(): UserAPI {
        var retrofit = Retrofit.Builder().baseUrl("$RETROFIT_BASE_URL/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideSightAPI(): SightAPI{
        var retrofit = Retrofit.Builder().baseUrl("$RETROFIT_BASE_URL/api/tour-sight/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(SightAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMomentAPI(): MomentAPI{
        var retrofit = Retrofit.Builder().baseUrl("$RETROFIT_BASE_URL/api/tour-sight/moments/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(MomentAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMapAPI(): MapAPI {
        var retrofit = Retrofit.Builder().baseUrl("$RETROFIT_BASE_URL/api/map/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(MapAPI::class.java)    }

    //<------------------OTHER--------------->
    @Provides
    @Singleton
    fun provideDatastoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}