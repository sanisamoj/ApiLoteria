package com.sanisamoj.data.models.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class WinnersLocations(
    val ganhadores: Int?,
    val municipio: String?,
    val nomeFatansiaUL: String?,
    val serie: String?,
    val posicao: Int?,
    val uf: String?
)
