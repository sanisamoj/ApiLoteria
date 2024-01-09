package com.sanisamoj.launcher

import com.example.demo.utils.findMissingNumber
import com.sanisamoj.database.ResultsCaixa
import com.sanisamoj.external.ApiRequest
import com.sanisamoj.service.Operations

//@Component
class Launcher {

    //Busca o último sorteio da loteria, e vai registrar todos
    suspend fun registerAllResults(apiUrl: String, loteria: String, lastConc: String) {

        //Instancializa o objeto da apiRequest
        val apiRequest: ApiRequest = ApiRequest()

        //Instancializa o objeto das operações
        val operation: Operations = Operations()

        //Retorna o valor do último sorteio
        val lastestConc: Int? = apiRequest.get<ResultsCaixa>("$apiUrl/$loteria/$lastConc")?.numero

        //Caso não seja nulo percorre entre todos os resultados
        lastestConc?.let {
            for (conc in lastestConc downTo 1) {

                //Requisita o resultado de uma loteria
                val result: ResultsCaixa? = apiRequest.get<ResultsCaixa>("$apiUrl/$loteria/$conc")

                result?.let {
                    operation.addItem(result = result)
                }

            }
        }
    }

    //Verifica quais jogos não estão registrados e salvá-os
    suspend fun veriFyIntegrity(apiUrl: String, game: String): Unit {

        //Instancializa o serviço operações
        val operations = Operations()

        //Armazena uma lista de todos os resultados do banco de dados
        val allMyConc: List<ResultsCaixa> = operations.getAll(game).toList()

        //Variável que irá armazenar todos os números dos jogos do banco de dados
        val allGamesNumbers: MutableList<Int> = mutableListOf<Int>()

        //Adiciona todos os números dos concursos que estão registrados no banco de dados
        for(index in allMyConc.indices) {
            allGamesNumbers.add(allMyConc[index].numero)
        }

        //Instancializa a API_REQUEST
        val apiRequest = ApiRequest()

        //Armazena o número do último sorteio atualizado
        val lastConc: Int? = apiRequest.get<ResultsCaixa>("$apiUrl/$game")?.numero

        //Armazena a array com os números faltantes
        val missingNumbers: List<Int> = findMissingNumber(allGamesNumbers, lastConc!!)

        //Percorre os números faltarantes realizando a requisição
        missingNumbers.forEach { missingNumber ->
            val result: ResultsCaixa? = apiRequest.get<ResultsCaixa>("$apiUrl/$game/$missingNumber")
            operations.addItem(result)
        }

    }

}