package com.sanisamoj.service

import com.sanisamoj.GlobalContext
import com.sanisamoj.database.CollectionsInDb
import com.sanisamoj.database.Fields
import com.sanisamoj.database.MongodbOperations
import com.sanisamoj.database.OperationField
import com.sanisamoj.models.generics.ResultsCaixa
import com.sanisamoj.models.interfaces.LoteriaRepository
import org.bson.Document
import org.bson.types.ObjectId

class ResultService(private val loteriaRepository: LoteriaRepository = GlobalContext.LoteriaRepository) {

    // Registra um resultado no banco de dados
    suspend fun register(result: ResultsCaixa) {
        loteriaRepository.register(result)
    }

    // Retorna todos os resultados de uma loteria específica
    suspend fun returnAll(loteria: String): List<ResultsCaixa> {
        return loteriaRepository.getAllResults(loteria)

    }

    // Retorna um resultado de uma loteria, com um concurso específico
    suspend fun returnByConc(loteria: String, conc: Int): ResultsCaixa? {
        return loteriaRepository.getByConc(loteria, conc)
    }

    // Retorna o último resultado da loteria
    suspend fun returnLatest(loteria: String): ResultsCaixa {
        return loteriaRepository.getLatest(loteria)
    }
}