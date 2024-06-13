package com.sanisamoj.models.repository

import com.sanisamoj.external.LoteriaApi
import com.sanisamoj.models.generics.ResultsCaixa
import com.sanisamoj.models.interfaces.CaixaRepository

class CaixaDefaultRepository: CaixaRepository {
    override suspend fun getResult(loteria: String, conc: Int): ResultsCaixa {
        return LoteriaApi.retrofitService.getGameResult(loteria, conc.toString())
    }

    override suspend fun getLatestResult(loteria: String): ResultsCaixa {
        return LoteriaApi.retrofitService.getLatestGameResult(loteria)
    }
}