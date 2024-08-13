package com.sanisamoj.service

import com.sanisamoj.utils.analyzers.findMissingNumber
import com.sanisamoj.config.GlobalContext
import com.sanisamoj.data.models.dataclass.ResultsApiResponse
import com.sanisamoj.data.models.enums.Games
import com.sanisamoj.data.models.interfaces.CaixaRepository
import com.sanisamoj.data.models.interfaces.DatabaseRepository
import com.sanisamoj.api.LoteriaApi
import retrofit2.HttpException

class UpdateResultService(
    private val databaseRepository: DatabaseRepository = GlobalContext.databaseRepository,
    private val apiRepository: CaixaRepository = GlobalContext.apiRepository
) {
    @OptIn(ExperimentalStdlibApi::class)
    suspend fun verifyAllResultsAndUpdate() {
        val games : Array<Games> = Games.entries.toTypedArray()
        games.forEach {
            val missingNumbers = getMissingResultsNumber(it.name)
            updateMissingResults(it.name, missingNumbers)
        }
    }

    suspend fun updateMissingResults(loteria: String, missingNumbers: List<Int>? = null) {
        val numbers = missingNumbers ?: getMissingResultsNumber(loteria)
        numbers.forEach { missingNumber ->
            try {
                val result: ResultsApiResponse = LoteriaApi.retrofitService.getGameResult(loteria, missingNumber.toString())
                databaseRepository.register(result)
                println("Número $missingNumber foi registrado / $loteria ")

            } catch (e: HttpException) {
                println("Número $missingNumber não foi registrado / $loteria ")
                println("Error: ${e.message}")
            }
        }
    }

    private suspend fun getMissingResultsNumber(game: String): List<Int> {
        val allResults: List<ResultsApiResponse> = databaseRepository.getAllResults(game).toList()
        val allGamesNumbers: List<Int> = allResults.map { it.numero }

        val lastConc: Int

        try {
            lastConc = apiRepository.getLatestResult(game).numero
        } catch (e: Exception) {
            throw Exception("Last game is null error")
        }

        val missingNumbers: List<Int> = findMissingNumber(allGamesNumbers, lastConc)
        return missingNumbers
    }
}