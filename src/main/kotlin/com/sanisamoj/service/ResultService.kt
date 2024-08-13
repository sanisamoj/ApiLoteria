package com.sanisamoj.service

import com.sanisamoj.config.GlobalContext
import com.sanisamoj.data.models.dataclass.ResultsApiResponse
import com.sanisamoj.data.models.interfaces.DatabaseRepository

class ResultService(
    private val repository: DatabaseRepository = GlobalContext.databaseRepository
) {

    suspend fun register(result: ResultsApiResponse) {
        repository.register(result)
    }

    suspend fun returnAll(loteria: String): List<ResultsApiResponse> {
        return repository.getAllResults(loteria)
    }

    suspend fun returnByConc(loteria: String, conc: Int): ResultsApiResponse? {
        return repository.getByConc(loteria, conc)
    }

    suspend fun returnLatest(loteria: String): ResultsApiResponse {
        return repository.getLatest(loteria)
    }
}