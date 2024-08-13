package com.sanisamoj.data.models.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class PrizeApportionmentList(
    val descricaoFaixa: String?,
    val faixa: Int?,
    val numeroDeGanhadores: Int?,
    val valorPremio: Float?
)
