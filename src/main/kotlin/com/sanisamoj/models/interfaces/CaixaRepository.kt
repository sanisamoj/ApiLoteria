package com.sanisamoj.models.interfaces

import com.sanisamoj.models.generics.ResultsCaixa

interface CaixaRepository {
    suspend fun getResult(loteria: String, conc: Int): ResultsCaixa
    suspend fun getLatestResult(loteria: String): ResultsCaixa
}