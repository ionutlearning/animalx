package com.example.animalx.data.service

import com.example.animalx.data.dto.TokenDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenService {

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getToken(
        @Field("grant_type") type: String = "client_credentials",
        @Field("client_id") key: String = "8FvB92COL3loJkRHBozGPLOVKZTG4CgXal6Dou6EjsH5lj2SXB",
        @Field("client_secret") secret: String = "zcYSA3CrhG6yW1dc539o8rAVgj7ecwLUaYHTSe3s"
    ): Response<TokenDto>

}