package com.example.nativo

import retrofit2.Response
import retrofit2.http.GET

interface IbgeApiService {
    @GET("api/v1/localidades/estados?orderBy=nome") // Ordenando por nome para uma lista mais amigável
    suspend fun getEstados(): Response<List<EstadoIBGE>>
}