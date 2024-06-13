package com.sanisamoj.models.interfaces

import com.sanisamoj.models.generics.ResultsCaixa

interface LoteriaRepository {
    suspend fun register(result: ResultsCaixa) : ResultsCaixa
    suspend fun getAllResults(loteria: String): List<ResultsCaixa>
    suspend fun getByConc(loteria: String, conc: Int) : ResultsCaixa
    suspend fun getLatest(loteria: String): ResultsCaixa
}