package com.sanisamoj.data.models.interfaces

import com.sanisamoj.data.models.dataclass.ResultsApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LoteriaApiService {
    @GET("{loteria}/{conc}")
    suspend fun getGameResult(@Path("loteria") loteria: String, @Path("conc") conc: String): ResultsApiResponse

    @GET("{loteria}")
    suspend fun getLatestGameResult(@Path("loteria") loteria: String): ResultsApiResponse
}