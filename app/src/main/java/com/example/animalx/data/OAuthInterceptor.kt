package com.example.animalx.data

import android.content.SharedPreferences
import com.example.animalx.data.service.TokenService
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

@Singleton
class OAuthInterceptor @Inject constructor(
    private val tokenService: TokenService,
    private val tokenPreferences: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val token = tokenPreferences.getString(
                "token",
                null
            )
            val originalRequest = chain.request()
            val authenticationRequest = originalRequest.newBuilder().addHeader(
                "Authorization",
                "Bearer $token"
            ).build()

            val initialResponse = chain.proceed(authenticationRequest)

            return when (initialResponse.code) {
                401 -> {
                    val responseNewTokenLoginModel = runBlocking { tokenService.getToken() }

                    if (responseNewTokenLoginModel.isSuccessful) {
                        val newToken = responseNewTokenLoginModel.body()?.accessToken?.let { accessToken ->
                            tokenPreferences.edit().putString("token", accessToken).apply()
                            accessToken
                        }
                        val newAuthenticationRequest = originalRequest.newBuilder().addHeader(
                                "Authorization",
                                "Bearer $newToken"
                            ).build()
                        initialResponse.close()
                        chain.proceed(newAuthenticationRequest)
                    } else initialResponse
                }

                else -> initialResponse
            }
        }
    }
}