package com.sanisamoj.data.models.interfaces

import com.sanisamoj.data.models.dataclass.ResultsApiResponse

interface DatabaseRepository {
    suspend fun register(result: ResultsApiResponse) : ResultsApiResponse
    suspend fun getAllResults(loteria: String): List<ResultsApiResponse>
    suspend fun getByConc(loteria: String, conc: Int) : ResultsApiResponse
    suspend fun getLatest(loteria: String): ResultsApiResponse
}