package com.sanisamoj.data.models.interfaces

import com.sanisamoj.data.models.dataclass.ResultsApiResponse

interface CaixaRepository {
    suspend fun getResult(loteria: String, conc: Int): ResultsApiResponse
    suspend fun getLatestResult(loteria: String): ResultsApiResponse
}