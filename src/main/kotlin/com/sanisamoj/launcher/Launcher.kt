package com.sanisamoj.launcher

import com.sanisamoj.config.GlobalContext
import com.sanisamoj.data.models.dataclass.ResultsApiResponse
import com.sanisamoj.data.models.interfaces.DatabaseRepository
import com.sanisamoj.data.repository.ApiRepository
import com.sanisamoj.utils.analyzers.findMissingNumber

class Launcher(
    private val apiRepository: ApiRepository = GlobalContext.apiRepository,
    private val databaseRepository: DatabaseRepository = GlobalContext.databaseRepository
) {

    companion object {
        private val failedResults = mutableMapOf<String, MutableSet<Int>>()
    }

    // Search for the latest lottery draw and record all
    suspend fun registerAllResults(loteria: String) {
        val lastestConc: Int? = apiRepository.getLatestResult(loteria).numero
        lastestConc?.let {
            for (conc in lastestConc downTo 1) {
                val result: ResultsApiResponse? = apiRepository.getResult(loteria, conc)
                result?.let {
                    databaseRepository.register(result)
                }
            }
        }
    }

    // Check which games are not registered and save them
    suspend fun verifyIntegrity(loteria: String): Unit {
        println("$loteria is being updated")
        val allMyConc: List<ResultsApiResponse> = databaseRepository.getAllResults(loteria).toList()
        val allGamesNumbers: MutableList<Int> = mutableListOf<Int>()
        for(index in allMyConc.indices) {
            allGamesNumbers.add(allMyConc[index].numero)
        }

        val lastestConc: Int? = apiRepository.getLatestResult(loteria).numero
        val missingNumbers: List<Int> = findMissingNumber(allGamesNumbers, lastestConc!!)
        println("Missing $loteria numbers - $missingNumbers")

        missingNumbers.forEach { missingNumber ->
            if (failedResults[loteria]?.contains(missingNumber) == true) {
                println("Skipping previously failed number $missingNumber for $loteria")
                return@forEach
            }
            try {
                val result: ResultsApiResponse = apiRepository.getResult(loteria, missingNumber)
                databaseRepository.register(result)
            } catch (ex: Throwable) {
                println("$missingNumber for $loteria has not been updated")
                failedResults.computeIfAbsent(loteria) { mutableSetOf() }.add(missingNumber)
            }
        }

    }

}