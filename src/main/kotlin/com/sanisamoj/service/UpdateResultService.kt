package com.sanisamoj.service

import com.example.demo.utils.findMissingNumber
import com.sanisamoj.GlobalContext
import com.sanisamoj.external.LoteriaApi
import com.sanisamoj.models.enums.Games
import com.sanisamoj.models.generics.ResultsCaixa
import com.sanisamoj.models.interfaces.CaixaRepository
import com.sanisamoj.models.interfaces.LoteriaRepository
import retrofit2.HttpException

class UpdateResultService(
    private val loteriaRepository: LoteriaRepository = GlobalContext.LoteriaRepository,
    private val caixaRepository: CaixaRepository = GlobalContext.caixaRepository
) {
    @OptIn(ExperimentalStdlibApi::class)
    suspend fun verifyAllResultsAndUpdate() {
        val games : Array<Games> = Games.entries.toTypedArray()
        games.forEach { it ->
            val missingNumbers = getMissingResultsNumber(it.name)
            updateMissingResults(it.name, missingNumbers)
        }
    }

    suspend fun updateMissingResults(loteria: String, missingNumbers: List<Int>? = null) {
        val numbers = missingNumbers ?: getMissingResultsNumber(loteria)
        numbers.forEach { missingNumber ->
            try {
                val result: ResultsCaixa = LoteriaApi.retrofitService.getGameResult(loteria, missingNumber.toString())
                loteriaRepository.register(result)
                println("Número $missingNumber foi registrado / $loteria ")

            } catch (e: HttpException) {
                println("Número $missingNumber não foi registrado / $loteria ")
                println("Error: ${e.message}")
            }
        }
    }

    // Verifica quais jogos não estão registrados e salvá-os
    private suspend fun getMissingResultsNumber(game: String): List<Int> {
        val allResults: List<ResultsCaixa> = loteriaRepository.getAllResults(game).toList()
        val allGamesNumbers: List<Int> = allResults.map { it.numero }

        // Armazena o número do último sorteio atualizado
        val lastConc: Int

        try {
            lastConc = caixaRepository.getLatestResult(game).numero
        } catch (e: Exception) {
            throw Exception("Last game is null error")
        }

        // Armazena a array com os números faltantes
        val missingNumbers: List<Int> = findMissingNumber(allGamesNumbers, lastConc)
        return missingNumbers
    }
}