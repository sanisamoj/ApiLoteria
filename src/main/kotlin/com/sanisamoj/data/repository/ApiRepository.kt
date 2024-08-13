package com.sanisamoj.data.repository

import com.sanisamoj.data.models.dataclass.ResultsApiResponse
import com.sanisamoj.data.models.interfaces.CaixaRepository
import com.sanisamoj.api.LoteriaApi

class ApiRepository: CaixaRepository {
    override suspend fun getResult(loteria: String, conc: Int): ResultsApiResponse {
        return LoteriaApi.retrofitService.getGameResult(loteria, conc.toString())
    }

    override suspend fun getLatestResult(loteria: String): ResultsApiResponse {
        return LoteriaApi.retrofitService.getLatestGameResult(loteria)
    }
}