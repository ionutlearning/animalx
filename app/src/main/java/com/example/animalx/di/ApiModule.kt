package com.example.animalx.di

import android.content.Context
import android.content.SharedPreferences
import com.example.animalx.data.OAuthInterceptor
import com.example.animalx.data.service.AnimalsService
import com.example.animalx.data.service.TokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val URL = "https://api.petfinder.com/v2/"

    @Provides
    @Singleton
    fun getClient(interceptor: OAuthInterceptor) =
        OkHttpClient
            .Builder()
            .dispatcher(Dispatcher().apply { maxRequests = 5 })
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun getPetFinderService(
        client: OkHttpClient
    ): AnimalsService {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimalsService::class.java)

    }

    @Provides
    @Singleton
    fun getTokenService(): TokenService {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenService::class.java)
    }

    @Provides
    @Singleton
    fun getSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("token", Context.MODE_PRIVATE)
}