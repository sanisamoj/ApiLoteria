package com.sanisamoj

import com.sanisamoj.models.repository.CaixaDefaultRepository
import com.sanisamoj.models.repository.LoteriaDefaultRepository

object GlobalContext {
    val caixaRepository = CaixaDefaultRepository()
    val LoteriaRepository = LoteriaDefaultRepository()
}