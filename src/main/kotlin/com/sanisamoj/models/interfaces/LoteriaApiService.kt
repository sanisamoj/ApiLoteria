package com.sanisamoj.models.interfaces

import com.sanisamoj.models.generics.ResultsCaixa
import retrofit2.http.GET
import retrofit2.http.Path

interface LoteriaApiService {
    @GET("{loteria}/{conc}")
    suspend fun getGameResult(@Path("loteria") loteria: String, @Path("conc") conc: String): ResultsCaixa

    @GET("{loteria}")
    suspend fun getLatestGameResult(@Path("loteria") loteria: String): ResultsCaixa
}